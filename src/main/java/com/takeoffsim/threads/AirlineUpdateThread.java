/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.threads;

import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.models.world.Time;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Erik in 11, 2014.
 */
public class AirlineUpdateThread{

    private static ArrayList<Airline> airlines = null;

    private LocalDateTime time = null;

    public AirlineUpdateThread() {
        if(airlines == null)
            airlines = Airlines.cloneAirlines();
        if(time == null)
            time = Time.getDateTimeInstance();
    }

    public void run() {
        while(!airlines.isEmpty()){

        }
    }

}
