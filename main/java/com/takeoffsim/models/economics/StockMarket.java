/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.economics;

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

    private StockMarket() {
    }


    public static void move() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods,
        // choose Tools | Templates.
    }

    /*
    public void trade( Company buyer,  Company bought, long shares) {
        Money purchasePrice = bought.getCorporateStock().getCurrentPrice().multipliedBy(shares);
        if (buyer.getFunds()> 0) {
            buyer.pay(purchasePrice);
            bought.setSharesOfCorporate(-shares);
            Stock s = new Stock();
            s.setHolder(buyer);
            s.setCompany(bought);
            s.setShares(shares);
            s.setCurrentPrice(purchasePrice);
            buyer.addToHoldings(s);
        }
    }
    */
}
