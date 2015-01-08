/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.threads;

import com.takeoffsim.airport.Airport;
import com.takeoffsim.airport.AirportBuilder;
import com.takeoffsim.airport.Airports;
import com.takeoffsim.airport.Runway;
import com.takeoffsim.models.world.Countries;
import org.jdom2.Element;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik in 11, 2014.
 */
public class TAPAirportThread extends Thread {

    public static List<Element> airports = new ArrayList<>();

    @Override
    public void run(){
        while(!airports.isEmpty()) {
            Element e = airports.remove(new SecureRandom().nextInt(airports.size()));
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
