/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.services.xml;

/**
 * Created by Erik on 10/5/14.
 */
public class GeneralLoader {


    public static void loadAllButAirlines(String simName) {

    }

    public static void loadAll(String simName){
        loadAllButAirlines(simName);
        new AirlineLoader().createAirlines();
    }
}
