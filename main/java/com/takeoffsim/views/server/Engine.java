/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.views.server;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.StringLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.takeoffsim.airport.Airport;
import com.takeoffsim.airport.Airports;
import lombok.extern.apachecommons.CommonsLog;

import java.io.*;
import java.util.*;

@CommonsLog
public class Engine {

    public static InputStream createAirline() throws PebbleException, IOException{
        StringLoader loader = new StringLoader();
        String file = Files.toString(new File("/home/erik/Takeoffsim/themes/TakeoffSim-Themes/default/create-airline.html"), Charsets.UTF_8);
        PebbleEngine engine = new PebbleEngine(loader);
        PebbleTemplate template = engine.getTemplate(file);
        Writer out = new StringWriter();
        Map<String, Object> context = new HashMap<>();
        context.put("airports", Airports.sortedValuesList());
        template.evaluate(out, context);
        System.out.println(out.toString());
        return Server.stringToInputStream(out.toString());
    }
    
    
    private static List<Airport> setToList(Set<Airport> list){
        List<Airport> airports = new ArrayList<>();
        list.forEach(apt -> airports.add(apt));
        return airports;
    }
}
