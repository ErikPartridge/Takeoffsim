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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Erik
 * @since 0.3 alpha
 */

@SuppressWarnings("ClassNamePrefixedWithPackageName")
@CommonsLog
public class Airline extends Company implements Serializable {

    static final long serialVersionUID = 2093027591L;

    private final ArrayList<Airport> hubs = new ArrayList<>();

    private Fleet fleet;

    private final Map<String, Flight> flights = new ConcurrentHashMap<>();

    private int reputation;

    private String iata;

    private String icao;

    private Money flightAttendantPay;

    private Money mechanicPay;

    private Money pilotPay;

    private List<FlightAttendant> flightAttendants = new ArrayList<>();

    private List<Pilot> pilots = new ArrayList<>();

    private List<Mechanic> mechanics = new ArrayList<>();

    private List<Executive> executives = new ArrayList<>();

    private List<Route> routes = new ArrayList<>();

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
    public synchronized List<Airport> getHubs() {
        return Collections.unmodifiableList(hubs);
    }

    public synchronized void addHub(@NotNull Airport hub) {
        this.hubs.add(hub);
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
    public synchronized Map<String, Flight> getFlights() {
        return Collections.unmodifiableMap(flights);
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

    public synchronized List<FlightAttendant> getFlightAttendants() {
        return Collections.unmodifiableList(flightAttendants);
    }


    public synchronized List<Pilot> getPilots() {
        return Collections.unmodifiableList(pilots);
    }


    public synchronized List<Mechanic> getMechanics() {
        return Collections.unmodifiableList(mechanics);
    }

    public synchronized List<Executive> getExecutives() {
        return Collections.unmodifiableList(executives);
    }

    public synchronized List<Route> getRoutes() {
        return Collections.unmodifiableList(routes);
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

    public synchronized List<Airline> getCodeShares() {
        return Collections.unmodifiableList(codeShares);
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


    @Override
    public String toString() {
        return "Airline{" +
                "hubs=" + hubs +
                ", fleet=" + fleet +
                ", flights=" + flights +
                ", reputation=" + reputation +
                ", iata='" + iata + '\'' +
                ", icao='" + icao + '\'' +
                ", flightAttendantPay=" + flightAttendantPay +
                ", mechanicPay=" + mechanicPay +
                ", pilotPay=" + pilotPay +
                ", flightAttendants=" + flightAttendants +
                ", pilots=" + pilots +
                ", mechanics=" + mechanics +
                ", executives=" + executives +
                ", routes=" + routes +
                ", human=" + human +
                ", connectionRate=" + connectionRate +
                ", hubPercent=" + hubPercent +
                ", aggressiveness=" + aggressiveness +
                ", codeShares=" + codeShares +
                ", alliance=" + alliance +
                ", aircraftMentality=" + aircraftMentality +
                ", scope=" + scope +
                ", type=" + type +
                ", focus=" + focus +
                ", phase=" + phase +
                '}';
    }

    public void addHubs(Collection<Airport> list) {
        hubs.addAll(list);
    }

    public void addCodeShares(Collection<Airline> list){
        codeShares.addAll(list);
    }
}

