/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.controllers;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.models.airport.Airport;
import com.takeoffsim.models.airport.Airports;
import com.takeoffsim.services.Config;
import lombok.extern.apachecommons.CommonsLog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

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
        context.put("airline", Airlines.humanAirline());
        context.put("default", new Random().nextInt(9999));
        InputStream is = PebbleManager.getInputStream(file, context);
        return is;
    }

    public static InputStream manage(String url, Map<String, String> params) throws IOException{
        String uri = url.replaceFirst("/human/", "").replaceAll(".html", "");
        try{
            switch(uri){
                case "index" :return getAirlineIndex();
                case "new-flight": return getNewFlightView();
                case "flight-config": return finishFlightView(params);
            }
        }catch (PebbleException e){
            log.error(e,e);
        }
        throw new IOException();
    }

    private static InputStream finishFlightView(Map<String, String> params) throws PebbleException, IOException{
        final Map<String, String> map = params;
        String dep = map.remove("depart");
        String arr = map.remove("arrive");
        dep = dep.split(" ")[0].trim();
        arr = arr.split(" ")[0].trim();
        final String auto = map.remove("auto-gates");
        final String flightNo = map.remove("flightNumber");
        final TempFlightData data = new TempFlightData(dep, arr, auto, flightNo, map.keySet());
        final File file = new File(Config.themePath() + "human/flight-config.html");
        final Map<String, Object> context = new HashMap<>();
        context.put("airline", Airlines.humanAirline());
        context.put("data", data);
        final InputStream is = PebbleManager.getInputStream(file, context);
        return is;
    }


}

class TempFlightData{

    public static TempFlightData data = null;

    private final Airport depart;

    private final Airport arrive;

    private final boolean[] operates = new boolean[7];

    private final boolean autoGates;

    private final String flightNumber;

    public TempFlightData(String depart, String arrive, String autoGates, String flightNumber, Set<String> opr){
        System.out.println(depart);
        this.depart = Airports.getAirport(depart);
        this.arrive = Airports.getAirport(arrive);
        this.autoGates = fromString(autoGates);
        this.flightNumber = Airlines.humanAirline().getIcao() + flightNumber;
        makeOperates(opr);
        data = this;
    }

    private static boolean fromString(String string){
        System.out.println(string);
        switch(string){
            case "true" : return true;
            default: return false;
        }
    }

    private void makeOperates(Set<String> days){
        List<String> list = days.stream().collect(Collectors.toList());
        if(list.contains("Monday"))
            operates[0] = true;
        if(list.contains("Tuesday"))
            operates[1] = true;
        if(list.contains("Wednesday"))
            operates[2] = true;
        if(list.contains("Thursday"))
            operates[3] = true;
        if(list.contains("Friday"))
            operates[4] = true;
        if(list.contains("Saturday"))
            operates[5] = true;
        if(list.contains("Sunday"))
            operates[6] = true;
    }

}