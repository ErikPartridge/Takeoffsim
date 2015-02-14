/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.economics;

import com.takeoffsim.services.history.History;
import org.apache.commons.math3.random.MersenneTwister;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.ArrayList;
import java.util.HashMap;

/*

 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014
 *
/*
package economics;



import java.util.ArrayList;


/**
 * @author Erik
 */
@Deprecated
final class StockMarket {
    
    private static final HashMap<String, Stock> stocks = new HashMap<>();

    private static final Index dow = new Index(18019.35);
    
    private static final Index oil = new Index(4.71);
    
    private StockMarket() {
    }


    public static void move() {
        oil.tick();dow.tick();
    }
    
    public static Money getOilPrice(){
        return Money.of(CurrencyUnit.USD, oil.getIndex());
    }
    
}

final class Index{
    
    private final MersenneTwister random = new MersenneTwister();
    
    private double value;
    
    private transient static ArrayList<History<Index>> histories = new ArrayList<>();
    
    public Index(double val){
        this.value = val;
    }

    /**
     * Should be called once a minute 
     */
    public void tick(){
        double factor  = random.nextDouble() / 1200 - (0.00041666666);
        value *= 1 + factor;
        histories.add(new History<>(this));
    }
    
    public double getIndex(){
        return value;
    }
    
}
