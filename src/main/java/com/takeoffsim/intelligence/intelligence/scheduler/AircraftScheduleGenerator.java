/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
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
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Erik in 11, 2014.
 */
public class AircraftScheduleGenerator implements CandidateFactory<AircraftSchedule> {

    private List<Flight> flights;

    private List<Airplane> airplanes;

    public AircraftScheduleGenerator(List<Flight> flights, List<Airplane> airplanes){
        this.flights = flights;
        this.airplanes = airplanes;
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

        return schedules;
    }

    private Stream<Airplane> airplaneOptions(List<Airplane> used){
        return airplanes.stream().filter(new Predicate<Airplane>() {
            @Override
            public boolean test(Airplane airplane) {
                return !used.contains(airplane);
            }
        });
    }

    private Stream<Flight> flightOptions(List<Flight> used){
        return flights.stream().filter(new Predicate<Flight>() {
            @Override
            public boolean test(Flight flight) {
                return !used.contains(flight);
            }
        });
    }
    @Override
    public List<AircraftSchedule> generateInitialPopulation(int populationSize, Collection<AircraftSchedule> seedCandidates, Random rng) {
        List<AircraftSchedule> schedules = new ArrayList<>();

        List<AircraftSchedule> newSchedules = new ArrayList<>();
        List<Airplane> usedAirplanes = new ArrayList<>();
        List<Flight> usedFlights = new ArrayList<>();

        int size = populationSize;
        seedCandidates.forEach(seed -> schedules.add(seed));
        seedCandidates.forEach(s -> usedAirplanes.add(s.getAirplane()));
        seedCandidates.forEach(s -> usedFlights.addAll(s.getFlights()));
        size -= seedCandidates.size();
        for(int i = 0; i < size; i ++){
            Stream<Airplane> airplaneOptions = airplaneOptions(usedAirplanes);
            Airplane airplane = airplaneOptions.findAny().get();
            newSchedules.add(AircraftSchedule.emptySchedule(airplane));
            usedAirplanes.add(airplane);
        }

        flightOptions(usedFlights).forEach(new Consumer<Flight>() {
            @Override
            public void accept(Flight flight) {
                usedFlights.add(flight);
                newSchedules.get(rng.nextInt(newSchedules.size())).add(flight);
            }
        });

        schedules.addAll(newSchedules);

        return schedules;

    }

    @Override
    public AircraftSchedule generateRandomCandidate(Random rng) {
        return generateInitialPopulation(airplanes.size(), rng).get(rng.nextInt(airplanes.size()));
    }
}
