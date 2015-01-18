/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.intelligence.intelligence.scheduler;

import com.takeoffsim.models.aircraft.AircraftSchedule;
import com.takeoffsim.models.aircraft.Airplane;
import com.takeoffsim.models.airline.Flight;
import org.uncommons.watchmaker.framework.CandidateFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by Erik in 11, 2014.
 */
class AircraftScheduleGenerator implements CandidateFactory<AircraftSchedule> {

    private final List<Flight> flights = new ArrayList<>();

    private final List<Airplane> airplanes = new ArrayList<>();

    public AircraftScheduleGenerator(Iterable<Flight> fs, Iterable<Airplane> planes){
        fs.forEach(flights::add);
        planes.forEach(airplanes::add);
    }
    @Override
    public List<AircraftSchedule> generateInitialPopulation(int populationSize, Random rng) {
        List<AircraftSchedule> schedules = new ArrayList<>();

        List<Airplane> usedAirplanes = new ArrayList<>();
        List<Flight> usedFlights = new ArrayList<>();

        for(int i = 0; i < populationSize; i ++){
            Stream<Airplane> airplaneOptions = airplaneOptions(usedAirplanes);
            Airplane airplane = airplaneOptions.findAny().get();
            schedules.add(AircraftSchedule.emptySchedule(airplane));
            usedAirplanes.add(airplane);
        }
        //TODO review
        return schedules;
    }

    private Stream<Airplane> airplaneOptions(Collection<Airplane> used){
        return airplanes.stream().filter(t -> !used.contains(t));
    }

    private Stream<Flight> flightOptions(Collection<Flight> used){
        return flights.stream().filter(t-> !used.contains(t));
    }
    @Override
    public List<AircraftSchedule> generateInitialPopulation(int populationSize, Collection<AircraftSchedule> seedCandidates, Random rng) {
        List<AircraftSchedule> schedules = new ArrayList<>();

        List<AircraftSchedule> newSchedules = new ArrayList<>();
        Collection<Airplane> usedAirplanes = new ArrayList<>();
        Collection<Flight> usedFlights = new ArrayList<>();

        int size = populationSize;
        seedCandidates.forEach(schedules::add);
        seedCandidates.forEach(s -> usedAirplanes.add(s.getAirplane()));
        seedCandidates.forEach(s -> usedFlights.addAll(s.getFlights()));
        size -= seedCandidates.size();
        for(int i = 0; i < size; i ++){
            Stream<Airplane> airplaneOptions = airplaneOptions(usedAirplanes);
            Airplane airplane = airplaneOptions.findAny().get();
            newSchedules.add(AircraftSchedule.emptySchedule(airplane));
            usedAirplanes.add(airplane);
        }

        flightOptions(usedFlights).forEach(new FlightConsumer(usedFlights, newSchedules, rng));

        schedules.addAll(newSchedules);

        return schedules;

    }

    @Override
    public AircraftSchedule generateRandomCandidate(Random rng) {
        return generateInitialPopulation(airplanes.size(), rng).get(rng.nextInt(airplanes.size()));
    }

    @Override
    public String toString() {
        return "AircraftScheduleGenerator{" +
                "flights=" + flights +
                ", airplanes=" + airplanes +
                '}';
    }

    private static class FlightConsumer implements Consumer<Flight> {
        private final Collection<Flight> usedFlights;
        private final List<AircraftSchedule> newSchedules;
        private final Random rng;

        public FlightConsumer(Collection<Flight> usedFlights, List<AircraftSchedule> newSchedules, Random rng) {
            this.usedFlights = usedFlights;
            this.newSchedules = newSchedules;
            this.rng = rng;
        }

        @Override
        public void accept(Flight t) {
            usedFlights.add(t);
            newSchedules.get(rng.nextInt(newSchedules.size())).addFlight(t);
        }
    }
}
