/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/**
 * (c) Erik Malmstrom-Partridge 2014 This code is provided as-is without any
 * warranty This source code or its compiled program may not be redistributed in
 * any way
 *
 */
package com.takeoffsim.models.economics;

import com.takeoffsim.models.aircraft.AircraftType;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.round;

/**
 * @author Erik
 */
public class FuelBurn {

    /**
     *
     * @param a the aircraft type
     * @param distance the distance of the flight
     * @param initWeight the starting weight of the aircraft
     * @return the fuel burn in pounds, rounded down to the ones place.
     */
    public static int fuelBurn(@NotNull AircraftType a, int distance, int initWeight) {
        int time = time(a, distance);
        int fuelBurn = (int) round(a.getSfc() * a.getNumberOfEngines() * initWeight * 20);
        double hours = time / 60.0;

        return (int) (fuelBurn * hours);
    }

    /**
     *
     * @param a the aircraft type
     * @param distance the distance of the flight in miles..
     * @return the flying time in minutes
     */
    public static int time(@NotNull AircraftType a, int distance) {
        int distanceToGo = distance;
        int time = 0;
        if (distance < 50) {
            time += 10;
            time += 10;
            distanceToGo -= 20;
        } else if (distance < 200) {
            time += 15;
            distanceToGo -= 35;
            time += 20;
            distanceToGo *= .6;
        } else {
            time += 20;
            distanceToGo -= 55;
            distanceToGo -= 100;
            time += 25;
        }
        time += (distanceToGo / a.getCruiseSpeed()) * 60;
        return time;

    }
}
