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
import com.takeoffsim.threads.ThreadManager;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.*;

@CommonsLog
public class Engine {

    public static InputStream createAirlineView() throws PebbleException, IOException{
        new RegionLoader().createRegions();
        new AirlineLoader().createAirlines();
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
    public static InputStream createCeoView(Map<String, List<String>> params) throws PebbleException, IOException{
        ThreadManager.submit(() -> createAirline(params));
        System.out.println(Main.server.resourceAtPath("/create-ceo.html"));
        return Main.server.resourceAtPath("create-ceo.html");
    }

    @Async
    private static void createAirline(Map<String, List<String>> params) throws InvalidParameterException{
        Airline mine = new Airline();
        Airline existingIcao = Airlines.get(params.get("icaocode").get(0));
        Airlines.getMap().remove(params.get("icaocode").get(0));
        Airline existingIata = Airlines.get(params.get("iatacode").get(0));
        Airlines.getMap().remove(params.get("iatacode").get(0));
        mine.setIcao(params.get("icaocode").get(0));
        mine.setIata(params.get("iatacode").get(0));
        mine.setName(params.get("name").get(0));
        mine.setFleet(new Fleet(mine.getIcao(), new ArrayList<Subfleet>()));
        mine.setHuman(true);
        Airlines.put(mine.getIcao(), mine);
    }

    private void reselectIcao(Airline a){
        int count = 0;
        while(Airlines.get(a.getIcao()) != null && count < 500){
            a.setIcao(RandomStringUtils.randomAlphabetic(3));
            count++;
        }
        if(Airlines.get(a.getIcao()) == null){
            Airlines.put(a.getIcao(), a);
        }
    }

    private void reselectIata(Airline a){
        int count = 500;
        a.setIata(RandomStringUtils.randomAlphanumeric(2));
    }
    
    
    private static List<Airport> setToList(Set<Airport> list){
        List<Airport> airports = new ArrayList<>();
        list.forEach(apt -> airports.add(apt));
        return airports;
    }

    public static InputStream createWorldLoadView(Map<String, List<String>> params) {
        ThreadManager.submit(() -> createCeo(params));
        return Main.server.resourceAtPath("create-world.html");
    }

    public static InputStream creationResultsView(Map<String, List<String>> params) throws IOException, PebbleException {
        System.out.println(params);
        GameProperties.setNameOfSim(params.get("name").get(0));
        switch(params.get("difficulty").get(0)){
            case "Realistic": GameProperties.setInvestorDifficulty(5); break;
            case "Insane": GameProperties.setInvestorDifficulty(4); break;
            case "Difficult": GameProperties.setInvestorDifficulty(3); break;
            case "Moderate": GameProperties.setInvestorDifficulty(2); break;
            case "Easy": GameProperties.setInvestorDifficulty(1); break;
        }
        System.out.println("it's the switch");
        MersenneTwisterRNG rand = new MersenneTwisterRNG();
        int numInvestors = rand.nextInt(7) + 2;
        System.out.println("Got here");
        double median = (130000000 - 10000000 * GameProperties.getInvestorDifficulty()) / numInvestors;
        List<Investor> investors = new ArrayList<>();
        Money totalInvestment = Money.zero(CurrencyUnit.USD);
        for(int i = 0; i < numInvestors; i++){
            investors.add(GeneratePerson.createInvestor());
        }
        for(Investor i : investors){
            totalInvestment.plus(i.invest(median));
        }
        System.out.println("Got 2");
        String ceo = Airlines.humanAirline().getCeo();
        System.out.println("It's not the airlines");
        String airline = Airlines.humanAirline().getName();

        File file = new File(Config.themePath() + "creation-results.html");
        Map<String, Object> context = new TreeMap<>();
        context.put("money", totalInvestment);
        context.put("investors", investors);
        context.put("ceoName", ceo);
        context.put("airlineName", airline);

        return getInputStream(file, context);
    }


    private static void createCeo(Map<String, List<String>> params){

    }
}
