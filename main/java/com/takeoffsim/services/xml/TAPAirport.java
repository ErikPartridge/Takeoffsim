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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystemException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Erik on 9/11/14.
 */
@CommonsLog
public class TAPAirport {

    public void createAirports() {
        try{
            this.createAllAirports(this.getClass().getClassLoader().getResourceAsStream("tap_airports/north_america.xml"));
            this.createAllAirports(this.getClass().getClassLoader().getResourceAsStream("tap_airports/africa.xml"));
            this.createAllAirports(this.getClass().getClassLoader().getResourceAsStream("tap_airports/asia.xml"));
            this.createAllAirports(this.getClass().getClassLoader().getResourceAsStream("tap_airports/europe.xml"));
            this.createAllAirports(this.getClass().getClassLoader().getResourceAsStream("tap_airports/south_america.xml"));
            this.createAllAirports(this.getClass().getClassLoader().getResourceAsStream("tap_airports/oceania.xml"));
            this.createAllAirports(this.getClass().getClassLoader().getResourceAsStream("tap_airports/missing.xml"));
        }catch(FileSystemException fse){
            TAPAirport.log.fatal(fse);
            System.exit(150);
        }
    }


    void makeAirport(Element e) {
        AirportBuilder builder = new AirportBuilder();
        builder = builder.setName(e.getAttribute("name").getValue());
        builder = builder.setIata(e.getAttribute("iata").getValue());
        builder = builder.setIcao(e.getAttribute("icao").getValue());
        ZoneId zoneId;
        try {
            String gmt = e.getChild("town").getAttribute("GMT").getValue();
            if (gmt.equals("00:00:00")) {
                zoneId = ZoneId.of("GMT");
            } else if (gmt.charAt(0) != '-') {
                gmt = "+" + gmt;
                zoneId = ZoneId.of(gmt);
            } else {
                zoneId = ZoneId.of(gmt);
            }
        }catch(NullPointerException ex){
            TAPAirport.log.trace(ex);
            zoneId = TimeZone.getTimeZone(e.getChild("town").getAttributeValue("timezone")).toZoneId();
        }

        builder = builder.setTimeZone(zoneId);
        builder = builder.setCountry(Countries.getTapCountry(e.getChild("town").getAttributeValue("country")));
        Element degrees = e.getChild("coordinates");
        builder = builder.setLatitude(this.latitudeFromString(degrees.getChild("latitude").getAttributeValue("value")));
        builder = builder.setLongitude(this.longitudeFromString(degrees.getChild("longitude").getAttributeValue("value")));
        int gates = 0;
        Element terminals = e.getChild("terminals");
        for (Element element : terminals.getChildren()) {
            gates += Integer.parseInt(element.getAttribute("gates").getValue());
        }
        builder = builder.setGates(gates);
        builder = builder.setDelayFactor();
        builder = builder.setDemandBonus();
        Airport apt = builder.createAirport();
        Collection<Runway> runways = new ArrayList<>();
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
        apt.addRunways(runways);
        if (!apt.getIcao().equals("")) {
            Airports.put(apt.getIcao(), apt);
        } else {
            Airports.put(apt.getIata(), apt);
        }
    }

    /**
     *
     * @param f The input stream of the xml document
     */
    void createAllAirports(InputStream f) throws FileSystemException {
        Document doc;
        try {
            doc = new SAXBuilder().build(f);
        } catch (JDOMException | IOException e) {
            TAPAirport.log.error(e);
            return;
        }
        Element root = doc.getRootElement();
        List<Element> airports = root.getChildren();
        airports.forEach(this::makeAirport);
    }

    private double latitudeFromString(@NotNull String s) {
        if(s.matches("^-?\\d*.\\d*")){
            return Double.parseDouble(s);
        }
        double degrees = Double.parseDouble(s.split("\\D")[0]);
        degrees += Double.parseDouble(s.split("\\D")[1]) / 60.0;
        degrees += Double.parseDouble(s.split("\\D")[2]) / 3600.00;
        if (s.contains("S")) {
            degrees *= -1;
        }
        return degrees;
    }

    private double longitudeFromString(@NotNull String s) {
        if(s.matches("^-?\\d*.\\d*")){
            return Double.parseDouble(s);
        }
        double degrees = Double.parseDouble(s.split("\\D")[0]);
        degrees += Double.parseDouble(s.split("\\D")[1]) / 60.0;
        degrees += Double.parseDouble(s.split("\\D")[2]) / 3600.00;
        if (s.contains("W")) {
            degrees *= -1;
        }
        return degrees;
    }
}
