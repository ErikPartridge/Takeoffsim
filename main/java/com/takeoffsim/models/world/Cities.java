/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.world;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Erik on 9/12/14.
 */
public final class Cities {

    private static final List<City> cities = new ArrayList<>(40000);


    public static void clear(){
        cities.clear();
    }

    private Cities() {
    }

    public static Stream<City> cityStream(){
        return cities.stream();
    }

    public static List<City> getCities(){
        return cities;
    }

    public static void putCity(City c){
        cities.add(c);
    }
}
