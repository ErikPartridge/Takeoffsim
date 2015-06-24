/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.controllers;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.services.Config;
import com.takeoffsim.services.demand.DemandCreator;
import lombok.extern.apachecommons.CommonsLog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
@CommonsLog
public class AirlineController {

    /**
     *
     * @param url the page requested
     * @param params any POST/GET Data
     * @return InputStream to write
     * @throws IOException
     */
    public static InputStream manage(String url, Map<String, String> params) throws IOException{
        String uri = url.replaceFirst("/airline/", "").replaceAll(".html", "");
        String[] split = uri.split("/");
        try{
            switch(split[0]) {
                case "view": return view(split[1]);
            }
        }catch(PebbleException e){
            throw new IOException(e);
        }
        return null;
    }

    private static String path(){
        return Config.themePath() + "airline/";
    }

    /**
     *
     * @param icao the airline's icao code
     * @return an overview of the aircraft type
     * @throws PebbleException
     * @throws IOException
     */
    public static InputStream view(String icao) throws PebbleException, IOException{
        if(Airlines.getAirline(icao) == null){
            throw new InvalidParameterException("Airline could not be found with icao " + icao);
        }else{
            while(DemandCreator.wait){
                try {
                    Thread.sleep(50);
                    System.out.println("waiting");
                } catch (InterruptedException e) {
                    log.error(e);
                }
            }
            Map<String, Object> context = new HashMap<>();
            context.put("online", Config.isWebConnected());
            context.put("airline", Airlines.getAirline(icao));
            File file = new File(path() + "view.html");
            return PebbleManager.getInputStream(file, context);
        }
    }



}
