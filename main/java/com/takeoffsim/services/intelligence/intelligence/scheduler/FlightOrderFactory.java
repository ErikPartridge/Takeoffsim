/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services.intelligence.intelligence.scheduler;

import com.takeoffsim.models.airline.Flight;
import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.*;

/**
 * Created by Erik in 11, 2014.
 */
class FlightOrderFactory extends AbstractCandidateFactory<List<Flight>> {

    private final List<Flight> startingList = new ArrayList<>();

    FlightOrderFactory(Collection<Flight> flights){
        this.startingList.addAll(flights);
    }
    @Override
    public List<Flight> generateRandomCandidate(Random rng) {
        Collections.shuffle(startingList);
        //noinspection ReturnOfCollectionOrArrayField
        return startingList;
    }

    @Override
    public String toString() {
        return "FlightOrderFactory{" +
                "startingList=" + startingList +
                '}';
    }
}
