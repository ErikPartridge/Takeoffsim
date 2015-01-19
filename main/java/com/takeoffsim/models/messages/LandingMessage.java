/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.messages;

import com.takeoffsim.models.aircraft.Airplane;
import com.takeoffsim.models.airline.Flight;
import com.takeoffsim.models.airport.Airport;
import lombok.Data;

/**
 * Created by erik on 12/6/14.
 */
@Data
public class LandingMessage {

    private final Airport arrival;

    private final Airplane airplane;

    private final Flight flight;

    public LandingMessage(Airport arrival, Flight flight, Airplane airplane){
        this.arrival = arrival;
        this.airplane = airplane;
        this.flight = flight;
    }

}
