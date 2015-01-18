/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.messages;

import com.takeoffsim.airport.Airport;
import com.takeoffsim.models.aircraft.Airplane;
import lombok.extern.apachecommons.CommonsLog;

/**
 * A class to ask an airport for takeoff clearance. Completely thread safe.
 */
@CommonsLog
class TryTakeoff{

    private final Airplane airplane;

    private final Airport airport;


    public final String message = "I would like to takeoff from this airport";

    private TryTakeoff(Airplane airplane, Airport apt){
        this.airplane = airplane;
        this.airport = apt;
    }


    public Airplane getAirplane() {
        return airplane;
    }

    public Airport getAirport() {
        return airport;
    }

    @Override
    public String toString() {
        return "TryTakeoff{" +
                "airplane=" + airplane +
                ", airport=" + airport +
                ", MESSAGE='" + message + '\'' +
                '}';
    }
}
