/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.intelligence;

import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Flight;
import com.takeoffsim.models.airline.Route;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Erik in 09, 2014.
 */
public class FlightScheduler {

    private Airline airline;

    private LocalDate firstDay;

    public FlightScheduler(Airline airline, LocalDate firstDay) {
        this.airline = airline;
        this.firstDay = firstDay;
    }

    public void allocate() throws Exception {
        ArrayList<Route> routes = airline.getRoutes();
        ArrayList<Flight> flights = new ArrayList<>();
        for (Route route : routes) {
            if (route.getOperates()[0]) {
                flights.add(new Flight(null, 0, firstDay, route));
            }
            if (route.getOperates()[1]) {
                flights.add(new Flight(null, 0, firstDay.plusDays(1), route));
            }
            if (route.getOperates()[2]) {
                flights.add(new Flight(null, 0, firstDay.plusDays(2), route));
            }
            if (route.getOperates()[3]) {
                flights.add(new Flight(null, 0, firstDay.plusDays(3), route));
            }
            if (route.getOperates()[4]) {
                flights.add(new Flight(null, 0, firstDay.plusDays(4), route));
            }
            if (route.getOperates()[5]) {
                flights.add(new Flight(null, 0, firstDay.plusDays(5), route));
            }
            if (route.getOperates()[6]) {
                flights.add(new Flight(null, 0, firstDay.plusDays(6), route));
            }
        }


    }
}
