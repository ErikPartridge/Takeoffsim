/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.world;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * @author Erik
 */
public class World {


    @NotNull
    private static ArrayList<City> cities = new ArrayList<>();


    public static int getNumberOfPoints() {
        return cities.size();
    }


    public static City getPopulationPoint(int i) {
        return cities.get(i);
    }

}
