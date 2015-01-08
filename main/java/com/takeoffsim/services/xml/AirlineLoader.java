/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-Partridge 2014
 */

package com.takeoffsim.services.xml;

import com.google.common.collect.HashBiMap;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.models.airline.Alliances;
import com.takeoffsim.models.airline.Flight;
import com.takeoffsim.airport.Airport;
import com.takeoffsim.models.economics.Company;
import com.takeoffsim.models.economics.Stock;
import lombok.extern.apachecommons.CommonsLog;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.money.Money;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.takeoffsim.airport.Airports.getAirport;

@CommonsLog
public class AirlineLoader {

    public AirlineLoader() {
        super();
    }

    @Nullable
    private static HashBiMap<String, Flight> getFlights(Element root, Company owner) {

        return null;
    }

    @Nullable
    private static ArrayList<Stock> getHoldings(Element root, Company owner) {
        ArrayList<Stock> holds = new ArrayList<>();
        //Stock s = new Stock();
        //TODO
        return null;
    }

    public void createAirlines() {
        makeAirlinesFromStream(getClass().getClassLoader().getResourceAsStream("airlines/airlines.xml"));
    }

    protected void makeAirlinesFromStream(InputStream is) {
        org.jdom2.Document doc = null;
        try {
            doc = new SAXBuilder().build(is);
        } catch (JDOMException e) {
            log.error("JDOMException when trying to load the airlines, I ended the loading process, this may cause some bugs.");
            return;
        } catch (IOException e) {
            log.error("IOException when trying to load the airlines, I ended the loading process, this may cause some bugs.");
            return;
        }
        ArrayList<Airline> results = new ArrayList<>(16);
        List<Element> airlines = doc.getRootElement().getChildren("airline");
        System.out.println("number of airline elements: " + airlines.size());
        for (Element element : airlines){
            Airline airline = new Airline();
            airline.setName(getString("name", element));
            airline.setHuman(false);
            airline.setIata(getString("iata", element));
            airline.setIcao(getString("icao", element));
            airline.setReputation(Integer.parseInt(getString("reputation", element)));
            airline.setAggressiveness(Integer.parseInt(getString("aggressiveness", element)));
            //airline.setNumShares(Long.parseLong(getString("shares", element)));
            airline.setCeo(getString("ceo", element));
            airline.setHeadquarters(getString("headquarters", element));
            airline.setEarnings(Money.parse(getString("earnings", element)));
            airline.setCosts(Money.parse(getString("costs", element)));
            airline.setCash(Money.parse(getString("cash", element)));
            airline.setValuation(Money.parse(getString("valuation", element)));
            airline.setFlightAttendantPay(Money.parse(getString("faPay", element)));
            airline.setPilotPay(Money.parse(getString("pilotPay", element)));
            airline.setMechanicPay(Money.parse(getString("mechPay", element)));
            airline.setHubs(getHubs(getString("hubs", element)));
            airline.setDividend(Double.parseDouble(getString("dividends", element)));
            airline.setAlliance(Alliances.getAlliance(getString("alliance", element)));
            airline.setCodeShares(getCodeShares(getString("codeshares", element)));
            Airlines.put(airline.getIcao(), airline);

            System.out.println("Put airline " + airline.getIcao());
        }

    }

    private String getString(String child, Element element) {
        return element.getChildTextTrim(child);
    }

    private ArrayList<Airline> getCodeShares(String string) {
        ArrayList<Airline> airlines = new ArrayList<>();
        String[] split = string.split(",");
        for (String str : split) {
            airlines.add(Airlines.getAirline("str"));
        }
        return airlines;
    }

    @NotNull
    private ArrayList<Airport> getHubs(String string) {
        ArrayList<Airport> hubs = new ArrayList<>();
        String[] hubList = string.trim().split(",");
        for (String apt : hubList) {
            Airport a = getAirport(apt);
            hubs.add(a);
        }
        return hubs;
    }


}
