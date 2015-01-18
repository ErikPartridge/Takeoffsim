/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.economics;

import com.takeoffsim.models.aircraft.Airplane;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Erik in 11, 2014.
 */
public final class UsedAircraftMarket {

    private static final Collection<Airplane> forSale = new ArrayList<>();

    private UsedAircraftMarket() {
    }

    public static void putOnSale(Airplane a){
        forSale.add(a);
    }

    private static void buy(Airplane a){
        throw new UnsupportedOperationException("ha no");
    }

    public static void buy(String s){
        Airplane airplane = forSale.stream().filter(t-> t.getRegistration().equals(s)).findAny().get();
        buy(airplane);
    }
}
