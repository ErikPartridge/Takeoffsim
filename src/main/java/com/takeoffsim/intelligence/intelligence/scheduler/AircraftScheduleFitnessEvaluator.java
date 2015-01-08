/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.intelligence.intelligence.scheduler;

import com.takeoffsim.models.aircraft.AircraftSchedule;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;

/**
 * Created by Erik in 11, 2014.
 */
public class AircraftScheduleFitnessEvaluator implements FitnessEvaluator<AircraftSchedule>{
    @Override
    public double getFitness(AircraftSchedule candidate, List<? extends AircraftSchedule> population) {
        return candidate.score();
    }

    @Override
    public boolean isNatural() {
        return true;
    }
}
