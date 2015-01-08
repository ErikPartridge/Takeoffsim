/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.threads;

import com.takeoffsim.models.world.City;
import com.takeoffsim.models.world.Countries;
import com.takeoffsim.models.world.Country;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.math3.random.MersenneTwister;
import org.jdom2.Element;

import java.util.List;

/**
 * Created by Erik in 11, 2014.
 */
@CommonsLog
public class CityThread extends Thread {

    private static List<Element> elements = null;


    public CityThread(List<Element> e){
        if(elements == null){
            elements = e;
        }
    }

    @Override
    public void run(){
        Element element = elements.remove(new MersenneTwister().nextInt(elements.size()));

        if(!element.getChild("country").getTextTrim().equals("US")){
            String name = element.getChild("name").getTextTrim();
            double latitude = Double.parseDouble(element.getChildTextTrim("latitude"));
            double longitude = Double.parseDouble(element.getChildTextTrim("longitude"));
            int population = Integer.parseInt(element.getChildTextTrim("population"));
            Country country = Countries.getCountry(element.getChildTextTrim("country"));
            City city = new City(name, latitude, longitude, population, country);
            country.addCity(city);
            log.info("Created city " + name);
        }


    }

}
