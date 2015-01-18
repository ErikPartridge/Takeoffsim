/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline;

import com.takeoffsim.airport.Airport;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Erik
 */
public final class GlobalRoutes {

    public static final ConcurrentHashMap<String, GlobalRoute> globalRoutes = new ConcurrentHashMap<>(24000);

    private GlobalRoutes() {
    }


    public static int getFrequency(@NotNull Airport dept, @NotNull Airport arr, Airline a) {
        String name = dept.getIcao() + "-" + arr.getIcao();
        GlobalRoute g = globalRoutes.get(name);
        assert g != null;
        Iterable<Route> nonStops = g.getNonstops();
        int sum = 0;
        for (Route nonStop : nonStops) {
            if (nonStop.getAirline().equals(a)) {
                sum++;
            }
        }
        return sum;
    }


    @Nullable
    public static GlobalRoute get(@NotNull Airport dept, @NotNull Airport arr) {
        String name = dept.getIcao() + "-" + arr.getIcao();
        return globalRoutes.get(name);
    }

    public static void todaysFlights() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods,
        // choose Tools | Templates.
    }
}
