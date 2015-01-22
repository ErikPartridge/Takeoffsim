/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-Partridge 2014
 */

package com.takeoffsim.models.aircraft;


import com.takeoffsim.models.manufacturers.AircraftManufacturer;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;
import org.joda.money.Money;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

@CommonsLog @Data
public class AircraftType implements Serializable, Comparable {

    static final long serialVersionUID = -4014513L;

    private final String icao;

    private final int numberOfEngines;

    private final String name;

    private final int cruiseSpeed;

    private final int departureRunway;

    private final int arrivalRunway;

    private final int range;

    @NotNull
    private Money price;

    @NotNull
    private final LocalDate entry;

    private LocalDate stop;

    @NotNull
    private final List<EngineType> engineOptions = new ArrayList<>();

    private int maxEconomySeats;

    private int productionRate;

    private AircraftManufacturer manufacturer;

    private final AircraftTypeMaintenance maintenanceProfile;

    private final int fuelBurn;

    private final int fuelCapacity;

    private final int mtow;

    private final int mlw;

    private final int oew;

    private final int mzfw;

    private final int turntime;



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
                ", entry=" + entry +
                ", stop=" + stop +
                ", engineOptions=" + engineOptions +
                ", maxEconomySeats=" + maxEconomySeats +
                ", productionRate=" + productionRate +
                ", manufacturer=" + manufacturer +
                ", maintenanceProfile=" + maintenanceProfile +
                ", fuelBurn=" + fuelBurn +
                ", fuelCapacity=" + fuelCapacity +
                ", mtow=" + mtow +
                ", mlw=" + mlw +
                ", oew=" + oew +
                ", mzfw=" + mzfw +
                ", turntime=" + turntime +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if(! (o instanceof AircraftType))
            return -1;
        AircraftType type = (AircraftType) o;
        return ((AircraftType) o).toString().compareTo(this.toString());
    }
}
