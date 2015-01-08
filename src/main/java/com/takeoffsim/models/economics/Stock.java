/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014
 */

package com.takeoffsim.models.economics;


import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.Serializable;
import java.math.RoundingMode;

public class Stock implements Serializable {
    //Instance variables. This is not a single stock, but rather a holding.

    static final long serialVersionUID = -1000831293132L;
    @NonNull
    private final Company company;
    private final Company holder;
    private long shares;
    private Money currentPrice;
    private final Money purchasePrice;

    private Money calculatePrice() {
        Money value = company.getValuation();
        value = value.dividedBy(company.getNumShares(), RoundingMode.DOWN);
        return value;
    }

    public Stock(Company of, Company owner, long share){
        this.company = of;
        this.holder = owner;
        this.shares = share;
        this.currentPrice = calculatePrice();
        this.purchasePrice = calculatePrice();
    }

    public Stock(Company of, long shares){
        this.company = of;
        this.holder = company;
        this.shares = shares;
        this.purchasePrice = calculatePrice();
    }


    /**
     * @return the company
     */
    public Company getCompany() {
        return company;
    }



    /**
     * @return the holder
     */
    public Company getHolder() {
        return holder;
    }



    /**
     * @return the shares
     */
    public long getShares() {
        return shares;
    }


    /**
     * @param shares the shares to set
     */
    public void setShares(long shares) {
        this.shares = shares;
    }


    /**
     * @return the currentPrice
     */
    public Money getCurrentPrice() {
        return currentPrice;
    }


    /**
     * @param currentPrice the currentPrice to set
     */
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = Money.of(CurrencyUnit.USD, currentPrice);
    }


    @NotNull
    @Override
    public String toString() {
        return "Stock{" +
                "company=" + company +
                ", holder=" + holder +
                ", shares=" + shares +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
