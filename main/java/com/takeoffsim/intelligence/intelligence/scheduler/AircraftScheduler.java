/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.intelligence.intelligence.scheduler;

import com.takeoffsim.airport.Airport;
import com.takeoffsim.models.aircraft.AircraftSchedule;
import com.takeoffsim.models.aircraft.AircraftType;
import com.takeoffsim.models.aircraft.Airplane;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Flight;
import com.takeoffsim.models.airline.Route;
import com.takeoffsim.models.world.Time;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.EvaluatedCandidate;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.operators.ListOrderMutation;
import org.uncommons.watchmaker.framework.selection.RankSelection;
import org.uncommons.watchmaker.framework.selection.TruncationSelection;
import org.uncommons.watchmaker.framework.termination.Stagnation;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Erik in 11, 2014.
 */
@CommonsLog
public final class AircraftScheduler {

    private final Airline airline;

    public AircraftScheduler(Airline a){
        this.airline = a;
    }

    /**
     *
     */
    public void schedule() {
        //List of finished schedules
        Collection<AircraftSchedule> finishedSchedules = new ArrayList<>();
        airline.getFleet().getFleet().stream().parallel().forEach(sf -> finishedSchedules.addAll(scheduleSubfleet(sf.getAcfType())));
        finishedSchedules.stream().parallel().forEach(this::setTimes);

        throw new UnsupportedOperationException("No finish");

        //TODO finalize
    }


    /**
     *
     * @param as the aircraft schedule to set the times of the flights
     */
    @SuppressWarnings("FeatureEnvy")
    void setTimes(AircraftSchedule as){
        //How much extra time between each flight?
        int flex = AircraftSchedule.LENGTH_OF_WEEK - as.getMinutes() - 6 * 60 - (as.getFlights().size() - 1) * as.getAirplane().getType().getTurntime();
        LocalDateTime current = Time.getDateTimeInstance().plusHours(6);
        sortFlights(as);
        List<Flight> flights = as.getFlights();
        for(int i = 0; i < as.getFlights().size(); i++ ){
            if(i != 0){
                current = current.plusMinutes(flights.get(i).getAircraft().getType().getTurntime());
            }
            flights.get(i).setDepartsGmt(current);
            current = current.plusMinutes(flights.get(i).getFlyingTime());
            flights.get(i).setArrivesGmt(current);
        }

    }

    /**
     *
     * @param flights the list of flights to score
     * @return the score of this list of flights
     */
    @SuppressWarnings("FeatureEnvy")
    private int scoreListFlight(List<Flight> flights){
        if(flights.size() == 0){
            return 0;
        }
        Airport current = flights.get(0).getRoute().getArrives();
        int score = 0;
        for(int i = 1; i < flights.size(); i++){
            if(current.equals(flights.get(i).getRoute().getDeparts())){
                score += 2;
            }
            current = flights.get(i).getRoute().getArrives();
        }
        return score;
    }

    /**
     *
     * @param as the aircraft schedule to sort
     */
    @SuppressWarnings("FeatureEnvy")
    void sortFlights(AircraftSchedule as){
        //The engine that puts the flights in order
        EvolutionEngine<List<Flight>> engine = new GenerationalEvolutionEngine<>(
                new FlightOrderFactory(as.getFlights()), new ListOrderMutation<>(), new FitnessEvaluator<List<Flight>>() {
            @Override
            public double getFitness(List<Flight> candidate, List<? extends List<Flight>> population) {
                return scoreListFlight(candidate);
            }

            @Override
            public boolean isNatural() {
                return true;
            }
        },
                new TruncationSelection(.3), new MersenneTwisterRNG()
        );
        //Get me the best option
        List<Flight> list = engine.evolve(15, 3, new TargetFitness(2 * (as.getFlights().size() - 1) - .04, true), new Stagnation(20, true));
        //Turn it into a list, and reset the schedule's flights
        ArrayList<Flight> flts = new ArrayList<>();
        list.forEach(flts::add);
        as.setFlights(flts);
    }

    /**
     * WARNING this is one of the slowest methods in the application-- use carefully
     * @param type the aircraft type of the subfleet
     * @return a list of completed aircraft schedules
     */
    private Collection<AircraftSchedule> scheduleSubfleet(AircraftType type) {
        //Add all the flights that I have to schedule or cancel
        final ArrayList<Flight> toSchedule = new ArrayList<>();
        //filter to only routes for this subfleet
        List<Airplane> airplanes = new ArrayList<>();
        List<AircraftSchedule> finished = new ArrayList<>();
        //Fill out the airplanes
        airline.getFleet().getSubFleet(type.getIcao()).getAircraft().values().forEach(airplanes::add);
        //Fill out the flights
        airline.getRoutes().stream().
                filter(new RoutePredicate(type)).forEach(r ->
                toSchedule.addAll(r.makeWeeklyFlights(Time.getDateInstance())));

        //Create an evolutional engine to schedule (genetic)
        GenerationalEvolutionEngine<AircraftSchedule> engine = new GenerationalEvolutionEngine<>(
                new AircraftScheduleGenerator(toSchedule, airplanes), new AircraftSchedulerOperator(), new AircraftScheduleFitnessEvaluator(),
                new RankSelection(), new MersenneTwisterRNG());
        //Make it thread-- it needs it.
        engine.setSingleThreaded(false);

        //Will create the best list it can find, as long as the population keeps improving
        List<EvaluatedCandidate<AircraftSchedule>> end = engine.evolvePopulation(airplanes.size(), airplanes.size() / 2, new Stagnation(12, true, true));

        //If not good- cancel some flights... Probably should tell the airline's AI too.
        fixSchedules(end.stream().parallel().filter(candidate -> candidate.getFitness() < 10));

        //Fill the finished schedules list with good schedules!
        end.stream().forEach(sc -> finished.add(sc.getCandidate()));

        return finished;
    }

    /**
     * **WARNING** dangerous-- this WILL result in cancelled flights
     * @param schedules the list of schedules to fix
     * @return a fixed list
     */
    List<AircraftSchedule> fixSchedules(Stream<EvaluatedCandidate<AircraftSchedule>> schedules){
        List<AircraftSchedule> scheds = new ArrayList<>();
        schedules.parallel().forEach(s -> scheds.add(fixSchedule(s.getCandidate())));
        return scheds;
    }

    /**
     *
     * @param schedule the schedule to fix
     * @return a schedule that is fixed
     */
    @SuppressWarnings("FeatureEnvy")
    private AircraftSchedule fixSchedule(AircraftSchedule schedule){
        while(schedule.score() < 10.0d && schedule.getFlights().size() > 0){
            Flight f = schedule.removeRandom();
            Flight ret = null;
            try {
                ret = getReturn(f, schedule);
            }catch(InvalidParameterException e){
                log.debug(e);
                schedule.cancel();
                return schedule;
            }
            schedule.remove(ret);
            f.cancel();
            ret.cancel();
        }
        return schedule;
    }



    /**
     *
     * @param f the flight to search for the return
     * @param s the schedule that contains the return
     * @return the soonest return
     * @throws InvalidParameterException if there is no return flight in the schedule
     */
    @SuppressWarnings("FeatureEnvy")
    private Flight getReturn(Flight f, AircraftSchedule s) {

        Stream<Flight> allReturns = s.getFlights().stream().filter(t ->
                 t.getRoute().getDeparts().equals(f.getRoute().getArrives()) && t.getRoute().getArrives().equals(f.getRoute().getDeparts()));


        if(allReturns.count() == 1){
            return allReturns.findFirst().get();
        }else if(allReturns.count() == 0){
            throw new InvalidParameterException(f.toString() + " does not have a return flight in the provided schedule");
        }else{
            return allReturns.findAny().get();
        }

    }


    /**
     *
     * @param toSchedule the list of flights to schedule
     * @param airplanes the list of airplanes to allocate flights to
     * @return a list of random schedules
     */
    private List<AircraftSchedule> randomSchedule(Iterable<Flight> toSchedule, Iterable<Airplane> airplanes){
        RandomGenerator rand = new MersenneTwister();
        List<AircraftSchedule> schedules = new ArrayList<>();
        airplanes.forEach(a -> schedules.add(AircraftSchedule.emptySchedule(a)));
        toSchedule.forEach(flt -> schedules.get(rand.nextInt(schedules.size())).addFlight(flt));
        return schedules;
    }

    @Override
    public String toString() {
        return "AircraftScheduler{" +
                "airline=" + airline +
                '}';
    }


    private static class RoutePredicate implements Predicate<Route> {
        private final AircraftType type;

        public RoutePredicate(AircraftType type) {
            this.type = type;
        }

        @Override
        public boolean test(Route t) {
            return t.getType().equals(type);
        }
    }
}
