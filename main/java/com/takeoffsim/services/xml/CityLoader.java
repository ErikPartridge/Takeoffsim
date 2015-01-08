/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
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
public class CityLoader {


    public void loadCities() {

        InputStream is = getClass().getClassLoader().getResourceAsStream("tap_cities/cities.xml");

        Document doc = null;
        try {
            doc = new SAXBuilder().build(is);
        } catch (JDOMException | IOException e) {
            log.error(e);
        }

        Element root = doc.getRootElement();
        List<Element> elements = root.getChildren();
        loadFromList(elements);
    }

    public void loadFromList(List<Element> e){
        List<Element> elements = e;
        elements.forEach(new Consumer<Element>() {
            @Override
            public void accept(Element element) {

                if (!element.getChild("country").getTextTrim().equals("US")) {
                    String name = element.getChild("name").getTextTrim();
                    double latitude = Double.parseDouble(element.getChildTextTrim("latitude"));
                    double longitude = Double.parseDouble(element.getChildTextTrim("longitude"));
                    int population = Integer.parseInt(element.getChildTextTrim("population"));
                    Country country = Countries.getCountry(element.getChildTextTrim("country"));
                    City city = new City(name, latitude, longitude, population, country);
                    try {
                        country.addCity(city);
                    } catch (NullPointerException ptr) {
                        log.error("Could not find country with code : " + element.getChildTextTrim("country"));
                    }
                }
            }
        });

    }

}
