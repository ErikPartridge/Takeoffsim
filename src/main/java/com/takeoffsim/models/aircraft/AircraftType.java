/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-Partridge 2014
 */

package com.takeoffsim.models.aircraft;


import com.takeoffsim.airport.Airport;
import com.takeoffsim.manufacturers.AircraftManufacturer;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;
import org.joda.money.Money;

import java.io.Serializable;
import java.util.ArrayList;

/*
*This class is here to provide a framework for each aircraft
*Each aircrafttype has default parts, that are represented with
*this class here.
*
*/

/**
 * @author Erik
 * @since 0.3 alpha
 */

@CommonsLog
public class AircraftType implements Serializable {

    static final long serialVersionUID = -4014513L;
    private final String icao;
    private final int numberOfEngines;
    private String name;
    private int cruiseSpeed;
    private int departureRunway;
    private int arrivalRunway;
    private int range;
    private Money price;

    @NotNull
    private ArrayList<EngineType> engineOptions = new ArrayList<>();

    private int maxEconomySeats;

    private int productionRate;

    private AircraftManufacturer manufacturer;

    private AircraftTypeMaintenance maintenanceProfile;

    private double sfc;

    private int mtow;

    private int mlw;

    private int oew;

    private int mzfw;

    private Airport buildLocation;

    private int turntime;

    /**
     * @param name               The name of this aircraft type- eg. McDonnell-Douglas MD-90
     * @param cruiseSpeed        The cruise speed of this aircraft
     * @param icao               the icao code / lookup code of the aircraft eg. MD90
     * @param range              maximum range of this aircraft
     * @param price              the list price of the aircraft type
     * @param numberOfEngines    number of engines, 2 for A320, 4 for 747
     * @param maxEconomySeats    the maximum number of seats this can fit
     * @param productionRate     how many planes made per month
     * @param manufacturer       the manufacturer of this aircraft
     * @param maintenanceProfile the maintenance
     * @param sfc                the specific fuel consumption
     * @param mtow               the max takeoff weight
     * @param mlw                the max landing weight
     * @param oew                the operating empty weight
     * @param mzfw               the max zero fuel weight
     */
    public AircraftType(String name, int cruiseSpeed, int departureRunway, int arrivalRunway, String icao, int range, double price, byte numberOfEngines, int maxEconomySeats, int productionRate, AircraftManufacturer manufacturer, AircraftTypeMaintenance maintenanceProfile, double sfc, int mtow, int mlw, int oew, int mzfw) {
        this.icao = icao;
        this.setName(name);
        this.setCruiseSpeed(cruiseSpeed);
        this.setDepartureRunway(departureRunway);
        this.setArrivalRunway(arrivalRunway);
        this.setRange(range);
        this.setPrice(Money.parse(price + "USD"));
        this.numberOfEngines = numberOfEngines;
        this.setMaxEconomySeats(maxEconomySeats);
        this.setProductionRate(productionRate);
        this.setManufacturer(manufacturer);
        this.setMaintenanceProfile(maintenanceProfile);
        this.setSfc(sfc);
        this.setMtow(mtow);
        this.setMlw(mlw);
        this.setOew(oew);
        this.setMzfw(mzfw);
        log.info("Created aircraft type, with name: " + name);
    }

    public AircraftType(String icao, int engines){
        this.icao = icao;
        this.numberOfEngines = engines;
    }

    public String getIcao() {
        return icao;
    }

    public int getNumberOfEngines() {
        return numberOfEngines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCruiseSpeed() {
        return cruiseSpeed;
    }

    public void setCruiseSpeed(int cruiseSpeed) {
        this.cruiseSpeed = cruiseSpeed;
    }



    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    /**
    *All of the Engine options currently available for this aircraft type--
    * The first entry will be given to all aircraft without a defined
    * engine in their xml files
    */
    @NotNull
    public ArrayList<EngineType> getEngineOptions() {
        return engineOptions;
    }

    public void setEngineOptions(@NotNull ArrayList<EngineType> engineOptions) {
        this.engineOptions = engineOptions;
    }

    /**
     *Maximum values for the configuration, a default, and a list.
    */
    public int getMaxEconomySeats() {
        return maxEconomySeats;
    }

    public void setMaxEconomySeats(int maxEconomySeats) {
        this.maxEconomySeats = maxEconomySeats;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public void setProductionRate(int productionRate) {
        this.productionRate = productionRate;
    }

    public AircraftManufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(AircraftManufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public AircraftTypeMaintenance getMaintenanceProfile() {
        return maintenanceProfile;
    }

    public void setMaintenanceProfile(AircraftTypeMaintenance maintenanceProfile) {
        this.maintenanceProfile = maintenanceProfile;
    }

    public double getSfc() {
        return sfc;
    }

    public void setSfc(double sfc) {
        this.sfc = sfc;
    }

    public int getMtow() {
        return mtow;
    }

    public void setMtow(int mtow) {
        this.mtow = mtow;
    }

    public int getMlw() {
        return mlw;
    }

    public void setMlw(int mlw) {
        this.mlw = mlw;
    }

    public int getOew() {
        return oew;
    }

    public void setOew(int oew) {
        this.oew = oew;
    }

    public int getMzfw() {
        return mzfw;
    }

    public void setMzfw(int mzfw) {
        this.mzfw = mzfw;
    }

    public Airport getBuildLocation() {
        return buildLocation;
    }

    public void setBuildLocation(Airport buildLocation) {
        this.buildLocation = buildLocation;
    }

    public int getTurntime() {
        return turntime;
    }

    public void setTurntime(int turntime) {
        this.turntime = turntime;
    }

    public int getDepartureRunway() {
        return departureRunway;
    }

    public void setDepartureRunway(int departureRunway) {
        this.departureRunway = departureRunway;
    }

    public int getArrivalRunway() {
        return arrivalRunway;
    }

    public void setArrivalRunway(int arrivalRunway) {
        this.arrivalRunway = arrivalRunway;
    }

    @Override
    public String toString() {
        return "AircraftType{" +
                "icao='" + icao + '\'' +
                ", numberOfEngines=" + numberOfEngines +
                ", name='" + name + '\'' +
                ", cruiseSpeed=" + cruiseSpeed +
                ", departureRunway=" + departureRunway +
                ", arrivalRunway=" + arrivalRunway +
                ", range=" + range +
                ", price=" + price +
                ", engineOptions=" + engineOptions +
                ", maxEconomySeats=" + maxEconomySeats +
                ", productionRate=" + productionRate +
                ", manufacturer=" + manufacturer +
                ", maintenanceProfile=" + maintenanceProfile +
                ", sfc=" + sfc +
                ", mtow=" + mtow +
                ", mlw=" + mlw +
                ", oew=" + oew +
                ", mzfw=" + mzfw +
                ", buildLocation=" + buildLocation +
                ", turntime=" + turntime +
                '}';
    }
}
