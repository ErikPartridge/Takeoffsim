/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.economics;

import com.takeoffsim.models.aircraft.AircraftType;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.world.Time;
import org.jetbrains.annotations.NotNull;
import org.joda.money.Money;

import java.math.RoundingMode;

/**
 * @author Erik
 */
public class Order extends Contract{

    static final long serialVersionUID = -212012033L;
    /**
     * @param a the airline placing the order
     * @param acf the aircraft type
     * @param number the number of aircraft
     */
    public Order(@NotNull Airline a, @NotNull AircraftType acf, int number, boolean payUpFront) {
        for (int i = 0; i < number; i++) {
            if(payUpFront){
                Money cost = null;
                if(number > 40){
                    cost = acf.getPrice().dividedBy(1.40d, RoundingMode.CEILING);
                }else{
                    cost = acf.getPrice().dividedBy(1 + number / 100.0d, RoundingMode.CEILING);
                }
                Bills.bills.add(new Bill(a, acf.getManufacturer(), acf.getPrice(), Time.getDateTimeInstance()));
            }else{

            }
            Delivery d = new Delivery(a, acf);
            acf.getManufacturer().toDeliver.add(d);
            a.getFleet().getSubFleet(acf.getIcao()).getOrders().add(d);
        }
    }
}
