/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.world;


import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Erik
 */
public final class Regions {


    private static final Map<String, Region> regions = new ConcurrentHashMap<>(300);

    private Regions() {
    }

    public static void clear(){
        regions.clear();
    }


    @Nullable
    public static Region getRegion(String s) {
        return regions.get(s);
    }

    public static void putAllRegions(Iterable<Region> r) {
        for (Region region : r) {
            regions.put(region.getName(), region);
        }
    }

    public static Iterable<Region> getRegionsList(){
        return regions.values();
    }

}
