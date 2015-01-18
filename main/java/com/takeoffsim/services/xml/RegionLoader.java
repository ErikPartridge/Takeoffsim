/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services.xml;

import com.takeoffsim.models.world.City;
import com.takeoffsim.models.world.Countries;
import com.takeoffsim.models.world.Region;
import com.takeoffsim.models.world.Regions;
import lombok.extern.apachecommons.CommonsLog;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CommonsLog
public class RegionLoader {

    public void createRegions() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("regions/USRegions.xml");
        Regions.putAllRegions(makeRegions(is));
    }

    Iterable<Region> makeRegions(InputStream is) {
        Document doc = null;
        try {
            doc = new SAXBuilder().build(is);
        } catch (JDOMException e) {
            log.error(e);
            return null;
        } catch (IOException e) {
            log.error(e);
            return null;
        }
        List<Element> list = doc.getRootElement().getChildren();
        Collection<Region> regions = new ArrayList<>();
        for (Element e : list) {
            String name = e.getChildTextTrim("Name");
            Region region = new Region(name);
            region.addPoints(citiesInRegion(e));
            regions.add(region);
        }

        return regions;

    }

    Collection<City> citiesInRegion(Element element) {
        List<Element> elements = element.getChildren();
        Collection<City> cities = new ArrayList<>();
        elements.stream().filter(e -> !e.getName().equals("Name")).forEach(e -> {
            String name = e.getChildTextTrim("SubName");
            int population = Integer.parseInt(e.getChildTextTrim("Population"));
            double latitude = Double.parseDouble(e.getChildTextTrim("Latitude"));
            double longitude = Double.parseDouble(e.getChildTextTrim("Longitude"));
            City c = new City(name, latitude, longitude, population, Countries.getCountry("US"));
            cities.add(c);
        });
        return cities;
    }

}
