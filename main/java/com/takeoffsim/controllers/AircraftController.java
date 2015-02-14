/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.controllers;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.takeoffsim.models.aircraft.AircraftTypes;
import com.takeoffsim.services.Config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
public class AircraftController {

    public static InputStream manage(String url, Map<String, String> params) throws IOException {
        String uri = url.replaceFirst("/aircraft/", "").replaceAll(".html", "");
        String[] split = uri.split("/");
        try{
            switch(split[0]) {
                case "overview": return overview(split[1]);
                case "list": return list();
            }
        }catch(PebbleException e){
            throw new IOException(e);
        }
        throw new IOException("Nothing found");
    }

    public static InputStream overview(String icao) throws PebbleException, IOException{
        if(AircraftTypes.getAcfType(icao) != null){
            throw new InvalidParameterException("Airline could not be found with icao " + icao);
        }else{
            Map<String, Object> context = new HashMap<>();
            context.put("acfType", AircraftTypes.getAcfType(icao));
            File file = new File(path() + "overview.html");
            return PebbleManager.getInputStream(file, context);
        }
    }

    public static InputStream list() throws PebbleException, IOException{
        Map<String, Object> context = new HashMap<>();
        System.out.println(AircraftTypes.listTypes().size());
        context.put("types", AircraftTypes.listTypes());
        File file = new File(path(), "list.html");
        return PebbleManager.getInputStream(file, context);
    }

    private static String path(){
        return Config.themePath() + "aircraft/";
    }


}
