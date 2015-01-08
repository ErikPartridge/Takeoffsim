/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
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

/**
 * @author Erik
 */
public class Region implements Serializable {
    private ArrayList<City> cities = new ArrayList<>();

    @NonNull
    private ArrayList<Airport> airportsInRegion = new ArrayList<Airport>();

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
    public ArrayList<City> getPoints() {
        return cities;
    }


    /**
     * @param cities the cities to set
     */
    public void setPoints(ArrayList<City> cities) {
        this.cities = cities;
    }


    /**
     * @return the airportsInRegion
     */
    public ArrayList<Airport> getAirportsInRegion() {
        return airportsInRegion;
    }


    /**
     * @param airportsInRegion the airportsInRegion to set
     */
    public void setAirportsInRegion(ArrayList<Airport> airportsInRegion) {
        this.airportsInRegion = airportsInRegion;
    }

    public void addPoints(ArrayList<City> points) {
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
