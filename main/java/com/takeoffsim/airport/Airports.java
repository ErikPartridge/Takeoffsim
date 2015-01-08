/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014
 */

package com.takeoffsim.airport;


import com.google.common.collect.HashBiMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.HashBiMap.create;


public final class Airports implements Serializable {
    //List of all the airports. Lot of memory

    static final long serialVersionUID = 14141298797799L;

    private static HashBiMap<String, Airport> airports = create(10000);


    private Airports() {
    }

    @Nullable
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
