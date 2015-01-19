/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.messages;

import com.takeoffsim.models.aircraft.Airplane;
import com.takeoffsim.models.airline.Flight;
import com.takeoffsim.models.airport.Airport;

/**
 * A class to tell an airplane to start the takeoff process. Completely thread safe.
 *
 */
final class TakeoffMessage {

    private final Airport airport;

    private final Airplane airplane;

    private final Flight flight;


    TakeoffMessage(Airport apt, Airplane ap, Flight flt){
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

    @Override
    public String toString() {
        return "TakeoffMessage{" +
                "airport=" + airport +
                ", airplane=" + airplane +
                ", flight=" + flight +
                '}';
    }
}
