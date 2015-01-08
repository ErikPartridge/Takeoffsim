/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline;


import com.takeoffsim.airport.Airport;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Erik
 */
public class GlobalRoute implements Serializable {
    //All the options to get from point a to point b
    private ArrayList<ConnectingRoute> connectingFlights = new ArrayList<>();

    private ArrayList<Route> nonstops = new ArrayList<>();

    private int availableDemand;

    private Airport depart;

    private Airport arrive;

    private boolean finalized;

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
     * @param connectingFlights the connectingFlights to set
     */
    public void setConnectingFlights(ArrayList<ConnectingRoute> connectingFlights) {
        this.connectingFlights = connectingFlights;
    }


    /**
     * @return the nonstops
     */
    public ArrayList<Route> getNonstops() {
        return nonstops;
    }


    /**
     * @param nonstops the nonstops to set
     */
    public void setNonstops(ArrayList<Route> nonstops) {
        this.nonstops = nonstops;
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

    public void setDepart(Airport depart) {
        this.depart = depart;
    }

    public Airport getArrive() {
        return arrive;
    }

    public void setArrive(Airport arrive) {
        this.arrive = arrive;
    }

    public boolean isFinalized() {
        return finalized;
    }



    public void finalize() throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Fix this");
    }
}
