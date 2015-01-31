/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline;


import com.takeoffsim.models.airport.Airport;
import com.takeoffsim.models.airport.Airports;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Erik
 */
public class GlobalRoute implements Serializable {
    //All the options to get from point a to point b
    private final List<ConnectingRoute> connectingFlights = new ArrayList<>();

    private final List<Route> nonstops = new ArrayList<>();

    private int availableDemand;

    private Airport depart;

    private Airport arrive;

    public GlobalRoute(Airport depart, Airport arrive) {
        this.setDepart(depart);
        this.setArrive(arrive);
    }

    public GlobalRoute(String depart, String arrive){
        this.depart = Airports.getAirport(depart);
        this.arrive = Airports.getAirport(arrive);
    }

    /**
     * @return the connectingFlights
     */
    public List<ConnectingRoute> getConnectingFlights() {
        return Collections.unmodifiableList(connectingFlights);
    }


    /**
     * @return the nonstops
     */
    public Iterable<Route> getNonstops() {
        return Collections.unmodifiableList(nonstops);
    }


    /**
     * @return the availableDemand
     */
    public int getAvailableDemand() {
        return availableDemand;
    }


    /**
     * @param availableDemand the availableDemand to set
     */
    public void setAvailableDemand(int availableDemand) {
        this.availableDemand = availableDemand;
    }


    @NotNull
    @Override
    public String toString() {
        return "GlobalRoute{" +
                "connectingFlights=" + connectingFlights +
                ", nonstops=" + nonstops +
                ", availableDemand=" + availableDemand +
                '}';
    }


    public Airport getDepart() {
        return depart;
    }

    void setDepart(Airport depart) {
        this.depart = depart;
    }

    public Airport getArrive() {
        return arrive;
    }

    void setArrive(Airport arrive) {
        this.arrive = arrive;
    }

}
