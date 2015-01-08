/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.manufacturers;


import com.google.common.collect.HashBiMap;
import org.jetbrains.annotations.Nullable;

import static com.google.common.collect.HashBiMap.create;


public class EngineManufacturers {

    private static HashBiMap<String, EngineManufacturer> manufacturers = create(10);


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
