/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.controllers;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.takeoffsim.models.airline.Airlines;
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
        File file = new File(Config.themePath() + "airline-index.html");
        Map<String,Object> context = new HashMap<>();
        context.put("Airline", Airlines.humanAirline());
        return PebbleManager.getInputStream(file, context);
    }

    public static InputStream manage(String url, Map<String, String> params) throws IOException{
        String uri = url.replaceFirst("/human/", "").replaceAll(".html", "");
        try{
            switch(uri){
                case "index" : getAirlineIndex();
            }
        }catch (PebbleException e){
            log.error(e,e);
        }
        throw new IOException();
    }
}
