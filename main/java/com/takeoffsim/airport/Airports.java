/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014
 */

package com.takeoffsim.airport;


import com.google.common.collect.HashBiMap;
import com.jcabi.aspects.Cacheable;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.collect.HashBiMap.create;

@CommonsLog
public final class Airports implements Serializable {
    //List of all the airports. Lot of memory

    static final long serialVersionUID = 14141298797799L;

    private static HashBiMap<String, Airport> airports = create(10000);


    private Airports() {
    }

    @Nullable @Cacheable(lifetime = 30, unit = TimeUnit.SECONDS)
    public static Airport getAirport(String name) {
        return airports.get(name);
    }

    public static void put(String id, Airport o) {
        airports.put(id, o);
    }

    public static HashBiMap<String, Airport> getAirports() {
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

}
