/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.models.economics;

import com.takeoffsim.models.aircraft.Airplane;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Created by Erik in 11, 2014.
 */
public class UsedAircraftMarket {

    private static ArrayList<Airplane> forSale = new ArrayList<>();

    public static void putOnSale(Airplane a){
        forSale.add(a);
    }

    public static void buy(Airplane a){
        throw new UnsupportedOperationException();
    }

    public static void buy(String s){
        Airplane airplane = forSale.stream().filter(new Predicate<Airplane>() {
            @Override
            public boolean test(Airplane airplane) {
                if(airplane.getRegistration().equals(s)){
                    return true;
                }else {
                    return false;
                }
            }
        }).findAny().get();
        buy(airplane);
    }
}
