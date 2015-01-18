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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@CommonsLog
public final class Airports implements Serializable {
    //List of all the airports. Lot of memory

    static final long serialVersionUID = 14141298797799L;

    private static final Map<String, Airport> airports = new ConcurrentHashMap<>();


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

    public static Map<String, Airport> getAirports() {
        return Collections.unmodifiableMap(airports);
    }

    public static void putAll(@NotNull Iterable<Airport> apts) {
        for (Airport airport : apts) {
            if (!airport.getIcao().equals("")) {
                airports.put(airport.getIcao(), airport);
            } else {
                airports.put(airport.getIata(), airport);
            }
        }
    }

    public static List<Airport> cloneAirports(){
        return airports.values().stream().collect(Collectors.toList());
    }

    public static List<Airport> sortedValuesList(){
         List<Airport> cloned = cloneAirports();
         cloned.sort((a,b) -> a.getIcao().compareTo(b.getIcao()));
        return cloned;
    }

}
