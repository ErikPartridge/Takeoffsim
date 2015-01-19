/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airport;

import com.takeoffsim.models.economics.Company;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.Serializable;


/**
 * @author Erik
 */
public class Gate implements Serializable {

    static final long serialVersionUID = -9999999999L;


    @Nullable
    private Company owner;

    private int number;

    private Company user;

    private Airport airport;


    public Gate(@Nullable Company owner, int number, Airport airport) {
        this.owner = owner;
        this.number = number;
        this.airport = airport;
        this.user = owner;
    }

    public Gate(@Nullable Company owner, int number, Airport airport, Company user){
        this.owner = owner;
        this.number = number;
        this.airport = airport;
        this.user = user;
    }


    public Gate() {
        this.owner = null;
    }


    /**
     * @return the owner
     */


    @Nullable
    public Company getOwner() {
        return owner;
    }


    /**
     * @param owner the owner to set
     */
    public void setOwner(@Nullable Company owner) {
        this.owner = owner;
    }


    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }


    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }


    /**
     * @return the airport
     */
    public Airport getAirport() {
        return airport;
    }


    /**
     * @param airport the airport to set
     */
    public void setAirport(@NonNull Airport airport) {
        this.airport = airport;
    }

    public Money value(){
        double price = airport.getAllocatedDemand() * 5.0 / airport.getGates().size();
        return Money.of(CurrencyUnit.USD, price);
    }

    @NotNull
    @Override
    public String toString() {
        return "Gate{" +
                "owner=" + owner +
                ", number=" + number +
                ", airport=" + airport +
                '}';
    }
}
