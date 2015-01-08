/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.airport;


import com.takeoffsim.models.world.Country;
import org.jetbrains.annotations.NotNull;

import java.time.ZoneId;


public final class AirportBuilder {

    private String name;

    private double latitude;

    private double longitude;

    private boolean isInternational;

    private String icao;

    private String iata;

    private Country country;

    private double delayFactor;

    private double demandBonus;

    private ZoneId timeZone;

    private int gates;

    private boolean slotControlled;


    public AirportBuilder() {
    }

    @NotNull
    public AirportBuilder setSlotControlled(boolean slot) {
        this.slotControlled = slot;
        return this;
    }

    @NotNull
    public AirportBuilder setName(String name) {
        this.name = name;
        return this;
    }


    @NotNull
    public AirportBuilder setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }


    @NotNull
    public AirportBuilder setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }


    @NotNull
    public AirportBuilder setIcao(String icao) {
        this.icao = icao;
        return this;
    }


    @NotNull
    public AirportBuilder setIata(String iata) {
        this.iata = iata;
        return this;
    }


    @NotNull
    public AirportBuilder setCountry(Country country) {
        this.country = country;
        return this;
    }


    @NotNull
    public AirportBuilder setDelayFactor(double delayFactor) {
        this.delayFactor = delayFactor;
        return this;
    }


    @NotNull
    public AirportBuilder setDemandBonus(double demandBonus) {
        this.demandBonus = demandBonus;
        return this;
    }


    @NotNull
    public AirportBuilder setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
        return this;
    }


    @NotNull
    public AirportBuilder setInternational(boolean international) {
        this.isInternational = international;
        return this;
    }


    @NotNull
    public AirportBuilder setGates(int gates) {
        this.gates = gates;
        return this;
    }


    @NotNull
    public Airport createAirport() {
        return new Airport(name, latitude, longitude, icao, iata, country, delayFactor,
                demandBonus, timeZone, gates);
    }


    @NotNull
    @Override
    public String toString() {
        return "AirportBuilder{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", icao='" + icao + '\'' +
                ", iata='" + iata + '\'' +
                ", country=" + country +
                ", delayFactor=" + delayFactor +
                ", demandBonus=" + demandBonus +
                ", timeZone=" + timeZone +
                ", gates=" + gates +
                '}';
    }
}
