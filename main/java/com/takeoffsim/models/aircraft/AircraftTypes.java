/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014
 */

package com.takeoffsim.models.aircraft;


import com.google.common.collect.HashBiMap;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.math3.random.MersenneTwister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
This keeps a nice list of AircraftTypes, makes them accessible
*/

/**
 * @author Erik
 * @since 0.3 alpha
 */
@CommonsLog
public class AircraftTypes implements Serializable {

    static final long serialVerionUID = 893521325L;


    /**
     * a bi-directional map of strings to aircrafttypes
     */
    private static HashBiMap<String, AircraftType> aircraftTypes = HashBiMap.create(400);
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
    public static AircraftType getRandom(@NotNull MersenneTwister str) {
        Set<AircraftType> s = aircraftTypes.values();
        int max = s.size();
        List<AircraftType> l = new ArrayList<>(s);
        return l.get(str.nextInt(max));
    }

    public static HashBiMap<String, AircraftType> getMap() {
        return aircraftTypes;
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