/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.controllers;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.StringLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.takeoffsim.server.Server;

import java.io.*;
import java.util.Map;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
class PebbleManager {

    private static PebbleEngine engine;

    /**
     *
     * @param file the file to render
     * @param context the map of values
     * @return an input stream to the rendering
     * @throws PebbleException
     * @throws IOException
     */
    static InputStream getInputStream(File file, Map<String, Object> context) throws PebbleException, IOException {
        StringLoader loader = new StringLoader();
        String result = Files.toString(file, Charsets.UTF_8);
        engine = new PebbleEngine(loader);
        PebbleTemplate template = engine.getTemplate(result);
        Writer out = new StringWriter();
        template.evaluate(out, context);
        return Server.stringToInputStream(out.toString());
    }

}
