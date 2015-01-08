/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.models.messages;

import com.takeoffsim.models.aircraft.Airplane;
import com.takeoffsim.models.airline.Flight;
import com.takeoffsim.airport.Airport;

/**
 * A class to tell an airplane to start the takeoff process. Completely thread safe.
 *
 */
public final class TakeoffMessage {

    private final Airport airport;

    private final Airplane airplane;

    private final Flight flight;


    public TakeoffMessage(Airport apt, Airplane ap, Flight flt){
        this.airport = apt;
        this.airplane = ap;
        this.flight = flt;
    }



    public Airport getAirport() {
        return airport;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public Flight getFlight() {
        return flight;
    }
}
