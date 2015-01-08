/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.intelligence.intelligence.scheduler;

import com.takeoffsim.models.aircraft.AircraftSchedule;
import com.takeoffsim.models.airline.Flight;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Erik in 11, 2014.
 */
public class AircraftSchedulerOperator implements EvolutionaryOperator<AircraftSchedule> {

    @Override
    public List<AircraftSchedule> apply(final List<AircraftSchedule> selectedCandidates, Random rng) {
        List<AircraftSchedule> candidates = selectedCandidates;
        ArrayList<Flight> removedFlights = new ArrayList<>();
        candidates.forEach(c -> removedFlights.add(c.removeRandom()));
        candidates.forEach(c -> c.add(removedFlights.remove(rng.nextInt(removedFlights.size()))));
        return candidates;
    }
}
