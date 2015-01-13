/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014.
 */
/*
This class holds the primary properties of an airline, although it doesn't
actually do anything beyond that.
*/

package com.takeoffsim.models.airline;

import com.google.common.collect.HashBiMap;
import com.takeoffsim.airport.Airport;
import com.takeoffsim.models.airline.enums.*;
import com.takeoffsim.models.economics.Company;
import com.takeoffsim.models.people.Executive;
import com.takeoffsim.models.people.FlightAttendant;
import com.takeoffsim.models.people.Mechanic;
import com.takeoffsim.models.people.Pilot;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.HashBiMap.create;

/**
 * @author Erik
 * @since 0.3 alpha
 */

@CommonsLog
public class Airline extends Company implements Serializable {

    static final long serialVersionUID = 2093027591L;
    @NotNull
    private ArrayList<Airport> hubs = new ArrayList<>();

    private Fleet fleet;

    private HashBiMap<String, Flight> flights = create(800);

    private int reputation;

    private String iata;

    private String icao;

    private Money flightAttendantPay;

    private Money mechanicPay;

    private Money pilotPay;

    private ArrayList<FlightAttendant> flightAttendants;

    private ArrayList<Pilot> pilots;

    private ArrayList<Mechanic> mechanics;

    private ArrayList<Executive> executives;

    private ArrayList<Route> routes;

    private boolean human;

    private double connectionRate;

    private double hubPercent;

    private int aggressiveness;

    private ArrayList<Airline> codeShares;

    private Alliance alliance;

    private AircraftMentality aircraftMentality;

    private Mindset scope;

    private Type type;

    private Focus focus;

    private Phase phase;

    /**
     * No parameters, default pay set
     */
    public Airline() {
        this.setFlightAttendantPay(Money.parse("USD 40000"));
        this.setMechanicPay(Money.parse("USD 65000"));
        this.setPilotPay(Money.parse("USD 95000"));
        Logger.getGlobal().log(Level.INFO, "Made empty airline");
    }


    /**
     * Where are its hubs- Delta has DTW, ATL, MSP, JFK, SLC
     */
    @NotNull
    public synchronized ArrayList<Airport> getHubs() {
        return hubs;
    }

    public synchronized void setHubs(@NotNull ArrayList<Airport> hubs) {
        this.hubs = hubs;
    }

    /**
     * The airline's fleet
     */
    public synchronized Fleet getFleet() {
        return fleet;
    }

    public synchronized void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    /**
     * All of its flights operating or in operation, with take off in the next 24 hours
     */
    public synchronized HashBiMap<String, Flight> getFlights() {
        return flights;
    }

    public synchronized void setFlights(HashBiMap<String, Flight> flights) {
        this.flights = flights;
    }

    /**
     * Reputation affects passenger desire and contracts
     */
    public synchronized int getReputation() {
        return reputation;
    }

    public synchronized void setReputation(int reputation) {
        this.reputation = reputation;
    }

    /**
     * eg. DL
     */
    public synchronized String getIata() {
        return iata;
    }

    public synchronized void setIata(String iata) {
        this.iata = iata;
    }

    /**
     * eg. DAL
     */
    public synchronized String getIcao() {
        return icao;
    }

    public synchronized void setIcao(String icao) {
        this.icao = icao;
    }

    public synchronized Money getFlightAttendantPay() {
        return flightAttendantPay;
    }

    public synchronized void setFlightAttendantPay(Money flightAttendantPay) {
        this.flightAttendantPay = flightAttendantPay;
    }

    public synchronized Money getMechanicPay() {
        return mechanicPay;
    }

    public synchronized void setMechanicPay(Money mechanicPay) {
        this.mechanicPay = mechanicPay;
    }

    public synchronized Money getPilotPay() {
        return pilotPay;
    }

    public synchronized void setPilotPay(Money pilotPay) {
        this.pilotPay = pilotPay;
    }

    public synchronized ArrayList<FlightAttendant> getFlightAttendants() {
        return flightAttendants;
    }

    public synchronized void setFlightAttendants(ArrayList<FlightAttendant> flightAttendants) {
        this.flightAttendants = flightAttendants;
    }

    public synchronized ArrayList<Pilot> getPilots() {
        return pilots;
    }

    public synchronized void setPilots(ArrayList<Pilot> pilots) {
        this.pilots = pilots;
    }

    public synchronized ArrayList<Mechanic> getMechanics() {
        return mechanics;
    }

    public synchronized void setMechanics(ArrayList<Mechanic> mechanics) {
        this.mechanics = mechanics;
    }

    public synchronized ArrayList<Executive> getExecutives() {
        return executives;
    }

    public synchronized void setExecutives(ArrayList<Executive> executives) {
        this.executives = executives;
    }

    public synchronized ArrayList<Route> getRoutes() {
        return routes;
    }

    public synchronized void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public synchronized boolean isHuman() {
        return human;
    }

    public synchronized void setHuman(boolean human) {
        this.human = human;
    }

    public synchronized double getConnectionRate() {
        return connectionRate;
    }

    public synchronized void setConnectionRate(double connectionRate) {
        this.connectionRate = connectionRate;
    }

    public synchronized double getHubPercent() {
        return hubPercent;
    }

    public synchronized void setHubPercent(double hubPercent) {
        this.hubPercent = hubPercent;
    }

    public synchronized int getAggressiveness() {
        return aggressiveness;
    }

    public synchronized void setAggressiveness(int aggressiveness) {
        this.aggressiveness = aggressiveness;
    }

    public synchronized ArrayList<Airline> getCodeShares() {
        return codeShares;
    }

    public synchronized void setCodeShares(ArrayList<Airline> codeShares) {
        this.codeShares = codeShares;
    }

    public synchronized Alliance getAlliance() {
        return alliance;
    }

    public synchronized void setAlliance(Alliance alliance) {
        this.alliance = alliance;
    }

    @Override
    public synchronized Money valueOfAssets(){
        Money amount = Money.of(CurrencyUnit.USD, 0);
        fleet.getFleet().parallelStream().forEach(sf ->
                sf.getAircraft().values().forEach(acf ->
                    amount.plus(acf.value())
        ));
        super.getGates().forEach(gate -> amount.plus(amount));
        super.getContracts().forEach(contract -> contract.getBills().forEach(bill ->
        amount.plus(bill.getAmount())));
        return amount;
    }





}

