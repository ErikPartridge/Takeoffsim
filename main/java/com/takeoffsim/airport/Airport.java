/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014.
 */

package com.takeoffsim.airport;

import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Flight;
import com.takeoffsim.models.world.Country;
import com.takeoffsim.models.world.Region;
import lombok.NonNull;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@CommonsLog
public class Airport implements Serializable, Comparable<Airport> {

    static final long serialVersionUID = -1591041241L;
    //An airport, and everything needed to make it function w/accessors.
    private String name;
    private double latitude;
    private double longitude;
    @NonNull
    private String icao;
    private String iata;
    @NonNull
    private final Map<String, Flight> flights = new ConcurrentHashMap<>(60);
    @NonNull
    private Country country;
    private double delayFactor = 0;
    private double demandBonus = 0;
    @NonNull
    private ZoneId timeZone;
    @NotNull
    @NonNull
    private final ArrayList<Region> regions = new ArrayList<>();
    private boolean slotControlled;

    private double allocatedDemand;
    @NotNull
    private final ArrayList<Gate> gates = new ArrayList<>();
    private GateBuilder builder;
    @NotNull
    private ArrayList<Airline> serves = new ArrayList<>();

    @NotNull
    private ArrayList<Runway> runways = new ArrayList<>();

    /*
    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof TryTakeoff){
            TryTakeoff tt = (TryTakeoff) o;
            boolean foundRunway = false;
            for(Runway r: runways){
                if(r.isAvailable() && r.length() > tt.getAirplane().getType().getDepartureRunway()){
                    r.reserve();
                    getSender().tell(true, this.getSelf());
                    foundRunway = true;
                    break;
                }
            }
            if(!foundRunway){
                getSender().tell(false, this.getSelf());
            }
        }else if(o instanceof TryLanding){
            TryLanding tl = (TryLanding) o;
            boolean foundRunway = false;
            for(Runway r: runways){
                if(r.isAvailable() && r.length() > tl.getAirplane().getType().getArrivalRunway()){
                    r.reserve();
                    getSender().tell(true, this.getSelf());
                    foundRunway = true;
                    break;
                }
            }
            if(!foundRunway){
                getSender().tell(false, this.getSelf());
            }
        }
    }*/

    private Airport() {
        setName("Not initialized");
    }


    public Airport(String name, double latitude, double longitude, String icao, String iata,
                   Country country, double delayFactor, double demandBonus, ZoneId timeZone,
                   int gs) {
        this.setName(name);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setIcao(icao);
        this.setIata(iata);
        this.setCountry(country);
        this.setDelayFactor(delayFactor);
        this.setDemandBonus(demandBonus);
        this.setTimeZone(timeZone);
        setBuilder(new GateBuilder(this));
        getBuilder().makeGates(gs).forEach(gates::add);
    }

    /*
    * @returns number of flights
    */
    public int getNumFlights() {
        return flights.values().size();
    }


    @NotNull
    @Override
    public String toString() {
        return "Airport{" +
                "name='" + getName() + '\'' +
                ", latitude=" + getLatitude() +
                ", longitude=" + getLongitude() +
                ", icao='" + getIcao() + '\'' +
                ", iata='" + getIata() + '\'' +
                ", country=" + getCountry() +
                ", delayFactor=" + getDelayFactor() +
                ", demandBonus=" + getDemandBonus() +
                ", timeZone=" + getTimeZone() +
                ", regions=" + getRegions() +
                ", allocatedDemand=" + getAllocatedDemand() +
                ", gates=" + getGates() +
                ", builder=" + getBuilder() +
                ", serves=" + getServes() +
                '}';
    }

    @Override
    public int compareTo(@NotNull Airport o) {
        return (int) (getLatitude() * getLongitude() * 100 / 1 - o.getLatitude() * o.getLongitude() * 100 / 1);
    }


    synchronized String getName() {
        return name;
    }

    synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized double getLatitude() {
        return latitude;
    }

    synchronized void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public synchronized double getLongitude() {
        return longitude;
    }

    synchronized void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public synchronized String getIcao() {
        return icao;
    }

    synchronized void setIcao(String icao) {
        this.icao = icao;
    }

    public synchronized String getIata() {
        return iata;
    }

    synchronized void setIata(String iata) {
        this.iata = iata;
    }

    public synchronized Country getCountry() {
        return country;
    }

    synchronized void setCountry(Country country) {
        this.country = country;
    }

    synchronized double getDelayFactor() {
        return delayFactor;
    }

    synchronized void setDelayFactor(double delayFactor) {
        this.delayFactor = delayFactor;
    }

    synchronized double getDemandBonus() {
        return demandBonus;
    }

    synchronized void setDemandBonus(double demandBonus) {
        this.demandBonus = demandBonus;
    }

    synchronized ZoneId getTimeZone() {
        return timeZone;
    }

    synchronized void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }

    @NotNull
    synchronized ArrayList<Region> getRegions() {
        return regions;
    }

    public synchronized boolean isSlotControlled() {
        return slotControlled;
    }

    public synchronized void setSlotControlled(boolean slotControlled) {
        this.slotControlled = slotControlled;
    }

    public synchronized double getAllocatedDemand() {
        return allocatedDemand;
    }

    public synchronized void setAllocatedDemand(double allocatedDemand) {
        this.allocatedDemand = allocatedDemand;
    }

    @NotNull
    public synchronized List<Gate> getGates() {
        return gates;
    }

    synchronized GateBuilder getBuilder() {
        return builder;
    }

    synchronized void setBuilder(GateBuilder builder) {
        this.builder = builder;
    }

    @NotNull
    public synchronized List<Airline> getServes() {
        return serves;
    }

    @NotNull
    public synchronized ArrayList<Runway> getRunways() {
        return runways;
    }

    public Collection<Flight> getFlightsByAirline(Airline airline, ChronoLocalDate date){
        List<Flight> result = Collections.synchronizedList(new ArrayList<>());
        flights.values().parallelStream().forEach(new FlightConsumer(airline, date, result));

        return result;
    }

    private static class FlightConsumer implements Consumer<Flight> {
        private final Airline airline;
        private final ChronoLocalDate date;
        private final List<Flight> result;

        public FlightConsumer(Airline airline, ChronoLocalDate date, List<Flight> result) {
            this.airline = airline;
            this.date = date;
            this.result = result;
        }

        @Override
        public void accept(Flight t) {
            if(t.getAirline().equals(airline) && t.getDepartsGmt().toLocalDate().isEqual(date)){
                result.add(t);
            }
        }
    }
}

