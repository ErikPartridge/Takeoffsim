/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.world;


import com.google.common.collect.HashBiMap;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Set;

import static com.google.common.collect.HashBiMap.create;

/**
 * @author Erik
 */
public class Regions {


    private static HashBiMap<String, Region> regions = create(300);


    @Nullable
    public static Region getRegion(String s) {
        return regions.get(s);
    }

    public static void putAllRegions(ArrayList<Region> r) {
        for (Region region : r) {
            regions.put(region.getName(), region);
        }
    }

    public static Set<Region> getRegionsList(){
        return regions.values();
    }

}
