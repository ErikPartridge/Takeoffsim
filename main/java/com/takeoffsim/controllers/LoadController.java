/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.controllers;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.takeoffsim.services.Config;
import com.takeoffsim.services.Serialize;
import lombok.extern.apachecommons.CommonsLog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by erik on 1/13/15.
 */
@CommonsLog
public final class LoadController {
    private LoadController() {
    }

    public static InputStream manage(String url, Map<String, String> params) throws IOException{
        String uri = url.replaceFirst("/loading/", "").replaceAll(".html", "");
        try{
            switch(uri){
                case "wait": return loadView(params);
                case "saves": return saves();
            }
        }catch (PebbleException e){
            log.error(e, e);
            throw new IOException(e);
        }

        throw new IOException("No resource found for the given url");
    }

    private static String path(){
        return Config.themePath() + "/loading/";
    }


    public static InputStream saves() throws PebbleException, IOException{
        File file = new File(path() + "saves.html");
        Map<String, Object> context = new HashMap<>();
        File folder = new File(Serialize.homeDirectory() + "saves/");
        if(folder.exists() && folder.isDirectory()){
            List<String> list = new ArrayList<>();
            for (File f : folder.listFiles()) {
                list.add(f.getName().replaceAll(".tss", ""));
            }
            context.put("worldlist",list);
        }
        return PebbleManager.getInputStream(file, context);
    }

    public static InputStream loadView(Map<String, String> params) throws PebbleException, IOException{
        Config.nameOfSim = params.get("world");
        Serialize.readAll();
        return HumanController.getAirlineIndex();
    }

}
