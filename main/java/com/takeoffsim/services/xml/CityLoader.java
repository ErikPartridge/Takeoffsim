/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services.xml;

import com.takeoffsim.models.world.City;
import com.takeoffsim.models.world.Countries;
import com.takeoffsim.models.world.Country;
import lombok.extern.apachecommons.CommonsLog;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Erik .
 */
@CommonsLog
class CityLoader {


    public void loadCities() {

        InputStream is = getClass().getClassLoader().getResourceAsStream("tap_cities/cities.xml");

        Document doc = null;
        try {
            doc = new SAXBuilder().build(is);
        } catch (JDOMException | IOException e) {
            log.error(e);
        }

        assert doc != null;
        Element root = doc.getRootElement();
        List<Element> elements = root.getChildren();
        loadFromList(elements);
    }

    void loadFromList(List<Element> e){
        e.forEach(new ElementConsumer());

    }

    private static class ElementConsumer implements Consumer<Element> {
        @Override
        public void accept(Element t) {

            if (!t.getChild("country").getTextTrim().equals("US")) {
                String name = t.getChild("name").getTextTrim();
                double latitude = Double.parseDouble(t.getChildTextTrim("latitude"));
                double longitude = Double.parseDouble(t.getChildTextTrim("longitude"));
                int population = Integer.parseInt(t.getChildTextTrim("population"));
                Country country = Countries.getCountry(t.getChildTextTrim("country"));
                City city = new City(name, latitude, longitude, population, country);
                try {
                    country.addCity(city);
                } catch (NullPointerException ptr) {
                    log.error("Could not find country with code : " + t.getChildTextTrim("country"));
                }
            }
        }
    }
}
