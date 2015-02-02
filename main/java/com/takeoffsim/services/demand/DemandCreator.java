/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.services.demand;

import com.takeoffsim.models.airline.GlobalRoute;
import com.takeoffsim.models.airline.GlobalRoutes;
import com.takeoffsim.models.airport.Airport;
import com.takeoffsim.models.airport.Airports;
import com.takeoffsim.models.world.Cities;
import com.takeoffsim.models.world.City;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Erik
 */
public class DemandCreator {

    /**
     * allocates demand and puts it on routes
     */
    public void createAllDemand() {
        demandAllocation();
        GlobalRoutes.listRoutes().parallelStream().forEach(g -> g.setAvailableDemand(g.getAvailableDemand() + createDemandOnRoute(g)));
    }

    /**
     * @param route the global route to create the demand for
     * @return demand on this route
     */
    public synchronized int createDemandOnRoute(@NotNull GlobalRoute route) {
        return Math.toIntExact(Math.round(RouteDemand.demand(route.getDepart(), route.getArrive())));
    }

    public void demandAllocation(){
        Stream<City> cities = Cities.cityStream().parallel();
        Stream<Airport> airports = Airports.cloneAirports().stream();
        cities.forEach(c -> allocateDemand(airports.filter(new DistancePredicate(c).invoke()),c));
    }

    /**
     * @param inRegion list of airports in the region
     * @param city     the city from which to allocate the demand
     */
    public synchronized void allocateDemand(@NotNull Stream<Airport> inRegion, @NotNull final City city) {
        ArrayList<AirportScore> scores = new ArrayList<>();
        double sumOfScores = 0;

        for (Airport a : inRegion.toArray(Airport[]::new)) {
            double score = a.getNumFlights() / Math.pow(a.distance(city.getLatitude(), city.getLongitude()), 2);
            sumOfScores += score;
            scores.add(new AirportScore(a, score));
        }

        for (AirportScore s : scores) {
            Airport apt = s.getAirport();
            double add = (s.getScore() / sumOfScores) * city.getPopulation() * 1.7 / 365.0;
            apt.setAllocatedDemand(apt.getAllocatedDemand() + add);
        }

    }

    private class DistancePredicate {
        private final City city;

        public DistancePredicate(City city) {
            this.city = city;
        }

        public Predicate<Airport> invoke() {
            return airport -> airport.distance(city.getLatitude(), city.getLongitude()) < 150;
        }
    }
}

@Data
class AirportScore{

    private double score;

    private final Airport airport;

    public AirportScore(Airport a){
        this.airport = a;
        this.score = 0;
    }

    public AirportScore(Airport a, double s){
        this.score = s;
        this.airport = a;
    }

    public void add(double amount){
        score += amount;
    }
}