/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline;


import com.takeoffsim.airport.Airport;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Erik
 */
@SuppressWarnings("ReturnOfCollectionOrArrayField")
public class GlobalRoute implements Serializable {
    //All the options to get from point a to point b
    private ArrayList<ConnectingRoute> connectingFlights = new ArrayList<>();

    private ArrayList<Route> nonstops = new ArrayList<>();

    private int availableDemand;

    private Airport depart;

    private Airport arrive;

    private final boolean finalized;

    public GlobalRoute(Airport depart, Airport arrive) {
        this.setDepart(depart);
        this.setArrive(arrive);
        this.finalized = false;
    }

    /**
     * @return the connectingFlights
     */
    public ArrayList<ConnectingRoute> getConnectingFlights() {
        return connectingFlights;
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

    public boolean isFinalized() {
        return finalized;
    }

}
