/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline;

import com.google.common.collect.HashBiMap;
import com.takeoffsim.airport.Airport;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static com.google.common.collect.HashBiMap.create;

/**
 * @author Erik
 */
public class GlobalRoutes {

    public static HashBiMap<String, GlobalRoute> globalRoutes = create(10000);


    public static int getFrequency(@NotNull Airport dept, @NotNull Airport arr, Airline a) {
        String name = dept.getIcao() + "-" + arr.getIcao();
        GlobalRoute g = globalRoutes.get(name);
        ArrayList<Route> nonStops = g.getNonstops();
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
