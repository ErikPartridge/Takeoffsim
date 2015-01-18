/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-Partridge 2014
 */

package com.takeoffsim.services.xml;

import com.takeoffsim.airport.Airport;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.models.airline.Alliances;
import com.takeoffsim.models.airline.Flight;
import com.takeoffsim.models.economics.Company;
import com.takeoffsim.models.economics.Stock;
import lombok.extern.apachecommons.CommonsLog;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.money.Money;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static com.takeoffsim.airport.Airports.getAirport;

@CommonsLog
public class AirlineLoader {

    public AirlineLoader() {
        super();
    }

    @Nullable
    private static HashMap<String, Flight> getFlights(Element root, Company owner) {

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

    void makeAirlinesFromStream(InputStream is) {
        org.jdom2.Document doc;
        try {
            doc = new SAXBuilder().build(is);
        } catch (JDOMException | IOException e) {
            log.error(e);
            return;
        }
        List<Element> airlines = doc.getRootElement().getChildren("airline");
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
            airline.addHubs(getHubs(getString("hubs", element)));
            airline.setDividend(Double.parseDouble(getString("dividends", element)));
            airline.setAlliance(Alliances.getAlliance(getString("alliance", element)));
            airline.addCodeShares(getCodeShares(getString("codeshares", element)));
            Airlines.put(airline.getIcao(), airline);

            log.info("Put airline " + airline.getIcao());
        }

    }

    private String getString(String child, Element element) {
        return element.getChildTextTrim(child);
    }

    private Collection<Airline> getCodeShares(String sharers) {
        List<Airline> airlines = new ArrayList<>();
        String[] split = sharers.split(",");
        for (String str : split) {
            airlines.add(Airlines.getAirline("str"));
        }
        return airlines;
    }

    @NotNull
    private Collection<Airport> getHubs(String apts) {
        List<Airport> hubs = new ArrayList<>();
        String[] hubList = apts.trim().split(",");
        for (String apt : hubList) {
            Airport a = getAirport(apt);
            hubs.add(a);
        }
        return hubs;
    }


}
