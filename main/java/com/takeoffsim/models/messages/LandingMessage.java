/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.models.messages;

import com.takeoffsim.models.aircraft.Airplane;
import com.takeoffsim.models.airline.Flight;
import com.takeoffsim.airport.Airport;
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
