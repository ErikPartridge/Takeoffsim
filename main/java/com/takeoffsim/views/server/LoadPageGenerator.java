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
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.models.airline.Fleet;
import com.takeoffsim.models.airport.Airport;
import com.takeoffsim.models.airport.Airports;
import com.takeoffsim.models.people.GeneratePerson;
import com.takeoffsim.models.people.Investor;
import com.takeoffsim.services.Config;
import com.takeoffsim.services.GameProperties;
import com.takeoffsim.services.Serialize;
import com.takeoffsim.services.xml.AirlineLoader;
import com.takeoffsim.services.xml.RegionLoader;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.io.*;
import java.rmi.NoSuchObjectException;
import java.util.*;

/**
 * Created by erik on 1/13/15.
 */
@CommonsLog
final class LoadPageGenerator {
    private LoadPageGenerator() {
    }

    public static InputStream createWorldLoadView(Map<String, String> params) {
        createCeo(params);
        return Main.SERVER.resourceAtPath("create-world.html");
    }

    public static InputStream creationResultsView(Map<String, String> params) throws IOException, PebbleException {
        if(!Airlines.humanAirline().getCash().isZero()){
            throw new IOException("Page has already been accessed");
        }
        GameProperties.setNameOfSim(params.get("name"));
        Config.nameOfSim = params.get("name");
        switch(params.get("difficulty")){
            case "Realistic": GameProperties.setInvestorDifficulty(5); break;
            case "Insane": GameProperties.setInvestorDifficulty(4); break;
            case "Difficult": GameProperties.setInvestorDifficulty(3); break;
            case "Moderate": GameProperties.setInvestorDifficulty(2); break;
            case "Easy": GameProperties.setInvestorDifficulty(1); break;
        }
        MersenneTwisterRNG rand = new MersenneTwisterRNG();
        int numInvestors = rand.nextInt(7) + 2;
        double investment = 105000000.0 - 5000000 * GameProperties.getInvestorDifficulty();
        double median = investment / numInvestors;
        Collection<Investor> investors = new ArrayList<>();
        Money totalInvestment = Money.zero(CurrencyUnit.USD);

        for(int i = 0; i < numInvestors; i++){
            investors.add(GeneratePerson.createInvestor());
        }
        for(Investor i : investors){
            totalInvestment = totalInvestment.plus(i.invest(median));
        }
        String ceo = "CEO";
        if (Airlines.humanAirline() != null) {
            ceo = Airlines.humanAirline().getCeo();
        }
        String airline = Airlines.humanAirline().getName();


        File file = new File(Config.themePath() + "creation-results.html");
        Map<String, Object> context = new TreeMap<>();
        context.put("money", totalInvestment);
        context.put("investors", investors);
        context.put("ceoName", ceo);
        context.put("airlineName", airline);
        Airlines.humanAirline().setCash(totalInvestment);
        return getInputStream(file, context);
    }


    private static void createCeo(Map<String, String> params){
        String name = params.get("first") + " " + params.get("last");
        Airlines.humanAirline().setCeo(name);
    }


    public static InputStream createAirlineView() throws PebbleException, IOException{
        File file = new File(Config.themePath() + "create-airline.html");
        Map<String, Object> context = new HashMap<>();
        context.put("airports", Airports.sortedValuesList());
        return getInputStream(file, context);
    }

    private static InputStream getInputStream(File file, Map<String, Object> context) throws PebbleException, IOException{
        StringLoader loader = new StringLoader();
        String result = Files.toString(file, Charsets.UTF_8);
        PebbleEngine engine = new PebbleEngine(loader);
        PebbleTemplate template = engine.getTemplate(result);
        Writer out = new StringWriter();
        template.evaluate(out, context);
        return Server.stringToInputStream(out.toString());
    }
    public static InputStream createCeoView(Map<String, String> params) {
        if(Airlines.humanAirline() == null)
            createAirline(params);
        return Main.SERVER.resourceAtPath("create-ceo.html");
    }

    private static void createAirline(Map<String, String> params){
        new RegionLoader().createRegions();
        new AirlineLoader().createAirlines();

        Airline mine = new Airline();

        Airline a = Airlines.remove(params.get("iatacode"));
        reselectIcao(a);
        mine.setIcao(params.get("icaocode"));
        mine.setIata(params.get("iatacode"));
        mine.setName(params.get("name"));
        mine.setFleet(new Fleet(mine.getIcao(), new ArrayList<>()));
        mine.setHuman(true);
        String apt = params.get("hq").split("-")[0].toUpperCase().trim();
        mine.addHub(Airports.getAirport(apt));
        mine.setHeadquarters(Airports.getAirport(apt));
        Airlines.put(mine.getIcao(), mine);
    }

    private static void reselectIcao(Airline a){
        int count = 0;
        if(a == null)
            return;
        while(Airlines.get(a.getIcao()) != null && count < 500){
            a.setIcao(RandomStringUtils.randomAlphabetic(3));
            count++;
        }
        if(Airlines.get(a.getIcao()) == null){
            Airlines.put(a.getIcao(), a);
        }
    }

    private static void reselectIata(Airline a){
        int count = 500;
        a.setIata(RandomStringUtils.randomAlphanumeric(2));
    }


    private static List<Airport> setToList(Iterable<Airport> list){
        List<Airport> airports = new ArrayList<>();
        list.forEach(airports::add);
        return airports;
    }

    public static InputStream loadOptions() throws PebbleException, IOException{
        File file = new File(Config.themePath() + "load-save.html");
        Map<String, Object> context = new HashMap<>();
        File folder = new File(Serialize.homeDirectory() + "saves/");
        if(folder.exists() && folder.isDirectory()){
            List<String> list = new ArrayList<>();
            for (File f : folder.listFiles()) {
                String[] split = f.getName().split("/");
                list.add(split[split.length - 1]);
            }
            context.put("worldlist",list);
        }
        return getInputStream(file, context);
    }

    public static InputStream loadView(Map<String, String> params) throws PebbleException, IOException{
        try{
            Serialize.loadWorld(params.get("world"));
            Config.nameOfSim = params.get("world");
        }catch (NoSuchObjectException e){
            Main.load("http://localhost:40973/landing.html");
        }
        return AirlinePageGenerator.getAirlineIndex();
    }
}
