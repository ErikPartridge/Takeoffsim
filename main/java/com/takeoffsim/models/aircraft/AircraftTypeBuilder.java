/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.aircraft;

import java.time.LocalDate;

public class AircraftTypeBuilder {
    private String icao;
    private int numberOfEngines;
    private String name;
    private int cruiseSpeed;
    private int departureRunway;
    private int arrivalRunway;
    private int range;
    private LocalDate entry;
    private AircraftTypeMaintenance maintenanceProfile;
    private int fuelBurn;
    private int mtow;
    private int mlw;
    private int oew;
    private int mzfw;
    private int turntime;
    private int cargoCapacity;

    public AircraftTypeBuilder setIcao(String icao) {
        this.icao = icao;
        return this;
    }

    public AircraftTypeBuilder setNumberOfEngines(int numberOfEngines) {
        this.numberOfEngines = numberOfEngines;
        return this;
    }

    public AircraftTypeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public AircraftTypeBuilder setCruiseSpeed(int cruiseSpeed) {
        this.cruiseSpeed = cruiseSpeed;
        return this;
    }

    public AircraftTypeBuilder setDepartureRunway(int departureRunway) {
        this.departureRunway = departureRunway;
        return this;
    }

    public AircraftTypeBuilder setArrivalRunway(int arrivalRunway) {
        this.arrivalRunway = arrivalRunway;
        return this;
    }

    public AircraftTypeBuilder setRange(int range) {
        this.range = range;
        return this;
    }

    public AircraftTypeBuilder setEntry(LocalDate entry) {
        this.entry = entry;
        return this;
    }

    public AircraftTypeBuilder setMaintenanceProfile(AircraftTypeMaintenance maintenanceProfile) {
        this.maintenanceProfile = maintenanceProfile;
        return this;
    }

    public AircraftTypeBuilder setFuelBurn(int fuelBurn) {
        this.fuelBurn = fuelBurn;
        return this;
    }

    public AircraftTypeBuilder setMtow(int mtow) {
        this.mtow = mtow;
        return this;
    }

    public AircraftTypeBuilder setMlw(int mlw) {
        this.mlw = mlw;
        return this;
    }

    public AircraftTypeBuilder setOew(int oew) {
        this.oew = oew;
        return this;
    }

    public AircraftTypeBuilder setMzfw(int mzfw) {
        this.mzfw = mzfw;
        return this;
    }

    public AircraftTypeBuilder setTurntime(int turntime) {
        this.turntime = turntime;
        return this;
    }

    public AircraftTypeBuilder setCargoCapacity(int cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
        return this;
    }

    public AircraftType createAircraftType() {
        return new AircraftType(icao, numberOfEngines, name, cruiseSpeed, departureRunway, arrivalRunway, range, entry, maintenanceProfile, fuelBurn, mtow, mlw, oew, mzfw, turntime, cargoCapacity);
    }
}