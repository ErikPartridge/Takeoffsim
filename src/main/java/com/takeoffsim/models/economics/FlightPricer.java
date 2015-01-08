/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.economics;

import com.takeoffsim.models.airline.Flight;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erik
 */
public class FlightPricer {
    Flight flight;

    public FlightPricer(Flight f) {
        this.flight = f;
    }

    public double businessClass() {
        NormalDistribution gauss = new NormalDistribution(2.2, .8);
        return gauss.sample() * economyClass();
    }

    public double economyClass() {
        double distance = flight.getRoute().getDistance();
        if (distance < 400) {
            return new NormalDistribution(.25, .05).sample() * distance;
        }
        if (distance >= 400 && distance < 1000) {
            return new NormalDistribution(.2, .03).sample() * distance;
        }
        if (distance >= 1000 && distance < 3000) {
            return new NormalDistribution(.15, .05).sample() * distance;
        } else {
            return new NormalDistribution(.1, .02).sample() * distance;
        }
    }

    public double ecoPlus() {
        NormalDistribution gauss = new NormalDistribution(1.2, .1);
        return gauss.sample() * economyClass();
    }

    public double firstClass() {
        NormalDistribution gauss = new NormalDistribution(3.7, 1.2);
        return gauss.sample() * economyClass();
    }

    @NotNull
    @Override
    public String toString() {
        return "FlightPricer{" +
                "flight=" + flight +
                '}';
    }
}
