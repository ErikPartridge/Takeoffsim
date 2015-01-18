/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.world;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author Erik
 */

public class City implements Serializable{

    private String name;

    private double latitude;

    private double longitude;

    private int population;

    @NonNull
    private Country country;


    public City(String name, double latitude, double longitude, int population, Country country) {
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setPopulation(population);
        this.setCountry(country);
        this.setName(name);
    }



    public City() {

    }

    @NotNull
    @Override
    public String toString() {
        return "City{" +
                "latitude=" + getLatitude() +
                ", longitude=" + getLongitude() +
                ", population=" + getPopulation() +
                ", country=" + getCountry() +
                '}';
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getPopulation() {
        return population;
    }

    void setPopulation(int population) {
        this.population = population;
    }

    Country getCountry() {
        return country;
    }

    void setCountry(Country country) {
        this.country = country;
    }
}
