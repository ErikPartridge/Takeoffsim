/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.controllers;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.models.airline.Fleet;
import com.takeoffsim.models.airport.Airport;
import com.takeoffsim.models.airport.Airports;
import com.takeoffsim.models.people.GeneratePerson;
import com.takeoffsim.models.people.Investor;
import com.takeoffsim.server.Main;
import com.takeoffsim.services.Config;
import com.takeoffsim.services.GameProperties;
import com.takeoffsim.services.xml.AircraftTypeLoader;
import com.takeoffsim.services.xml.AirlineLoader;
import lombok.extern.apachecommons.CommonsLog;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
@CommonsLog
public class CreationController{

    /**
     *
     * @param url the page requested
     * @param params any POST/GET Data
     * @return InputStream to write
     * @throws IOException
     */
    public static InputStream manage(String url, Map<String, String> params) throws IOException{
        String uri = url.replaceFirst("/creation/", "").replaceAll(".html", "");
        System.out.println(uri);
        try{
            switch(uri){
                case "results": return results(params);
                case "world": return world(params);
                case "airline": return airline();
                case "ceo": return ceo(params);
            }
        }catch (PebbleException e){
            log.error(e, e);
            throw new IOException(e);
        }

        throw new IOException("No resource found for the given url");
    }

    private static InputStream world(Map<String, String> params) {
        buildCeo(params);
        return Main.SERVER.resourceAtPath("/creation/world.html");
    }

    private static InputStream results(Map<String, String> params) throws IOException, PebbleException {
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
        double investment = getInvestmentAmount();
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


        File file = new File(Config.themePath() + "/creation/results.html");
        Map<String, Object> context = new TreeMap<>();
        context.put("money", totalInvestment);
        context.put("investors", investors);
        context.put("ceoName", ceo);
        context.put("airlineName", airline);
        Airlines.humanAirline().setCash(totalInvestment);
        return PebbleManager.getInputStream(file, context);
    }

    public static double getInvestmentAmount(){
        return 105000000.0 - 5000000 * GameProperties.getInvestorDifficulty();
    }

    private static void buildCeo(Map<String, String> params){
        String name = params.get("first") + " " + params.get("last");
        Airlines.humanAirline().setCeo(name);
    }

    private static InputStream airline() throws PebbleException, IOException{
        File file = new File(Config.themePath() + "/creation/airline.html");
        Map<String, Object> context = new HashMap<>();
        context.put("airports", Airports.sortedValuesList());
        return PebbleManager.getInputStream(file, context);
    }

    private static InputStream ceo(Map<String, String> params) {
        if(Airlines.humanAirline() == null)
            buildAirline(params);
        return Main.SERVER.resourceAtPath("/creation/ceo.html");
    }

    private static void buildAirline(Map<String, String> params){
        new AircraftTypeLoader().makeAircraftTypes();
        new AirlineLoader().createAirlines();

        Airline mine = new Airline();

        Airline a = Airlines.remove(params.get("iatacode"));
        Airlines.reselectIcao(a);
        mine.setIcao(params.get("icaocode"));
        mine.setIata(params.get("iatacode"));
        mine.setName(params.get("name"));
        mine.setFleet(new Fleet(mine.getIcao(), new ArrayList<>()));
        mine.setHuman(true);
        String apt = params.get("hq").split("-")[0].toUpperCase().trim();
        mine.addHub(Airports.getAirport(apt));
        mine.setHeadquarters(Airports.getAirport(apt));
        Airlines.put(mine.getIcao(), mine);
        log.error(Airlines.getMap().size());
        log.error(Airlines.humanAirline().getIcao());
    }

    private static List<Airport> setToList(Iterable<Airport> list){
        List<Airport> airports = new ArrayList<>();
        list.forEach(airports::add);
        return airports;
    }
}
