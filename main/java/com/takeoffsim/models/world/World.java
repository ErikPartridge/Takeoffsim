/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.world;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Erik
 */
final class World {


    @NotNull
    private static final List<City> cities = new ArrayList<>();

    private World() {
    }


    public static int getNumberOfPoints() {
        return cities.size();
    }


    public static City getPopulationPoint(int i) {
        return cities.get(i);
    }

}
