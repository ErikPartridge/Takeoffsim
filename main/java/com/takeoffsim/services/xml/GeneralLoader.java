/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services.xml;

/**
 * Created by Erik on 10/5/14.
 */
final class GeneralLoader {


    private GeneralLoader() {
    }

    private static void loadAllButAirlines(String simName) {

    }

    public static void loadAll(String simName){
        loadAllButAirlines(simName);
        new AirlineLoader().createAirlines();
    }
}
