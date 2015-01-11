/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services.xml;

import com.takeoffsim.airport.Airport;
import com.takeoffsim.airport.AirportBuilder;
import com.takeoffsim.airport.Airports;
import com.takeoffsim.airport.Runway;
import com.takeoffsim.models.world.Countries;
import lombok.extern.apachecommons.CommonsLog;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystemException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 9/11/14.
 */
@CommonsLog
public class TAPAirport {

    public void createAirports() {
        try{
            createAllAirports(getClass().getClassLoader().getResourceAsStream("tap_airports/north_america.xml"));
            createAllAirports(getClass().getClassLoader().getResourceAsStream("tap_airports/africa.xml"));
            createAllAirports(getClass().getClassLoader().getResourceAsStream("tap_airports/asia.xml"));
            createAllAirports(getClass().getClassLoader().getResourceAsStream("tap_airports/europe.xml"));
            createAllAirports(getClass().getClassLoader().getResourceAsStream("tap_airports/south_america.xml"));
            createAllAirports(getClass().getClassLoader().getResourceAsStream("tap_airports/oceania.xml"));
        }catch(FileSystemException fse){
            System.exit(150);
        }
    }


    public void makeAirport(Element e) {
        AirportBuilder builder = new AirportBuilder();
        builder = builder.setName(e.getAttribute("name").getValue());
        builder = builder.setIata(e.getAttribute("iata").getValue());
        builder = builder.setIcao(e.getAttribute("icao").getValue());
        builder = builder.setInternational(e.getAttribute("type").getValue().contains("International"));
        String gmt = e.getChild("town").getAttribute("GMT").getValue();
        ZoneId zoneId = null;
        if (gmt.equals("00:00:00")) {
            zoneId = ZoneId.of("GMT");
        } else if (gmt.charAt(0) != '-') {
            gmt = "+" + gmt;
            zoneId = ZoneId.of(gmt);
        } else {
            zoneId = ZoneId.of(gmt);
        }
        builder = builder.setTimeZone(zoneId);
        builder = builder.setCountry(Countries.getTapCountry(e.getChild("town").getAttributeValue("country")));
        Element degrees = e.getChild("coordinates");
        builder = builder.setLatitude(latitudeFromString(degrees.getChild("latitude").getAttributeValue("value")));
        builder = builder.setLongitude(longitudeFromString(degrees.getChild("longitude").getAttributeValue("value")));
        int gates = 0;
        Element terminals = e.getChild("terminals");
        for (Element element : terminals.getChildren()) {
            gates += Integer.parseInt(element.getAttribute("gates").getValue());
        }
        builder = builder.setGates(gates);
        builder = builder.setDelayFactor(0);
        builder = builder.setDemandBonus(0);
        builder = builder.setSlotControlled(false);
        Airport apt = builder.createAirport();
        ArrayList<Runway> runways = new ArrayList<>();
        for (Element r : e.getChild("runways").getChildren()) {
            String title = r.getAttributeValue("name");
            int length = Integer.parseInt(r.getAttributeValue("length"));
            String surface = r.getAttributeValue("surface");
            if (title.equals("E/W")) {
                title = "09/27";
            } else if (title.equals("N/S")) {
                title = "18/36";
            } else if (title.contains("ESE")) {
                title = "11/29";
            } else if (title.contains("NW")) {
                title = "12/30";
            }
            if (r.getAttribute("type") != null) {
                Runway helipad = new Runway(title, length, surface, apt, true);
                runways.add(helipad);

            } else {
                Runway runway = new Runway(title, length, surface, apt);
                runways.add(runway);
            }
        }
        apt.setRunways(runways);
        if (!apt.getIcao().equals("")) {
            Airports.put(apt.getIcao(), apt);
        } else {
            Airports.put(apt.getIata(), apt);
        }
    }

    /**
     *
     * @param f The input stream of the xml document
     * @return A list of all the airports in the region defined by the xml document
     */
    @Nullable
    public void createAllAirports(InputStream f) throws FileSystemException {
        Document doc = null;
        try {
            doc = new SAXBuilder().build(f);
        } catch (JDOMException e) {
            log.error("Got a JDOMException trying to load TAPAirport, returning null");
            throw new FileSystemException("");
        } catch (IOException e) {
            log.error("IOException trying to load TAPAirports, returning null");
            throw new FileSystemException("");
        }
        Element root = doc.getRootElement();
        ArrayList<Airport> results = new ArrayList<>();
        List<Element> airports = root.getChildren();
        for(Element e: airports){
            makeAirport(e);
        }
    }

    private double latitudeFromString(@NotNull String s) {
        double degrees = Double.parseDouble(s.split("\\D")[0]);
        degrees += Double.parseDouble(s.split("\\D")[1]) / 60.0;
        degrees += Double.parseDouble(s.split("\\D")[2]) / 3600.00;
        if (s.contains("S")) {
            degrees *= -1;
        }
        return degrees;
    }

    private double longitudeFromString(@NotNull String s) {
        double degrees = Double.parseDouble(s.split("\\D")[0]);
        degrees += Double.parseDouble(s.split("\\D")[1]) / 60.0;
        degrees += Double.parseDouble(s.split("\\D")[2]) / 3600.00;
        if (s.contains("W")) {
            degrees *= -1;
        }
        return degrees;
    }
}
