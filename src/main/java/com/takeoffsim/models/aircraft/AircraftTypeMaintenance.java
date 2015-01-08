/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-Partridge 2014
 */
package com.takeoffsim.models.aircraft;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;

import java.io.Serializable;

/**
 * @author Erik
*/
@CommonsLog
@Data
public class AircraftTypeMaintenance implements Serializable {

    static final long serialVersionUID = 50223114L;


    private double priceA;

    private double priceB;

    private double priceC;

    private int hoursA;

    private int hoursB;

    private int hoursC;

    private int betweenA;

    private int betweenB;

    private int betweenC;

    /**
     *
     * @param priceA the price of an 'A' Check
     * @param priceB the price of an 'B' Check
     * @param priceC the price of an 'C' Check
     * @param hoursA how long an 'A' Check takes
     * @param hoursB how long a 'B' Check takes
     * @param hoursC how long a 'C' Check takes
     */
    public AircraftTypeMaintenance(double priceA, double priceB, double priceC, int hoursA, int hoursB, int hoursC, int betweenA, int betweenB, int betweenC) {
        this.priceA = priceA;
        this.priceB = priceB;
        this.priceC = priceC;
        this.hoursA = hoursA;
        this.hoursB = hoursB;
        this.hoursC = hoursC;
        this.betweenA = betweenA;
        this.betweenB = betweenB;
        this.betweenC = betweenC;
        log.trace("created aircraft type maintenance");
    }

    public AircraftTypeMaintenance() {
    }
}
