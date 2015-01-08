/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.intelligence.intelligence.scheduler;

import com.takeoffsim.models.airline.Flight;
import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Erik in 11, 2014.
 */
public class FlightOrderFactory extends AbstractCandidateFactory<List<Flight>> {

    private final List<Flight> startingList;

    public FlightOrderFactory(List<Flight> flights){
        this.startingList = flights;
    }
    @Override
    public List<Flight> generateRandomCandidate(Random rng) {
        Collections.shuffle(startingList);
        return startingList;
    }
}
