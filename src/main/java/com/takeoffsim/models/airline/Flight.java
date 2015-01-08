/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014.
 */

package com.takeoffsim.models.airline;


import com.takeoffsim.models.aircraft.Airplane;
import com.takeoffsim.models.world.Time;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Erik
 */
public @Data
class Flight implements Serializable {

    static final long serialVersionUID = 10000312411L;

    private volatile int seatsFull;

    private int flyingTime;

    private int pointToPoint;

    //A flight. Not a route, but a single point to point flight.
    private Airplane aircraft;

    private LocalDateTime departsGmt;

    private LocalDateTime actualDeparture;

    private LocalDateTime takeoff;

    private LocalDateTime actualTakeoff;

    private LocalDateTime arrivesGmt;

    private LocalDateTime actualArrival;

    private LocalDateTime now = Time.currentTime;

    private boolean cancelled;

    private double delayed;

    private LocalDate date;

    private Route route;

    private double fuelBurn;

    /**
     * @param acf       the aircraft that will operate this flight
     * @param seatsFull the number of seats already filled
     * @param date      the date
     * @param r         the route
     */
    public Flight(Airplane acf, int seatsFull, LocalDate date, @NotNull Route r) {
        flyingTime = r.getFlightTime();
        this.date = date;
        this.route = r;
        this.seatsFull = seatsFull;
        this.aircraft = acf;
        FlightQueue.addToDepartQueue(this);
    }

    public Flight(int seatsFull, LocalDate date, Route r){
        this.seatsFull = seatsFull;
        this.date = date;
        this.route = r;

    }

    public Airline getAirline() {
        return route.getAirline();
    }

    /**
     *
     * @param time this makes the airplane depart
     */
    public void depart(LocalDateTime time) {
        //
        setActualDeparture(time);
        setArrivesGmt(getActualDeparture());
        arrivesGmt = departsGmt.plusMinutes(flyingTime);
        getAircraft().setLocation(null);
        setCancelled(false);
        FlightQueue.addToArriveQueue(this);

    }


    public void cancel(){
        FlightQueue.removeFromDepartQuere(this);
        this.cancelled = true;
    }

    /**
     *
     * @param currentTime this makes the airplane arrive
     */
    public void arrive(LocalDateTime currentTime) {
        this.aircraft.setLocation(route.getArrives());
        actualArrival = currentTime;
        aircraft.getFlights().remove(0);
    }

    @NotNull
    @Override
    public String toString() {
        return "Flight{" +
                "aircraft=" + aircraft +
                ", departsGmt=" + departsGmt +
                ", actualDeparture=" + actualDeparture +
                ", takeoff=" + takeoff +
                ", actualTakeoff=" + actualTakeoff +
                ", arrivesGmt=" + arrivesGmt +
                ", actualArrival=" + actualArrival +
                ", now=" + now +
                ", cancelled=" + cancelled +
                ", delayed=" + delayed +
                ", seatsFull=" + seatsFull +
                ", date=" + date +
                ", route=" + route +
                ", fuelBurn=" + fuelBurn +
                ", flyingTime=" + flyingTime +
                ", pointToPoint=" + pointToPoint +
                '}';
    }

    /**
     *
     * @param seats the number of seats to book
     * @return the number of seats that could not be booked
     */
    public synchronized int book(int seats){
        for(int i = 0; i < seats; i++) {
            if(seatsFull == getAircraft().getType().getMaxEconomySeats()){
                return seats - i;
            }
            this.getRoute().getAirline().pay(route.getEcoPrice());
            seatsFull++;
        }
        return 0;
    }



}