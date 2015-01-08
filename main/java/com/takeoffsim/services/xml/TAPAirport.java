/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.services.xml;

import com.takeoffsim.airport.Airport;
import com.takeoffsim.threads.TAPAirportThread;
import com.takeoffsim.threads.ThreadManager;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 9/11/14.
 */
@CommonsLog
public class TAPAirport {

    public void createAirports() {
        try{
            List<Element> airports = new ArrayList<>();
            airports.addAll(createAllAirports(getClass().getClassLoader().getResourceAsStream("tap_airports/north_america.xml")));
            airports.addAll(createAllAirports(getClass().getClassLoader().getResourceAsStream("tap_airports/africa.xml")));
            airports.addAll(createAllAirports(getClass().getClassLoader().getResourceAsStream("tap_airports/asia.xml")));
            airports.addAll(createAllAirports(getClass().getClassLoader().getResourceAsStream("tap_airports/europe.xml")));
            airports.addAll(createAllAirports(getClass().getClassLoader().getResourceAsStream("tap_airports/south_america.xml")));
            airports.addAll(createAllAirports(getClass().getClassLoader().getResourceAsStream("tap_airports/oceania.xml")));

            for(int i = 0; i < 6; i++){
                ThreadManager.submit(new TAPAirportThread());
            }

        }catch(FileSystemException fse){
            System.exit(150);
        }
    }

    /**
     *
     * @param f The input stream of the xml document
     * @return A list of all the airports in the region defined by the xml document
     */
    @Nullable
    public List<Element> createAllAirports(InputStream f) throws FileSystemException {
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
        return airports;
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
