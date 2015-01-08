/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
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

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
