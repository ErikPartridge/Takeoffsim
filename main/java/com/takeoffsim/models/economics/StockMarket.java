/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.models.economics;

import java.util.ArrayList;

/*

 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014
 *
/*
package economics;



import java.util.ArrayList;


/**
 * @author Erik
 */
public class StockMarket {
    
    private static ArrayList<Stock> stocks = new ArrayList<>();


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
