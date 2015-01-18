/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014
 */

package com.takeoffsim.models.aircraft;


import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.math3.random.RandomGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
This keeps a nice list of AircraftTypes, makes them accessible
*/

/**
 * @author Erik
 * @since 0.3 alpha
 */
@CommonsLog
public final class AircraftTypes implements Serializable {

    static final long serialVersionUID = 893521325L;


    /**
     * a bi-directional map of strings to aircrafttypes
     */
    private static final Map<String, AircraftType> aircraftTypes = new ConcurrentHashMap<>(200);
    //This class runs a list and keeps methods. Only one instance;


    /**
     * Make only one of these
     */
    private AircraftTypes() {
    }

    /**
     * @param acfType the icao code of the aircraft type you are looking for
     * @return the found aircraft type else, null
     */

    @Nullable
    public static AircraftType getAcfType(String acfType) {
        return aircraftTypes.get(acfType);
    }

    /**
     * @param str the Mersenne Twister random number generator
     * @return the random aircraft type retrieved
     */
    public static AircraftType getRandom(@NotNull RandomGenerator str) {
        Collection<AircraftType> s = aircraftTypes.values();
        int max = s.size();
        List<AircraftType> l = new ArrayList<>(s);
        return l.get(str.nextInt(max));
    }

    public static Map<String, AircraftType> getMap() {
        return Collections.unmodifiableMap(aircraftTypes);
    }

    /**
     * @param type adds the aircraft type into the list
     */
    public void enterAircraftType(@NotNull AircraftType type) {
        aircraftTypes.put(type.getName(), type);
        log.trace("Added aircraft type: " + type);
    }


    @NotNull
    @Override
    public String toString() {
        String s = "";
        s += Arrays.toString(aircraftTypes.keySet().toArray());
        return s;
    }
}