/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
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
final class EngineLoader {
    private EngineLoader() {

    }

    public static void createEngineTypes() {
        createAllTypes(EngineLoader.class.getClassLoader().getResourceAsStream("engines/Engines.xml")).orElse(new ArrayList<>());
    }

    private static Optional<ArrayList<EngineType>> createAllTypes(InputStream stream){
        Document doc = null;
        try {
            doc = new SAXBuilder().build(stream);
        } catch (JDOMException e) {
            log.error(e);
            return null;
        } catch (IOException e) {
            log.error(e);
            return null;
        }
        Element root = doc.getRootElement();
        List<Element> elements = root.getChildren();

        return null;
    }
}
