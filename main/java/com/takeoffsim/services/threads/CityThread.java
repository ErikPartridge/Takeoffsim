/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services.threads;

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
class CityThread extends Thread {

    private static List<Element> elements = null;


    private CityThread(List<Element> e){
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
