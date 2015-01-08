/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.models.messages;

import com.takeoffsim.models.aircraft.Airplane;
import com.takeoffsim.airport.Airport;
import lombok.extern.apachecommons.CommonsLog;

/**
 * A class to ask an airport for takeoff clearance. Completely thread safe.
 */
@CommonsLog
public class TryTakeoff{

    private final Airplane airplane;

    private final Airport airport;


    public final String MESSAGE = "I would like to takeoff from this airport";

    public TryTakeoff(Airplane airplane, Airport apt){
        this.airplane = airplane;
        this.airport = apt;
    }


    public Airplane getAirplane() {
        return airplane;
    }

    public Airport getAirport() {
        return airport;
    }
}
