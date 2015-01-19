/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.people;

import com.takeoffsim.models.economics.Stock;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.io.Serializable;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by erik on 1/12/15.
 */
@Data
@CommonsLog
public class Investor implements Serializable, Comparable{

    static final long serialVersionUID = -02032333L;

    private static ConcurrentSkipListSet<Investor> set = new ConcurrentSkipListSet<>();

    private final int generousity;

    private final int tolerance;

    private final String name;

    private final List<Stock> holdings = new ArrayList<>();

    private Money investment = Money.zero(CurrencyUnit.USD);

    public Investor(String name){
        this.name = name;
        MersenneTwisterRNG rand = new MersenneTwisterRNG();
        generousity = rand.nextInt(8);
        tolerance = rand.nextInt(8);
        set.add(this);
    }


    public Money invest(double median){
        double calibrated = median;
        investment = Money.of(CurrencyUnit.USD, calibrated, RoundingMode.CEILING);
        return investment;
    }

    @Override
    public int compareTo(Object o) {
        if(! (o instanceof Investor)){
            return -99;
        }
        return name.compareTo(((Investor) o).getName());
    }
}
