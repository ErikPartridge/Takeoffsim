/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services.intelligence.intelligence.scheduler;

import com.takeoffsim.models.aircraft.AircraftSchedule;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;

/**
 * Created by Erik in 11, 2014.
 */
class AircraftScheduleFitnessEvaluator implements FitnessEvaluator<AircraftSchedule>{
    @Override
    public double getFitness(AircraftSchedule candidate, List<? extends AircraftSchedule> population) {
        return candidate.score();
    }

    @Override
    public boolean isNatural() {
        throw new UnsupportedOperationException("Failed");
    }
}
