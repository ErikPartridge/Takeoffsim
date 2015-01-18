/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014
 */

package com.takeoffsim.models.airline;


import com.jcabi.aspects.Cacheable;
import com.takeoffsim.intelligence.intelligence.scheduler.AircraftScheduler;
import com.takeoffsim.models.aircraft.AircraftTypeMaintenance;
import com.takeoffsim.models.aircraft.Airplane;
import com.takeoffsim.models.economics.UsedAircraftMarket;
import com.takeoffsim.models.people.*;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Erik
 * @since 0.3 alpha
 */
public final class Airlines implements Serializable {
    //List of all the airlines, single instance, has methods for getting airlines

    static final long serialVersionUID = 465599813L;
    private static final Map<String, Airline> airlines = new ConcurrentHashMap<>(400);
    private static final Map<String, String> icaos = new ConcurrentHashMap<>(400);

    private Airlines() {
    }

    /**
     *
     * @return a clone of the airlines values
     */
    public synchronized static List<Airline> cloneAirlines(){
        ArrayList<Airline> aln = new ArrayList<>(400);
        airlines.values().stream().forEach(aln::add);
        return aln;
    }


    /**
     *
     * @param time the current time, probably do this every week
     */
    public static void iteration(LocalDateTime time){
        assert airlines != null;
        assert icaos != null;
        Stream<Airline> nonHumanAirlines = airlines.values().stream().filter(a -> !a.isHuman());
        nonHumanAirlines.parallel().forEach(a -> new AircraftScheduler(a).schedule());
        nonHumanAirlines.parallel().forEach(Airlines::hire);
        nonHumanAirlines.parallel().forEach(Airlines::maintenance);
        nonHumanAirlines.parallel().forEach(Airlines::evaluateOldRoutes);
        nonHumanAirlines.parallel().forEach(Airlines::acquirePlanes);
        nonHumanAirlines.parallel().forEach(Airlines::evaluateOldRoutes);
        nonHumanAirlines.parallel().forEach(Airlines::sellPlanes);
        nonHumanAirlines.parallel().forEach(Airlines::createNewRoutes);
    }
    /**
     * @param icao The icao code of the airline to get
     * @return the Airline with this icao code
     */

    @Nullable
    public static Airline getAirline(String icao) {
        return airlines.get(icao);
    }

    /**
     * @param icao - icao code of the airline
     * @param a    airline to put in map
     */
    public static void put(String icao, Airline a) {
        airlines.put(icao, a);
    }

    /**
     * @return the icao/airline hashmap
     */
    public static Map<String, Airline> getMap() {
        return Collections.unmodifiableMap(airlines);
    }


    /**
     * @param airline the name of the airline
     * @return the airline with this name
     */

    @Nullable
    public static Airline get(String airline) {
        String icao = icaos.get(airline);
        return airlines.get(icao);

    }

    /**
     * @param name name of the airline to put in the map
     * @param icao the icao code for the airline
     */
    public synchronized static void putIcao(String name, String icao) {
        icaos.put(name, icao);
    }

    /**
     * @throws UnsupportedOperationException Retires planes for all airlines not under human control
     */
    public static void retirePlanes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods,
        // choose Tools | Templates.
    }

    /**
     * Processes all airlines and sets the prices for their flights
     */
    public static void setPrices() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods,
        // choose Tools | Templates.
    }

    public static List<Airline> humanAirlines(){
        return airlines.values().stream().filter(a -> a.isHuman()).collect(Collectors.toList());
    }

    @Cacheable(lifetime = 5, unit = TimeUnit.SECONDS)
    public static Airline humanAirline(){
        for(Airline a: airlines.values()){
            if(a.isHuman())
                return a;
        }
        throw new NullPointerException("No human airline");
    }
    /**
     *
     * @param a the airline/fire to hire staff for
     */
    private static void hire(Airline a){
        double numPlanes = 1;
        double reqFas = 1;
        Fleet f = a.getFleet();
        for (Subfleet s : f.getFleet()) {
            numPlanes += s.getAircraft().values().size();
            reqFas += s.getRequiredFA();
        }
        while (a.getPilots().size() / numPlanes < 6) {
            Pilot p = GeneratePerson.createPilot(a);
            a.getPilots().add(p);
        }
        while (a.getFlightAttendants().size() / reqFas < 1) {
            FlightAttendant fa = GeneratePerson.createFlightAttendant(a);
            a.getFlightAttendants().add(fa);
        }
        while (a.getMechanics().size() / numPlanes < 1.5) {
            Mechanic m = GeneratePerson.createMechanic(a);
            a.getMechanics().add(m);
        }
        while (a.getExecutives().size() / reqFas < 1) {
            Executive e = GeneratePerson.createExecutive(a);
            a.getExecutives().add(e);
        }
        while (a.getPilots().size() / numPlanes > 7) {
            a.getPilots().remove(0);
        }
        while (a.getFlightAttendants().size() / reqFas > 1.2) {
            a.getFlightAttendants().remove(0);
        }
        while (a.getMechanics().size() / numPlanes > 2) {
            a.getMechanics().remove(0);
        }
        while (a.getExecutives().size() / reqFas < 1) {
            a.getExecutives().remove(0);
        }
    }

    /**
     *
     */
    static void maintenance(Airline a) {
        a.getFleet().getFleet().stream().parallel().forEach(sf ->
                sf.getAircraft().values().stream().parallel().forEach(Airlines::maintain));
    }

    private static void maintain(Airplane a){
        AircraftTypeMaintenance prof = a.getType().getMaintenanceProfile();
        if(a.getaCheck() > prof.getHoursA()){
            a.getOperator().pay(a.getType().getMaintenanceProfile().getPriceA());
        }
        if(a.getbCheck() > prof.getHoursB()){
            a.getOperator().pay(prof.getPriceB());
        }
        if(a.getcCheck() > prof.getHoursC()){
            a.getOperator().pay(prof.getPriceC());
        }
    }

    /**
     *
     * @param a the airline to sell the aircraft of
     */
    static void sellPlanes(Airline a) {

        a.getFleet().getFleet().forEach(new Consumer<Subfleet>() {
            @Override
            public void accept(Subfleet t) {
                if(t.getAircraft().values().size() < 10
                        && getPercentOfFleet(t, a) < .05
                        && t.getOrders().size() != 0){

                    t.getAircraft().values().forEach(new AirplaneConsumer());
                }
            }
        });

        a.getFleet().getFleet().forEach(sf -> sf.getAircraft().values().forEach(new Consumer<Airplane>() {
            @Override
            public void accept(Airplane t) {
                //TODO
            }
        }));

    }

    private static double getPercentOfFleet(Subfleet sf, Airline a){
        double total = 0;

        for(Subfleet s : a.getFleet().getFleet()){
            total += s.getAircraft().values().size();
        }

        int fleet = 0;

        fleet += sf.getAircraft().values().size();

        return fleet / total;
    }

    /**
     * @throws UnsupportedOperationException
     */
    static void acquirePlanes(Airline a) {

    }

    /**
     * @throws UnsupportedOperationException
     */
    public static void invest(Airline a) {

    }

    /**
     * @throws UnsupportedOperationException
     */
    static void evaluateOldRoutes(Airline a) {

    }

    /**
     * @throws UnsupportedOperationException
     */
    static void createNewRoutes(Airline a) {

    }

    private static class AirplaneConsumer implements Consumer<Airplane> {
        @Override
        public void accept(Airplane t) {
            t.setForSale(true);
            UsedAircraftMarket.putOnSale(t);
        }
    }
}
