/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.manufacturers;


import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public final class AircraftManufacturers implements Serializable {

    static final long serialVersionUID = -1395910888323L;

    static final Map<String, AircraftManufacturer> manufacturers = new ConcurrentHashMap<>(10);


    private AircraftManufacturers() {
    }

    public static Collection<AircraftManufacturer> manufacturers(){
        return manufacturers.values();
    }


    public static AircraftManufacturer get(String str){
        return manufacturers.get(str);
    }
    public static void selfAnalyze() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods,
        // choose Tools | Templates.
    }

    public static void invest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods,
        // choose Tools | Templates.
    }

    public static void deliver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods,
        // choose Tools | Templates.
    }

    public static void put(AircraftManufacturer aircraftManufacturer) {
        manufacturers.put(aircraftManufacturer.getName(), aircraftManufacturer);
    }

    public static void clear() {
        manufacturers.clear();
    }
}
