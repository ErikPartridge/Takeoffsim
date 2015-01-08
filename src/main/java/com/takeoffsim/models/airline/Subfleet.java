/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014
 */

package com.takeoffsim.models.airline;

import com.google.common.collect.HashBiMap;
import com.takeoffsim.models.aircraft.AircraftType;
import com.takeoffsim.models.aircraft.Airplane;
import com.takeoffsim.models.economics.Delivery;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

import static com.google.common.collect.HashBiMap.create;

/**
 * @author Erik
 */
@Data
@CommonsLog
public class Subfleet implements Serializable {
    static final long serialVersionUID = 19230142401243L;
    //Subfleet of a type. Holds aircraft. Stored in fleet.

    @NonNull
    private final AircraftType acfType;

    private HashBiMap<String, Airplane> aircraft = create(30);

    private ArrayList<Route> routesForType = new ArrayList<>();

    private ArrayList<Delivery> orders = new ArrayList<>();


    public Subfleet(String arln, AircraftType acf, int lease, int own, HashBiMap<String, Airplane> members) {
        this.acfType = acf;
        this.aircraft = members;
    }


    public int getRequiredFA() {
        return (int) Math.ceil(acfType.getMaxEconomySeats() / 50.0);
    }

    /**
     * @return the aircraft
     */
    public HashBiMap<String, Airplane> getAircraft() {
        return aircraft;
    }


    /**
     * @param aircraft the aircraft to set
     */
    public void setAircraft(HashBiMap<String, Airplane> aircraft) {
        this.aircraft = aircraft;
    }


    @Nullable
    public Airplane getAirplane(String registration) {
        return aircraft.get(registration);
    }


    /**
     * @return the routesForType
     */
    public ArrayList<Route> getRoutesForType() {
        return routesForType;
    }


    /**
     * @param routesForType the routesForType to set
     */
    public void setRoutesForType(ArrayList<Route> routesForType) {
        this.routesForType = routesForType;
    }


    public void addRouteToType(Route r) {
        this.routesForType.add(r);
    }


}
