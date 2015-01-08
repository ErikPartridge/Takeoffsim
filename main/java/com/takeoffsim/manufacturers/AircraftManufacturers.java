/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.manufacturers;


import com.google.common.collect.HashBiMap;

import java.io.Serializable;

import static com.google.common.collect.HashBiMap.create;


public class AircraftManufacturers implements Serializable {

    static final long serialVersionUID = -1395910888323L;

    static HashBiMap<String, AircraftManufacturer> manufacturers = create(10);


    private AircraftManufacturers() {
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

}
