/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.views.server;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.jcabi.aspects.Async;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.StringLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.takeoffsim.airport.Airport;
import com.takeoffsim.airport.Airports;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.services.xml.AirlineLoader;
import com.takeoffsim.services.xml.EngineLoader;
import com.takeoffsim.services.xml.RegionLoader;
import com.takeoffsim.threads.ThreadManager;
import lombok.extern.apachecommons.CommonsLog;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.*;

@CommonsLog
public class Engine {

    public static InputStream createAirlineView() throws PebbleException, IOException{
        EngineLoader.createEngineTypes();
        new RegionLoader().createRegions();
        new AirlineLoader().createAirlines();
        StringLoader loader = new StringLoader();
        String file = Files.toString(new File("/home/erik/Takeoffsim/themes/TakeoffSim-Themes/default/create-airline.html"), Charsets.UTF_8);
        PebbleEngine engine = new PebbleEngine(loader);
        PebbleTemplate template = engine.getTemplate(file);
        Writer out = new StringWriter();
        Map<String, Object> context = new HashMap<>();
        context.put("airports", Airports.sortedValuesList());
        template.evaluate(out, context);
        return Server.stringToInputStream(out.toString());
    }

    public static InputStream createCeoView(Map<String, String> params) throws PebbleException, IOException{
        ThreadManager.submit(() -> createAirline(params));
        return Main.server.resourceAtPath("/create-ceo.html");
    }

    @Async
    private static void createAirline(Map<String, String> params) throws InvalidParameterException{
        Airline mine = new Airline();
        Airline existingIcao = Airlines.get(params.get("icaocode"));
        Airline existingIata = Airlines.get(params.get("iatacode"));
    }
    
    
    private static List<Airport> setToList(Set<Airport> list){
        List<Airport> airports = new ArrayList<>();
        list.forEach(apt -> airports.add(apt));
        return airports;
    }
}
