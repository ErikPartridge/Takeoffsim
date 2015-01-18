/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.world;


import com.google.common.collect.HashBiMap;
import org.jetbrains.annotations.Nullable;

import static com.google.common.collect.HashBiMap.create;

/**
 * @author Erik
 */
public final class Regions {


    private static final HashBiMap<String, Region> regions = create(300);

    private Regions() {
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
