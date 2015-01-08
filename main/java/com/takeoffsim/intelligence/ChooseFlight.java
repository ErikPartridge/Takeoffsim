/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/** (c) Erik Malmstrom-Partridge 2014
 This code is provided as-is without any warranty
 This source code or its compiled program may not be redistributed in any way
 **/
package com.takeoffsim.intelligence;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.ConnectingFlight;
import com.takeoffsim.models.airline.Flight;
import com.takeoffsim.airport.Airport;
import com.takeoffsim.demand.RouteDemand;
import com.takeoffsim.models.world.TimeUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@CommonsLog
public class ChooseFlight {


    public boolean chooseFlight(@NotNull Airport departs, @NotNull Airport arrives, LocalDate day) {

        boolean worked = false;
        //What airlines serve it
        ArrayList<Airline> serves = getOverlap(departs.getServes(), arrives.getServes());
        //list of connecting flights, one-stop, only flights with that airline are tied together
        ArrayList<ConnectingFlight> connectingFlights = new ArrayList<>();
        //nonstop options, only full once executing the loop
        ArrayList<Flight> nonstop = new ArrayList<>();
        //Loop through and fill lists
        for (Airline a : serves) {
            ArrayList<Flight> flightsToProcess = getFlightsByAirline(departs, arrives, a, day);
            connectingFlights.addAll(makeConnectingFlights(departs, arrives, flightsToProcess));
            nonstop.addAll(nonstops(departs, arrives, flightsToProcess));
        }
        int sum = 0;
        sum = connectingFlights.stream().map(this::getScoreConnecting).reduce(sum, Integer::sum);
        sum += nonstop.stream().map(this::getScore).reduce(sum, Integer::sum);

        double paxPerScore = RouteDemand.demand(departs, arrives) / sum;



        worked = true;
        return worked;
    }


    @NotNull
    private ArrayList<Airline> getOverlap(@NotNull ArrayList<Airline> one, @NotNull ArrayList<Airline> two) {
        ArrayList<Airline> overlap = new ArrayList<>();
        one.stream().filter((a) -> (two.contains(a) && !overlap.contains(a))).forEach(overlap::add);
        return overlap;
    }

    private int getLayover(@NotNull Flight one, @NotNull Flight two) {
        LocalDateTime dept = two.getDepartsGmt();
        LocalDateTime arr = one.getArrivesGmt();
        if (dept.isBefore(arr)) {
            return -1000;
        } else if (dept.getDayOfYear() - arr.getDayOfYear() > 1) {
            return -1001;
        } else if (dept.getDayOfYear() - arr.getDayOfYear() == 1) {
            int minutes = (24 - arr.getHour()) * 60 + (60 - arr.getMinute());
            minutes += dept.getHour() * 60 + dept.getMinute();
            return minutes;
        } else {
            return (dept.getHour() * 60 + dept.getMinute()) - (arr.getHour() * 60 + arr.getMinute());
        }
    }

    private ArrayList<Flight> getFlightsByAirline(@NotNull Airport departs, @NotNull Airport arrives, @NotNull Airline airline, LocalDate day) {
        ArrayList<Flight> flightsToHub = new ArrayList<>(5);
        ArrayList<Flight> flightsFromHub = new ArrayList<>(5);
        ArrayList<Flight> nonstops = new ArrayList<>();
        departs.getFlightsByAirline(airline, day).stream().map((f) -> {
            if (f.getRoute().getDeparts().equals(departs) && airline.getHubs().contains(f.getRoute().getArrives())) {
                flightsToHub.add(f);
            }
            return f;
        }).filter((f) -> (f.getRoute().getDeparts().equals(departs) && f.getRoute().getArrives().equals(arrives))).forEach(nonstops::add);
        arrives.getFlightsByAirline(airline, day).stream().filter((f) -> (airline.getHubs().contains(f.getRoute().getDeparts()) && f.getRoute().getArrives().equals(arrives))).forEach(flightsFromHub::add);
        ArrayList<Flight> all = new ArrayList<>();
        all.addAll(flightsToHub);
        all.addAll(flightsFromHub);
        all.addAll(nonstops);
        return all;
    }

    @NotNull
    private ArrayList<ConnectingFlight> makeConnectingFlights(Airport depart, Airport arrive, @NotNull ArrayList<Flight> toProcess) {
        ArrayList<ConnectingFlight> made = new ArrayList<>();
        toProcess.stream().filter((f) -> (f.getRoute().getDeparts().equals(depart))).forEach((f) -> {
            ArrayList<Flight> possible = new ArrayList<>();
            toProcess.stream().filter((flt) -> (flt.getRoute().getArrives().equals(arrive))).filter((flt) -> (TimeUtils.layover(f, flt) < 360)).forEach(possible::add);
            possible.stream().map((flight) -> new ConnectingFlight(f, flight)).forEach(made::add);
        });
        return made;
    }

    private ArrayList<Flight> flightsToHub(@NotNull Airport departs, @NotNull Airport arrives, @NotNull Airline airline, LocalDate day) {
        ArrayList<Flight> flightsToHub = new ArrayList<>(5);
        ArrayList<Flight> flightsFromHub = new ArrayList<>(5);
        ArrayList<Flight> nonstops = new ArrayList<>();
        try {
            departs.getFlightsByAirline(airline, day).stream().map((f) -> {
                if (f.getRoute().getDeparts().equals(departs) && airline.getHubs().contains(f.getRoute().getArrives())) {
                    flightsToHub.add(f);
                }
                return f;
            });
        }catch(Exception e){
            log.debug(e);
        }
        arrives.getFlightsByAirline(airline, day).stream().filter((f) -> (airline.getHubs().contains(f.getRoute().getDeparts()) && f.getRoute().getArrives().equals(arrives))).forEach(flightsFromHub::add);
        ArrayList<Flight> all = new ArrayList<>();
        all.addAll(flightsToHub);
        all.addAll(flightsFromHub);
        return all;
    }

    @NotNull
    private ArrayList<Flight> nonstops(Airport departs, Airport arrives, @NotNull ArrayList<Flight> toProcess) {
        ArrayList<Flight> ns = new ArrayList<>();
        toProcess.stream().filter((f) -> (f.getRoute().getDeparts().equals(departs) && f.getRoute().getArrives().equals(arrives))).forEach(ns::add);
        return ns;
    }

    //offset should be percent CASM change since 2011
    public double getBasePrice(@NotNull Airport departs, @NotNull Airport arrives, double percentOffset) {
        final double CASM = .116 * (1 + percentOffset);
        double bestPrice;
        int distance = (int) LatLngTool.distance(new LatLng(departs.getLatitude(), departs.getLongitude()), new LatLng(arrives.getLatitude(), arrives.getLongitude()), LengthUnit.MILE);
        bestPrice = distance * CASM;
        return bestPrice;
    }

    protected int getScoreConnecting(@NotNull ConnectingFlight cf) {
        int score = 100;
        if (cf.getLayover() < 35) {
            score = 0;
        } else if (cf.getLayover() < 110) {
            score -= (110 - cf.getLayover()) / 4;
        } else {
            score -= (cf.getLayover() - 110) / 3;
        }
        score -= (((int) getBasePrice(cf.getFlights()[0].getRoute().getDeparts(), cf.getFlights()[1].getRoute().getArrives(), 0.0d)) - cf.getPrice()) / 5;
        if (score < 0) {
            score = 0;
        }
        return score;
    }

    protected int getScore(@NotNull Flight f) {
        int score = 100;
        score -= (((int) getBasePrice(f.getRoute().getDeparts(), f.getRoute().getArrives(), 0.0d)) - f.getRoute().getEcoPrice()) / 5;
        if (score < 0) {
            score = 0;
        }
        return score;
    }

    protected void allocateDemand(Flight f, int number) {
        f.book(number * getScore(f));
    }
}
