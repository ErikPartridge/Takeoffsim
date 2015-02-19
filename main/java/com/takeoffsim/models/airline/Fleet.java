/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2013,2014
 */

package com.takeoffsim.models.airline;


import com.takeoffsim.models.aircraft.AircraftType;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.takeoffsim.models.aircraft.AircraftTypes.getAcfType;

/**
 * @author Erik
 */
public
@Data
class Fleet implements Serializable {
    //An arraylist of subfleet and a name, plus access methods
    static final long serialVersionUID = 4034349989L;

    private Airline airline;

    private List<Subfleet> fleet = Collections.synchronizedList(new ArrayList<>());

    /**
     * @param aln   the icao of the airline
     * @param fleet the fleet of this airline
     */
    public Fleet(String aln, Collection<Subfleet> fleet) {
        this.airline = Airlines.getAirline(aln);
        this.fleet.addAll(fleet);
    }

    public Fleet(){
        this.airline = null;
    }

    /**
     * @param aircraft the icao of the aircraft type
     * @return the airline's subfleet of this type
     */

    @NotNull
    public synchronized Subfleet getSubFleet(String aircraft) {
        AircraftType acfType = getAcfType(aircraft);
        for (Subfleet s : fleet) {
            if (s.getAcfType().equals(acfType)) {
                return s;
            }
        }
        return null;
    }

    public int getSize(){
        int sum = 0;
        for (Subfleet subfleet : fleet) {
            sum += subfleet.getAircraft().values().size();
        }
        return sum;
    }

    @NotNull
    @Override
    public String toString() {
        return "Fleet{" +
                "airline='" + airline + '\'' +
                ", fleet=" + fleet +
                '}';
    }

    @Nullable
    private synchronized Airline getAirline() {
        return airline;
    }
}

    

