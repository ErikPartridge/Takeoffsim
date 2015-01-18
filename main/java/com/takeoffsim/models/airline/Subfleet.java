/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014
 */

package com.takeoffsim.models.airline;

import com.takeoffsim.models.aircraft.AircraftType;
import com.takeoffsim.models.aircraft.Airplane;
import com.takeoffsim.models.economics.Delivery;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;

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

    private Map<String, Airplane> aircraft = new HashMap<>();

    private List<Route> routesForType = new ArrayList<>();

    private ArrayList<Delivery> orders = new ArrayList<>();


    private Subfleet(String arln, AircraftType acf, int lease, int own, Map<String, Airplane> members) {
        this.acfType = acf;
        this.aircraft.putAll(members);
    }


    public int getRequiredFA() {
        return (int) Math.ceil(acfType.getMaxEconomySeats() / 50.0);
    }

    /**
     * @return the aircraft
     */
    public Map<String,Airplane> getAircraft() {
        return Collections.unmodifiableMap(aircraft);
    }

    @Nullable
    public Airplane getAirplane(String registration) {
        return aircraft.get(registration);
    }


    /**
     * @return the routesForType
     */
    public List<Route> getRoutesForType() {
        return Collections.unmodifiableList(routesForType);
    }

    public void addRouteToType(Route r) {
        this.routesForType.add(r);
    }


}
