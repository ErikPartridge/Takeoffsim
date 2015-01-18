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
import com.takeoffsim.main.Config;
import com.takeoffsim.models.airline.Airlines;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by erik on 1/17/15.
 */
public class AirlinePageGenerator {

    public static InputStream getAirlineIndex() throws PebbleException, IOException{
        File file = new File(Config.themePath() + "airline-landing.html");
        Map<String,Object> context = new HashMap<>();
        System.out.println("Name should be" + Airlines.humanAirline().getName());
        context.put("name", Airlines.humanAirline().getName());
        return getInputStream(file, context);
    }

    public static InputStream getInputStream(File file, Map<String, Object> context) throws PebbleException, IOException {
        StringLoader loader = new StringLoader();
        String result = Files.toString(file, Charsets.UTF_8);
        PebbleEngine engine = new PebbleEngine(loader);
        PebbleTemplate template = engine.getTemplate(result);
        Writer out = new StringWriter();
        template.evaluate(out, context);
        return Server.stringToInputStream(out.toString());
    }
}
