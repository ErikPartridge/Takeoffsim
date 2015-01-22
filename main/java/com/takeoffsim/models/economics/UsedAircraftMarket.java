/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.economics;

import com.takeoffsim.models.aircraft.Airplane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Erik in 11, 2014.
 */
public final class UsedAircraftMarket {

    private static final List<Airplane> forSale = Collections.synchronizedList(new ArrayList<>());

    private UsedAircraftMarket() {
    }

    private static List<Airplane> sortedOptions(){
        forSale.sort(new Comparator<Airplane>() {
            @Override
            public int compare(Airplane o1, Airplane o2) {
                if(!o1.getType().equals(o2.getType()))
                    return o1.getType().compareTo(o2.getType());
                else
                    return o1.getCycles() - o2.getCycles();
            }
        });
        return forSale;
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
