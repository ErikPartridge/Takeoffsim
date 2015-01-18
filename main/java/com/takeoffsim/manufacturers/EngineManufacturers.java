/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.manufacturers;


import com.google.common.collect.HashBiMap;
import org.jetbrains.annotations.Nullable;

import static com.google.common.collect.HashBiMap.create;


final class EngineManufacturers {

    private static final HashBiMap<String, EngineManufacturer> manufacturers = create(10);


    private EngineManufacturers() {
    }

    @Nullable
    public static EngineManufacturer get(String property) {
        return manufacturers.get(property);
    }

    public static void selfAnalyze() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods,
        // choose Tools | Templates.
    }

    public static void invest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods,
        // choose Tools | Templates.
    }

}
