/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.demand;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.takeoffsim.airport.Airport;
import com.takeoffsim.models.airline.GlobalRoute;
import com.takeoffsim.models.world.City;
import com.takeoffsim.threads.AllocateDemandThread;
import com.takeoffsim.threads.DemandRouteThread;
import com.takeoffsim.threads.ThreadManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Erik
 */
public class DemandCreator {

    /**
     * allocates demand and puts it on routes
     */
    public void createAllDemand() {
        for(int i = 0; i < 6; i++){
            ThreadManager.submit(new AllocateDemandThread());
        }
        for(int i = 0; i < 6; i++){
            ThreadManager.submit(new DemandRouteThread());
        }
    }

    /**
     * @param route the global route to create the demand for
     * @return demand on this route
     */
    public synchronized double createDemandOnRoute(@NotNull GlobalRoute route) {
        return RouteDemand.demand(route.getDepart(), route.getArrive());
    }

    /**
     * @param inRegion list of airports in the region
     * @param city     the city from which to allocate the demand
     */
    public synchronized void allocateDemand(@NotNull List<Airport> inRegion, @NotNull final City city) {
        ArrayList<Double> scores = new ArrayList<>();
        double sumOfScores = 0;

        for (Airport a : inRegion) {
            double score = a.getNumFlights() / Math.pow(LatLngTool.distance(new LatLng(city.getLatitude(), city.getLongitude()),
                    new LatLng(a.getLatitude(), a.getLongitude()), LengthUnit.KILOMETER), 2);
            sumOfScores += score;
            scores.add(score);

        }

        for (int i = 0; i < scores.size(); i++) {
            Airport apt = inRegion.get(i);
            double add = (scores.get(i) / sumOfScores) * city.getPopulation() * 1.7 / 365.0;
            apt.setAllocatedDemand(apt.getAllocatedDemand() + add);
            inRegion.set(i, apt);
        }

    }
}
