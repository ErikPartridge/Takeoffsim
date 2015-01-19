/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services.intelligence.intelligence.scheduler;

import com.takeoffsim.models.aircraft.AircraftSchedule;
import com.takeoffsim.models.airline.Flight;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Erik in 11, 2014.
 */
class AircraftSchedulerOperator implements EvolutionaryOperator<AircraftSchedule> {

    @Override
    public List<AircraftSchedule> apply(final List<AircraftSchedule> selectedCandidates, Random rng) {
        List<Flight> removedFlights = new ArrayList<>();
        selectedCandidates.forEach(c -> removedFlights.add(c.removeRandom()));
        selectedCandidates.forEach(c -> c.addFlight(removedFlights.remove(rng.nextInt(removedFlights.size()))));
        return selectedCandidates;
    }
}
