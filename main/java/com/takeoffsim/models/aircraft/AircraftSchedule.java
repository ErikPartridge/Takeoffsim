/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.models.aircraft;

import com.takeoffsim.models.airline.Flight;
import com.takeoffsim.airport.Airport;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.ArrayList;

/**
 * Created by Erik in 09, 2014.
 */
public class AircraftSchedule {

    private Airplane airplane;

    public static final int LENGTH_OF_WEEK = 10080;
    private ArrayList<Flight> flights;

    public AircraftSchedule(ArrayList<Flight> flights, Airplane plane) {
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
    public boolean lengthValid() {
        int minutes = getMinutes();
        return minutes <= LENGTH_OF_WEEK - 10;
    }

    /**
     *
     * @return for each airport it arrives, does it also depart that airport. Full circle.
     */
    public boolean airportsValid() {
        //List of departure airports and arrival airports
        ArrayList<Airport> departures = new ArrayList<>();
        ArrayList<Airport> arrivals = new ArrayList<>();
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
            boolean success = arrivals.remove(d);
            if (!success) {
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
        return lengthValid() && airportsValid();
    }

    /**
     * @return score between 1 and 100.0 inclusive, higher is better, 100 is ideal.
     */
    public double score() {

        double ideal = .75 * LENGTH_OF_WEEK;
        if (getMinutes() == (int) ideal) {
            return 100.0d;
        } else if (getMinutes() < ideal) {
            return getMinutes() * 100.0d / ideal;
        } else if (LENGTH_OF_WEEK - getMinutes() > 0) {
            return 100.0d - LENGTH_OF_WEEK - Math.pow(getMinutes(), 2);
        } else {
            return 0.0d;
        }
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
        for(Flight flight: flights){
            flight.cancel();
        }
    }


    /**
     *
     * @return the airplane involved in the schedule
     */
    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    /**
     * @return The flights that make up this schedule
     */
    public ArrayList<Flight> getFlights() {
        return flights;
    }

    /**
     *
     * @param flights the list of flights to set the list to.
     */
    public void setFlights(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    /**
     *
     * @param flight the flight to add to the list
     */
    public void add(Flight flight){
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
     * @return if the action was successful
     */
    public boolean remove(Flight f){
        return this.flights.remove(f);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AircraftSchedule)) return false;

        AircraftSchedule that = (AircraftSchedule) o;

        if (airplane != null ? !airplane.equals(that.airplane) : that.airplane != null) return false;
        if (flights != null ? !flights.equals(that.flights) : that.flights != null) return false;

        return true;
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
