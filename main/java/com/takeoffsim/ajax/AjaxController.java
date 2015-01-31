/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.ajax;

import com.takeoffsim.models.airline.GlobalRoutes;
import com.takeoffsim.models.airport.Airport;
import com.takeoffsim.models.airport.Airports;
import com.takeoffsim.server.Server;
import com.takeoffsim.services.demand.RouteDemand;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
@CommonsLog
public class AjaxController {

    public static InputStream manage(String url, Map<String, String> params) throws IOException{
        switch (url.replaceAll("/ajax/", "")){
            case "airports.json": return airportJson();
            case "researchRoute.json": return forecastedOD(params);
        }
        throw new IOException();
    }

    public static InputStream airportJson() throws IOException{
        throw new IOException();
    }

    public static InputStream forecastedOD(Map<String, String> params) throws IOException{
        //Get the requested airports
        Airport apt1 = Airports.getAirport(params.get("depart"));
        Airport apt2 = Airports.getAirport(params.get("arrive"));
        double actual = RouteDemand.demand(apt1, apt2);
        NormalDistribution nd = new NormalDistribution(.94, .2);
        int skewed = (int)Math.round(Math.abs(actual * nd.sample()));
        int seats  = GlobalRoutes.getSeats(apt1, apt2);
        String res = "[" + skewed + "," + seats + "]";
        return Server.stringToInputStream(res);
    }
}

