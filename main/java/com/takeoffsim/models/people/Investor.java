/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.people;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.io.Serializable;

/**
 * Created by erik on 1/12/15.
 */
@Data
@CommonsLog
public class Investor implements Serializable{

    private final int generousity;

    private final int tolerance;

    private final String name;

    private Money investment = Money.zero(CurrencyUnit.USD);

    public Investor(String name){
        this.name = name;
        MersenneTwisterRNG rand = new MersenneTwisterRNG();
        generousity = rand.nextInt(8);
        tolerance = rand.nextInt(8);
    }


    public Money invest(double median){
        double calibrated = median * ((generousity + 8) / 12);
        MersenneTwisterRNG rand = new MersenneTwisterRNG();
        calibrated = calibrated * rand.nextGaussian();
        investment = Money.of(CurrencyUnit.USD, calibrated);
        return investment;
    }

}
