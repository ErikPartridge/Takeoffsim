/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.takeoffsim.models.aircraft.AircraftType;
import com.takeoffsim.airport.Airport;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Erik
 */
public
@Data
class Route implements Serializable {
    static final long serialVersionUID = -9301234123L;
    // A route between two airports. Ex. if DAL15 goes CDG-PAR daily it is a route.
    private String flightNumber;

    @NonNull
    private Airport departs;

    @NonNull
    private Airport arrives;

    @NonNull
    private AircraftType type;

    @NonNull
    private Airline airline;

    private double onTime;

    private double delayed;

    private double cancelled;

    private double onTimePercent;


    @NotNull
    private transient ArrayList<Flight> flights = new ArrayList<>();


    @NotNull
    private boolean[] operates = new boolean[7];

    private double bizPrice;

    private double firstPrice;

    private double ecoPrice;

    private double ecoplusPrice;

    private int flightTime;

    private double revenue;


    public Route(String flightNumber, Airport departs, Airport arrives, AircraftType type, Airline airline,
                 double onTimePercent, double bizPrice, double firstPrice, double ecoPrice, double ecoplusPrice) {
        this.flightNumber = flightNumber;
        this.departs = departs;
        this.arrives = arrives;
        this.type = type;
        this.airline = airline;
        this.onTimePercent = onTimePercent;
        this.bizPrice = bizPrice;
        this.firstPrice = firstPrice;
        this.ecoPrice = ecoPrice;
        this.ecoplusPrice = ecoplusPrice;
        this.revenue = 0;
    }


    public double getDistance() {
        return LatLngTool.distance(new LatLng(departs.getLatitude(), departs.getLongitude()), new LatLng(arrives.getLatitude(), arrives.getLongitude()), LengthUnit.KILOMETER);
    }

    public ArrayList<Flight> makeWeeklyFlights(LocalDate firstDayOfWeek){
        ArrayList<Flight> flts = new ArrayList<>(7);
        for(int i = 0; i < operates.length; i++){
            if(operates[i]){
                flts.add(new Flight(0, firstDayOfWeek.plusDays(i), this));
            }
        }
        return flts;
    }

    @NotNull
    @Override
    public String toString() {
        return "Route{" +
                "flightNumber='" + flightNumber + '\'' +
                ", departs=" + departs +
                ", arrives=" + arrives +
                ", type=" + type +
                ", airline=" + airline +
                ", onTime=" + onTime +
                ", delayed=" + delayed +
                ", cancelled=" + cancelled +
                ", onTimePercent=" + onTimePercent +
                ", flights=" + flights +
                ", operates=" + Arrays.toString(operates) +
                ", bizPrice=" + bizPrice +
                ", firstPrice=" + firstPrice +
                ", ecoPrice=" + ecoPrice +
                ", ecoplusPrice=" + ecoplusPrice +
                ", flightTime=" + flightTime +
                ", revenue=" + revenue +
                '}';
    }

}
