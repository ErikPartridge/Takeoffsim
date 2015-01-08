/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-Partridge 2014
 */

package com.takeoffsim.services.xml;

import com.takeoffsim.models.aircraft.EngineType;
import lombok.extern.apachecommons.CommonsLog;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Erik
 */
@CommonsLog
public class EngineLoader {
    public EngineLoader() {

    }

    public static void createEngineTypes() {
        createAllTypes(EngineLoader.class.getClassLoader().getResourceAsStream("engines/Engines.xml")).orElse(new ArrayList<>());
    }

    public static Optional<ArrayList<EngineType>> createAllTypes(InputStream stream){
        Document doc = null;
        try {
            doc = new SAXBuilder().build(stream);
        } catch (JDOMException e) {
            log.error("Got a JDOMException trying to load TAPAirport, returning empty list");
            return null;
        } catch (IOException e) {
            log.error("IOException trying to load TAPAirports, returning empty list");
            return null;
        }
        Element root = doc.getRootElement();
        List<Element> elements = root.getChildren();

        return null;
    }
}
