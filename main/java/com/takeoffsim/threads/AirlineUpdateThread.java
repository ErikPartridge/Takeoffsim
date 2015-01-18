/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.threads;

import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.models.world.Time;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Erik in 11, 2014.
 */
class AirlineUpdateThread{

    private static List<Airline> airlines = null;

    private LocalDateTime time = null;

    public AirlineUpdateThread() {
        if(airlines == null)
            airlines = Airlines.cloneAirlines();
        if(time == null)
            time = Time.getDateTimeInstance();
    }

    public void run() {
        while(!airlines.isEmpty()){
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String toString() {
        return "AirlineUpdateThread{" +
                "time=" + time +
                '}';
    }
}
