/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-Partridge 2014
 */
package com.takeoffsim.models.aircraft;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.Serializable;

/**
 * @author Erik
*/
@CommonsLog
@Data
public class AircraftTypeMaintenance implements Serializable {

    static final long serialVersionUID = 50223114L;


    private Money priceA = Money.of(CurrencyUnit.USD, 20000);

    private Money priceB = Money.of(CurrencyUnit.USD, 100000);

    private Money priceC = Money.of(CurrencyUnit.USD, 7500000);

    private int hoursA = 2;

    private int hoursB = 8;

    private int hoursC = 300;

    private int betweenA = 70;

    private int betweenB = 500;

    private int betweenC = 15000;

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
        this.priceA = Money.of(CurrencyUnit.USD, priceA);
        this.priceB = Money.of(CurrencyUnit.USD, priceB);
        this.priceC = Money.of(CurrencyUnit.USD, priceC);
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
