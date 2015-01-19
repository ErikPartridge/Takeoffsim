/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.aircraft;

import com.takeoffsim.models.airline.Flight;
import com.takeoffsim.models.airport.Airport;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Erik in 09, 2014.
 */
public class AircraftSchedule {

    private Airplane airplane;

    private static final double IDEAL_RATIO = .75;

    public static final int LENGTH_OF_WEEK = 10080;
    private ArrayList<Flight> flights;

    private AircraftSchedule(ArrayList<Flight> flights, Airplane plane) {
        this.setFlights(flights);
        this.setAirplane(plane);
    }

    /**
     * @param air airplane to schedule for
     * @return an empty aircraft schedule for this airplane
     */
    public static AircraftSchedule emptySchedule(Airplane air){
        return new AircraftSchedule(new ArrayList<>(), air);
    }

    /**
     *
     * @return if the length of the schedule fits into the week
     */
    boolean isLengthValid() {
        int minutes = getMinutes();
        return minutes <= LENGTH_OF_WEEK - 10;
    }

    /**
     *
     * @return for each airport it arrives, does it also depart that airport. Full circle.
     */
    boolean isAirportsValid() {
        //List of departure airports and arrival airports
        Collection<Airport> departures = new ArrayList<>();
        Collection<Airport> arrivals = new ArrayList<>();
        //fill the lists
        for (Flight flight : getFlights()) {
            departures.add(flight.getRoute().getDeparts());
            arrivals.add(flight.getRoute().getArrives());
        }
        //If you don't depart as many airports as you arrive, something's wrong
        if (departures.size() != arrivals.size()) {
            return false;
        }
        //For each airport the plane departs, it has to arrive there too
        for (Airport d : departures) {
            boolean failure = !arrivals.remove(d);
            if (failure) {
                return false;
            }
        }
        return arrivals.size() == 0;
    }

    /**
     *
     * @return is the schedule valid on the most basic level
     */
    public boolean isValid() {
        return isLengthValid() && isAirportsValid();
    }

    /**
     * @return score between 1 and 100.0 inclusive, higher is better, 100 is ideal.
     */
    public double score() {

        double ideal = IDEAL_RATIO * LENGTH_OF_WEEK;
        if (getMinutes() == (int) ideal) {
            return 100.0d;
        } else if (getMinutes() < ideal) {
            return getMinutes() * 100.0d / ideal;
        } else return LENGTH_OF_WEEK - getMinutes() > 0 ? 100.0d - LENGTH_OF_WEEK - Math.pow(getMinutes(), 2) : 0.0d;
    }

    /**
     *
     * @return the total number of minutes used by this schedule.
     */
    public int getMinutes() {
        int minutes = 0;
        for (Flight flight : getFlights()) {
            minutes += flight.getFlyingTime() + flight.getRoute().getType().getTurntime();
        }
        return minutes;
    }

    /**
     * Cancel every flight in the schedule
     */
    public void cancel(){
        flights.forEach(com.takeoffsim.models.airline.Flight::cancel);
    }


    /**
     *
     * @return the airplane involved in the schedule
     */
    public Airplane getAirplane() {
        return airplane;
    }

    void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    /**
     * @return The flights that make up this schedule
     */
    public List<Flight> getFlights() {
        return Collections.unmodifiableList(flights);
    }

    /**
     *
     * @param flights the list of flights to set the list to.
     */
    public void setFlights(ArrayList<Flight> flights) {
        //noinspection AssignmentToCollectionOrArrayFieldFromParameter
        this.flights = flights;
    }

    /**
     *
     * @param flight the flight to addFlight to the list
     */
    public void addFlight(Flight flight){
        this.flights.add(flight);
    }

    /**
     * Pulls a random flight from the list
     * @return the randomly selected, now removed, Flight
     */
    public Flight removeRandom(){
        return this.flights.remove(new MersenneTwister().nextInt(flights.size()));
    }

    /**
     *
     * @param f the flight to remove from the set of flights
     */
    public void remove(Flight f){
        this.flights.remove(f);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AircraftSchedule)) return false;

        AircraftSchedule that = (AircraftSchedule) obj;

        if (airplane != null ? !airplane.equals(that.getAirplane()) : that.getAirplane() != null) return false;
        return !(flights != null ? !flights.equals(that.getFlights()) : that.getFlights() != null);

    }

    @Override
    public int hashCode() {
        int result = airplane != null ? airplane.hashCode() : 0;
        result = 31 * result + (flights != null ? flights.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AircraftSchedule{" +
                "airplane=" + airplane +
                ", flights=" + flights +
                '}';
    }
}
