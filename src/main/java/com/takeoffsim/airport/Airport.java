/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014.
 */

package com.takeoffsim.airport;

import akka.actor.UntypedActor;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Flight;
import com.takeoffsim.models.messages.TryLanding;
import com.takeoffsim.models.messages.TryTakeoff;
import com.takeoffsim.models.world.Country;
import com.takeoffsim.models.world.Region;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Airport extends UntypedActor implements Serializable, Comparable<Airport> {

    static final long serialVersionUID = -1591041241L;
    //An airport, and everything needed to make it function w/accessors.
    private String name;
    private double latitude;
    private double longitude;
    @NonNull
    private String icao;
    private String iata;
    @NonNull
    private ConcurrentHashMap<String, Flight> flights = new ConcurrentHashMap<>(60);
    @NonNull
    private Country country;
    private double delayFactor = 0;
    private double demandBonus = 0;
    @NonNull
    private ZoneId timeZone;
    @NotNull
    @NonNull
    private ArrayList<Region> regions = new ArrayList<>();
    private boolean slotControlled;
    @NotNull
    private ArrayList<Slot> slots = new ArrayList<>(600);
    private double allocatedDemand;
    @NotNull
    private ArrayList<Gate> gates = new ArrayList<>();
    private GateBuilder builder;
    @NotNull
    private ArrayList<Airline> serves = new ArrayList<>();

    @NotNull
    private ArrayList<Runway> runways = new ArrayList<>();

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
    }

    public Airport() {
        setName("Not initialized");
    }


    public Airport(String name, double latitude, double longitude, String icao, String iata,
                   Country country, double delayFactor, double demandBonus, ZoneId timeZone,
                   int gates) {
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
        this.setGates(getBuilder().makeGates(gates));
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
    public int compareTo(Airport o) {
        return (int) (getLatitude() * getLongitude() * 100 / 1 - o.getLatitude() * o.getLongitude() * 100 / 1);
    }


    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized double getLatitude() {
        return latitude;
    }

    public synchronized void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public synchronized double getLongitude() {
        return longitude;
    }

    public synchronized void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public synchronized String getIcao() {
        return icao;
    }

    public synchronized void setIcao(String icao) {
        this.icao = icao;
    }

    public synchronized String getIata() {
        return iata;
    }

    public synchronized void setIata(String iata) {
        this.iata = iata;
    }

    public synchronized Country getCountry() {
        return country;
    }

    public synchronized void setCountry(Country country) {
        this.country = country;
    }

    public synchronized double getDelayFactor() {
        return delayFactor;
    }

    public synchronized void setDelayFactor(double delayFactor) {
        this.delayFactor = delayFactor;
    }

    public synchronized double getDemandBonus() {
        return demandBonus;
    }

    public synchronized void setDemandBonus(double demandBonus) {
        this.demandBonus = demandBonus;
    }

    public synchronized ZoneId getTimeZone() {
        return timeZone;
    }

    public synchronized void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }

    @NotNull
    public synchronized ArrayList<Region> getRegions() {
        return regions;
    }

    public synchronized void setRegions(@NotNull ArrayList<Region> regions) {
        this.regions = regions;
    }

    public synchronized boolean isSlotControlled() {
        return slotControlled;
    }

    public synchronized void setSlotControlled(boolean slotControlled) {
        this.slotControlled = slotControlled;
    }

    @NotNull
    public synchronized ArrayList<Slot> getSlots() {
        return slots;
    }

    public synchronized void setSlots(@NotNull ArrayList<Slot> slots) {
        this.slots = slots;
    }

    public synchronized double getAllocatedDemand() {
        return allocatedDemand;
    }

    public synchronized void setAllocatedDemand(double allocatedDemand) {
        this.allocatedDemand = allocatedDemand;
    }

    @NotNull
    public synchronized ArrayList<Gate> getGates() {
        return gates;
    }

    public synchronized void setGates(@NotNull ArrayList<Gate> gates) {
        this.gates = gates;
    }

    public synchronized GateBuilder getBuilder() {
        return builder;
    }

    public synchronized void setBuilder(GateBuilder builder) {
        this.builder = builder;
    }

    @NotNull
    public synchronized ArrayList<Airline> getServes() {
        return serves;
    }

    public synchronized void setServes(@NotNull ArrayList<Airline> serves) {
        this.serves = serves;
    }


    @NotNull
    public synchronized ArrayList<Runway> getRunways() {
        return runways;
    }

    public synchronized void setRunways(@NotNull ArrayList<Runway> runways) {
        this.runways = runways;
    }

    public List<Flight> getFlightsByAirline(Airline airline, LocalDate date){
        List<Flight> result = Collections.synchronizedList(new ArrayList<>());
        flights.values().parallelStream().forEach(new Consumer<Flight>() {
            @Override
            public void accept(Flight flight) {
                if(flight.getAirline().equals(airline) && flight.getDepartsGmt().toLocalDate().isEqual(date)){
                    result.add(flight);
                }
            }
        });

        return result;
    }
}

