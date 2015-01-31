/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.controllers;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.models.airport.Airports;
import com.takeoffsim.services.Config;
import lombok.extern.apachecommons.CommonsLog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
@CommonsLog
public final class HumanController {

    private HumanController() {
    }

    public static InputStream getAirlineIndex() throws PebbleException, IOException{
        File file = new File(Config.themePath() + "human/index.html");
        Map<String,Object> context = new HashMap<>();
        context.put("Airline", Airlines.humanAirline());
        InputStream is = PebbleManager.getInputStream(file, context);
        return is;
    }

    public static InputStream getNewFlightView() throws PebbleException, IOException{
        File file = new File(Config.themePath() + "human/new-flight.html");
        Map<String, Object> context = new HashMap<>();
        context.put("airports", Airports.sortedValuesList());
        InputStream is = PebbleManager.getInputStream(file, context);
        return is;
    }

    public static InputStream manage(String url, Map<String, String> params) throws IOException{
        String uri = url.replaceFirst("/human/", "").replaceAll(".html", "");
        try{
            switch(uri){
                case "index" :return getAirlineIndex();
                case "new-flight": return getNewFlightView();
            }
        }catch (PebbleException e){
            log.error(e,e);
        }
        throw new IOException();
    }
}
