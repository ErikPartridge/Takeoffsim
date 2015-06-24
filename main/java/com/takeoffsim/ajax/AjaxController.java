/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.ajax;

import com.takeoffsim.models.aircraft.Airplane;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.models.airline.Flight;
import com.takeoffsim.models.airline.GlobalRoutes;
import com.takeoffsim.models.airport.Airport;
import com.takeoffsim.models.airport.Airports;
import com.takeoffsim.models.economics.Index;
import com.takeoffsim.models.economics.StockMarket;
import com.takeoffsim.server.Server;
import com.takeoffsim.services.demand.RouteDemand;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
@CommonsLog
public class AjaxController {

    /**
     *
     * @param url the page requested
     * @param params any POST/GET Data
     * @return InputStream to write
     * @throws IOException
     */
    public static InputStream manage(String url, Map<String, String> params) throws IOException {
        switch (url.replaceAll("/ajax/", "")) {
            case "airports.json":
                return airportJson();
            case "researchRoute.json":
                return forecastedOD(params);
            case "planesForType.json":
                return planesForType(params);
        }
        throw new IOException();
    }

    /**
     *  THIS DOESNT WORK
     * @return
     * @throws IOException
     */
    public static InputStream airportJson() throws IOException {
        throw new IOException();
    }

    /**
     *
     * @param params the session parameters
     * @return the response
     * @throws IOException
     */
    public static InputStream planesForType(Map<String, String> params) throws IOException {
        Airport apt1 = Airports.getAirport(params.get("depart").split("-")[0].trim());
        Airport apt2 = Airports.getAirport(params.get("arrive").split("-")[0].trim());
        Airline airline = Airlines.humanAirline();
        final List<Airplane> planes = new ArrayList<>();
        airline.aircraftList().stream().filter(new Predicate<Airplane>() {
            @Override
            public boolean test(Airplane airplane) {
                Stream<Flight> stream = airplane.getFlights().stream().filter(f -> f.getRoute().getArrives().equals(apt1));
                return stream.count() > 1 || airplane.getFlights().size() == 0;
            }
        }).forEach(planes::add);
        String res = "[";
        for (Airplane p : planes) {
            res += "'" + p.getRegistration() + " - " + p.getFlights().size() + " flights',";
        }
        return Server.stringToInputStream(res.substring(0, res.length() - 1) + "]");
    }

    /**
     *
     * @param params session parameters
     * @return the response
     * @throws IOException
     */
    public static InputStream forecastedOD(Map<String, String> params) throws IOException {
        //Get the requested airports
        Airport apt1 = Airports.getAirport(params.get("depart").split("-")[0].trim());
        Airport apt2 = Airports.getAirport(params.get("arrive").split("-")[0].trim());
        double actual = RouteDemand.demand(apt1, apt2);
        NormalDistribution nd = new NormalDistribution(.94, .2);
        int skewed = (int) Math.round(Math.abs(actual * nd.sample()));
        int seats = GlobalRoutes.getSeats(apt1, apt2);
        String res = "[{ \"key\":\"Overview\", \"values\":[{ \"label\": \"Approximate PDEW\", \"value\":" + skewed + "},{ \"label\": \"Current Seats Daily\", \"value\":" + seats + "}]}]";
        return Server.stringToInputStream(res);
    }

    public static InputStream dowJones() throws IOException{
        Index dow = StockMarket.getDow();
        return Server.stringToInputStream(dow.toString());
    }
    
    
}

