/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.logic;

import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.GlobalRoute;
import com.takeoffsim.models.airline.Route;
import com.takeoffsim.airport.Airport;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.takeoffsim.models.airline.GlobalRoutes.get;
import static com.takeoffsim.models.airline.GlobalRoutes.getFrequency;

/**
 * @author Erik
 */
public class RouteEvaluator {

    public int evaluateLaunchNew(@NotNull Airline a, @NotNull Route r) {
        ArrayList<Airport> apt = new ArrayList<>();
        apt = a.getHubs();

        int score = 0;
        int frequency = 0;
        frequency = getFrequency(r.getDeparts(), r.getArrives(), r.getAirline());
        GlobalRoute g = get(r.getDeparts(), r.getArrives());
        int availableDemand = g.getAvailableDemand();
        if (frequency > 0) {
            if (availableDemand > 50) {
                score += 5;
            }
            if (availableDemand > 100) {
                score += 7;
            }
            if (availableDemand > 180) {
                score += 10;
            }
            if (availableDemand > 250) {
                score += 12;
            }
            if (availableDemand > 400) {
                score += 45;
            }
        }
        if (frequency == 0) {
            if (availableDemand > 50) {
                score += 4;
            }
            if (availableDemand > 80) {
                score += 3;
            }
            if (availableDemand > 100) {
                score += 5;
            }
            if (availableDemand > 180) {
                score += 12;
            }
            if (availableDemand > 250) {
                score += 15;
            }
            if (availableDemand > 400) {
                score += 40;
            }

        }
        if (apt.contains(r.getDeparts())) {
            score += 6;
        }
        if (apt.contains(r.getArrives())) {
            score += 6;
        }
        if (apt.contains(r.getArrives()) && apt.contains(r.getDeparts())) {
            score += 3;
        }
        return score;
    }

}
