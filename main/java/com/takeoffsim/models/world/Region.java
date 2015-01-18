/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.world;

import com.takeoffsim.airport.Airport;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Erik
 */
public class Region implements Serializable {
    private List<City> cities = new ArrayList<>();

    @NonNull
    private List<Airport> airportsInRegion = new ArrayList<>();

    private String name;


    public Region(String name) {
        this.name = name;

    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the points
     */
    public Iterable<City> getPoints() {
        return Collections.unmodifiableList(cities);
    }



    /**
     * @return the airportsInRegion
     */
    public List<Airport> getAirportsInRegion() {
        return Collections.unmodifiableList(airportsInRegion);
    }

    public void addPoints(Collection<City> points) {
        cities.addAll(points);
    }

    @NotNull
    @Override
    public String toString() {
        return "Region{" +
                "cities=" + cities +
                ", airportsInRegion=" + airportsInRegion +
                ", name='" + name + '\'' +
                '}';
    }
}
