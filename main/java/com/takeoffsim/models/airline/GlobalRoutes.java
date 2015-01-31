/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline;

import com.takeoffsim.models.airport.Airport;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Erik
 */
public final class GlobalRoutes {

    public static final Map<String, GlobalRoute> globalRoutes = new ConcurrentHashMap<>(24000);

    private GlobalRoutes() {
    }

    public static void clear(){
        globalRoutes.clear();
    }

    public static void put(GlobalRoute g){
        globalRoutes.put(g.getDepart().getIcao() + "-" + g.getArrive().getIcao(), g);
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

    public static int getSeats(Airport dept, Airport arr){
        String name = dept.getIcao() + "-" + arr.getIcao();
        GlobalRoute g = globalRoutes.get(name);
        if(g != null){
            int seats = 0;
            Iterable<Route> routes = g.getNonstops();
            for (Route route : routes) {
                seats += route.getType().getMaxEconomySeats() * countBools(route.getOperates());
            }
            return seats;
        }
        return 0;
    }

    private static int countBools(boolean[] array){
        int sum = 0;
        for(boolean b: array){
            if (b)
                sum ++;
        }
        return sum;
    }

    public static List<GlobalRoute> listRoutes(){
        ArrayList<GlobalRoute> rtes = new ArrayList<>();
        globalRoutes.values().forEach(rtes::add);
        return rtes;
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
