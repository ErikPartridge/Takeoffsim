/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-Partridge 2013,2014
 */

package com.takeoffsim.models.aircraft;

import akka.actor.UntypedActorWithStash;
import akka.pattern.Patterns;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Flight;
import com.takeoffsim.airport.Airport;
import com.takeoffsim.models.economics.Company;
import com.takeoffsim.models.messages.*;
import com.takeoffsim.models.world.Time;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;
import org.joda.money.Money;
import scala.concurrent.Await;
import scala.concurrent.duration.FiniteDuration;

import java.io.Serializable;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Erik
 * @since 0.3 alpha
 */
@CommonsLog
@Data
public class Airplane extends UntypedActorWithStash implements Serializable, Comparable {

    static final long serialVersionUID = 314214341233L;
    private final int msn;
    //Default properties of an airplane, not an airplane type.
    private String registration;
    private AircraftType type;
    private Engine engine;
    private int fin;
    private double age;
    private boolean leased;
    private double aCheck;
    private double bCheck;
    private double cCheck;
    @NotNull
    private ArrayList<Flight> flights = new ArrayList<>();
    private Airport location;
    private int cycles;
    private double hours;
    private Company owner;
    private Airline operator;
    private LocalDateTime until;
    private boolean forSale;
    private Flight activeFlight;



    /**
     * @param registration The registration is auto-generated as Country ID + Number + First two letters of Airline nam
     * @param type         has an aircraft type object
     * @param engine       the engine that it has
     * @param fin          fin number not changed after creation
     * @param age          in decimal format - 12yrs 6mths = 12.5
     * @param leased       if it is leased
     * @param aCheck       Amount of flying time since last A check.
     * @param bCheck       Amount of flying time since last B check.
     * @param cCheck       Amount of flying time since last C check.
     * @param location     Null means in flight, otherwise is where did the aircraft last land
     * @param cycles       Number of cycles on the plane
     * @param hours        Hours flown in its lifetime
     * @param owner        Who owns it/operates it
     */
    public Airplane(String registration, @NotNull AircraftType type, Engine engine, int fin, double age,
                    boolean leased, double aCheck, double bCheck, double cCheck,
                    Airport location, int cycles, double hours, @NotNull Company owner, int msn) {
        this.setRegistration(registration);
        this.setType(type);
        this.setEngine(engine);
        this.fin = fin;
        this.setAge(age);
        this.setLeased(leased);
        this.setaCheck(aCheck);
        this.setbCheck(bCheck);
        this.setcCheck(cCheck);
        this.setLocation(location);
        this.setCycles(cycles);
        this.setHours(hours);
        this.setOwner(owner);
        this.msn = msn;
        this.forSale = false;
        this.activeFlight = null;
        log.info("Created airplane with registration, owner, type:" + registration + type.getIcao() + owner.getName());
    }

    /**
     * @return the current value of this aircraft
     */
    public synchronized Money value(){
        Money money = this.getType().getPrice().multipliedBy(0.7d, RoundingMode.CEILING);
        if(this.getAge() < 1.0){
            money = money.multipliedBy(1 - (this.getAge() * 365 / 3050), RoundingMode.CEILING);
        }else{
            int h = (int) this.getHours();
            int a = (int) this.getAge() * 365;
            long penalty = a + this.getCycles() + h;
            money = money.multipliedBy(penalty);
            money = money.dividedBy(110000, RoundingMode.CEILING);
        }
        return money;
    }

    /**
     * @return if it is airworthy
     */
    public boolean checkAirworthyness() {
        return this.until.isBefore(Time.currentTime) || this.until.isEqual(Time.currentTime);
    }

    @NotNull
    @Override
    public String toString() {
        return "Airplane{" +
                "registration='" + getRegistration() + '\'' +
                ", type=" + getType() +
                ", engine=" + getEngine() +
                ", fin=" + getFin() +
                ", age=" + getAge() +
                ", leased=" + isLeased() +
                ", aCheck=" + getaCheck() +
                ", bCheck=" + getbCheck() +
                ", cCheck=" + getcCheck() +
                ", flights=" + getFlights() +
                ", location=" + getLocation() +
                ", cycles=" + getCycles() +
                ", hours=" + getHours() +
                ", owner=" + getOwner() +
                ", until=" + getUntil() +
                '}';
    }


    public double getaCheck() {
        return aCheck;
    }

    public void setaCheck(double aCheck) {
        this.aCheck = aCheck;
    }

    public double getbCheck() {
        return bCheck;
    }

    public void setbCheck(double bCheck) {
        this.bCheck = bCheck;
    }

    public double getcCheck() {
        return cCheck;
    }

    public void setcCheck(double cCheck) {
        this.cCheck = cCheck;
    }

    @Override
    public int compareTo(Object o) {
        Airplane a = (Airplane) o;
        int comp = 0;
        if (this.getType() != a.getType()) {
            comp = this.getType().getName().compareTo(a.getType().getName());
        } else {
            comp = (int) Math.round(a.getAge() - this.getAge() * 1000);
        }
        return comp;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof TakeoffMessage){
            TakeoffMessage tm = (TakeoffMessage) o;
            if(location == null){
                unhandled(tm);
            }else if(!location.equals(tm.getAirport())){
                unhandled(tm);
            }else{
                Object result = Await.result(Patterns.ask(location.getSelf(), new TryTakeoff(this, location), 1000), new FiniteDuration(1, TimeUnit.SECONDS));
                Boolean b = (Boolean) result;
                if(b){
                    tm.getFlight().depart(Time.getDateTimeInstance());
                    this.setActiveFlight(tm.getFlight());
                }else{
                    unhandled(tm);
                }
            }
        }else if(o instanceof MaintenanceMessage){
            MaintenanceMessage mm = (MaintenanceMessage) o;

            if(location == null){
                unhandled(mm);
            }else if(!this.operator.getHubs().contains(location)){
                unhandled(mm);
            }else{
                switch(mm.getType()){
                    case "A": executeACheck(); break;
                    case "B": executeBCheck(); break;
                    case "C": executeCCheck(); break;
                }
            }
        }else if(o instanceof LandingMessage){
            LandingMessage lm = (LandingMessage) o;
            if(location != null){
                unhandled(lm);
            }else if(!lm.getFlight().equals(activeFlight) || !lm.getArrival().equals(activeFlight.getRoute().getArrives())){
                unhandled(lm);
            }else{
                Object result = Await.result(Patterns.ask(lm.getArrival().getSelf(), new TryLanding(this, location), 1000), new FiniteDuration(1, TimeUnit.SECONDS));
                Boolean b = (Boolean) result;
                if(b){
                    lm.getFlight().arrive(Time.getDateTimeInstance());
                    this.setActiveFlight(null);
                }else{
                    unhandled(lm);
                }
            }
        }
    }

    public void executeACheck() {
        final AircraftTypeMaintenance mp = this.getType().getMaintenanceProfile();
        this.operator.pay(mp.getPriceA());
        until = Time.getDateTimeInstance().plusHours(mp.getHoursA());
        this.setaCheck(0.0d);
    }

    public void executeBCheck(){
        final AircraftTypeMaintenance mp = this.getType().getMaintenanceProfile();
        this.operator.pay(mp.getPriceB());
        until = Time.getDateTimeInstance().plusHours(mp.getHoursB());
        this.setbCheck(0.0d);
    }

    public void executeCCheck(){
        final AircraftTypeMaintenance mp = this.getType().getMaintenanceProfile();
        this.operator.pay(mp.getPriceC());
        until = Time.getDateTimeInstance().plusHours(mp.getHoursC());
        this.setcCheck(0.0d);
    }
}

