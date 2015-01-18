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
import com.takeoffsim.main.Config;
import com.takeoffsim.main.GameProperties;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.models.airline.Fleet;
import com.takeoffsim.models.airline.Subfleet;
import com.takeoffsim.models.people.GeneratePerson;
import com.takeoffsim.models.people.Investor;
import com.takeoffsim.services.xml.AirlineLoader;
import com.takeoffsim.services.xml.RegionLoader;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.io.*;
import java.util.*;

/**
 * Created by erik on 1/13/15.
 */
public class LoadPageGenerator {
    public static InputStream createWorldLoadView(Map<String, String> params) {
        createCeo(params);
        return Main.server.resourceAtPath("create-world.html");
    }

    public static InputStream creationResultsView(Map<String, String> params) throws IOException, PebbleException {
        if(!Airlines.humanAirline().getCash().isZero()){
            throw new IOException("Page has already been accessed");
        }
        GameProperties.setNameOfSim(params.get("name"));
        switch(params.get("difficulty")){
            case "Realistic": GameProperties.setInvestorDifficulty(5); break;
            case "Insane": GameProperties.setInvestorDifficulty(4); break;
            case "Difficult": GameProperties.setInvestorDifficulty(3); break;
            case "Moderate": GameProperties.setInvestorDifficulty(2); break;
            case "Easy": GameProperties.setInvestorDifficulty(1); break;
        }
        MersenneTwisterRNG rand = new MersenneTwisterRNG();
        int numInvestors = rand.nextInt(7) + 2;
        double median = (1170000000.0 - 1000000.0 * GameProperties.getInvestorDifficulty()) / numInvestors;
        List<Investor> investors = new ArrayList<>();
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
        System.out.println("Here");
        Airlines.humanAirline().setCeo(name);
    }


    public static InputStream createAirlineView() throws PebbleException, IOException{
        File file = new File(Config.themePath() + "create-airline.html");
        Map<String, Object> context = new HashMap<>();
        context.put("airports", Airports.sortedValuesList());
        return getInputStream(file, context);
    }

    public static InputStream getInputStream(File file, Map<String, Object> context) throws PebbleException, IOException{
        StringLoader loader = new StringLoader();
        String result = Files.toString(file, Charsets.UTF_8);
        PebbleEngine engine = new PebbleEngine(loader);
        PebbleTemplate template = engine.getTemplate(result);
        Writer out = new StringWriter();
        template.evaluate(out, context);
        return Server.stringToInputStream(out.toString());
    }
    public static InputStream createCeoView(Map<String, String> params) throws PebbleException, IOException{
        if(Airlines.humanAirline() == null)
            createAirline(params);
        return Main.server.resourceAtPath("create-ceo.html");
    }

    private static void createAirline(Map<String, String> params){
        new RegionLoader().createRegions();
        new AirlineLoader().createAirlines();

        Airline mine = new Airline();
        /*Airline existingIcao = Airlines.get(params.get("icaocode"));
        System.out.println("ERROR2");
        if(existingIcao != null){
            reselectIcao(existingIcao);
        }
        Airlines.getMap().remove(params.get("icaocode"));
        Airline existingIata = Airlines.get(params.get("iatacode"));
        if(existingIata != null){
            reselectIcao(existingIcao);
        }*/
        Airlines.getMap().remove(params.get("iatacode"));
        mine.setIcao(params.get("icaocode"));
        mine.setIata(params.get("iatacode"));
        mine.setName(params.get("name"));
        mine.setFleet(new Fleet(mine.getIcao(), new ArrayList<Subfleet>()));
        mine.setHuman(true);
        Airlines.put(mine.getIcao(), mine);
        System.out.println("put my airline");
    }

    private static void reselectIcao(Airline a){
        int count = 0;
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


    private static List<Airport> setToList(Set<Airport> list){
        List<Airport> airports = new ArrayList<>();
        list.forEach(apt -> airports.add(apt));
        return airports;
    }
}
