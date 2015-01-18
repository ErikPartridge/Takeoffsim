/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014
 */

package com.takeoffsim.airport;


import com.jcabi.aspects.Cacheable;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@CommonsLog
public final class Airports implements Serializable {
    //List of all the airports. Lot of memory

    static final long serialVersionUID = 14141298797799L;

    private static ConcurrentHashMap<String, Airport> airports = new ConcurrentHashMap<>();


    private Airports() {
    }

    @Nullable @Cacheable(lifetime = 30, unit = TimeUnit.SECONDS)
    public static Airport getAirport(String name) {
        return airports.get(name);
    }

    public static void put(String id, Airport o) {
        if(id.matches("[A-Z]*"))
            airports.put(id, o);
    }

    public static ConcurrentHashMap<String, Airport> getAirports() {
        return airports;
    }

    public static void putAll(@NotNull List<Airport> apts) {
        for (Airport airport : apts) {
            if (!airport.getIcao().equals("")) {
                airports.put(airport.getIcao(), airport);
            } else {
                airports.put(airport.getIata(), airport);
            }
        }
    }

    public static List<Airport> cloneAirports(){
        List<Airport> list = new ArrayList<>();
        for(Airport apt : airports.values()){
            list.add(apt);
        }
        return list;
    }

    public static List<Airport> sortedValuesList(){
         List<Airport> cloned = cloneAirports();
         cloned.sort(new Comparator<Airport>() {
             @Override
             public int compare(Airport o1, Airport o2) {
                 return o1.getIcao().compareTo(o1.getIcao());
             }
         });
        return cloned;
    }

}
