/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.economics;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.Serializable;
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
public final class StockMarket implements Serializable {
    
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
    
    public static Index getDow(){return dow;}
}

