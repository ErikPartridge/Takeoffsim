/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.aircraft;

import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.economics.Company;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
public final class UsedAircraftMarket {
    
    private final static Map<AircraftType, List<Airplane>> market = new ConcurrentHashMap<>();
    
    public static List<Airplane> planesByType(String type){
        List<Airplane> planes = market.get(AircraftTypes.getAcfType(type));
        Collections.sort(planes, new Comparator<Airplane>() {
            @Override
            public int compare(Airplane o1, Airplane o2) {
                return o1.value().compareTo(o2.value());
            }
        });
        return planes;
    }
    
    public synchronized static void submit(Airplane airplane){
        market.get(airplane.getType()).add(airplane);
    }
    
    public synchronized static void removeFromMarket(Airplane airplane){
        market.get(airplane.getType()).remove(airplane);
    }
    
    public synchronized static void purchase(Airplane airplane, Company buyer){
        Company original = airplane.getOwner();
        airplane.setOwner(buyer);
        if(buyer instanceof Airline) {
            Airline a = (Airline) buyer;
            airplane.setOperator(a);
            a.getFleet().getSubFleet(airplane.getType().getIcao()).getAircraft().put(airplane.getRegistration(), airplane);
        }else{
            airplane.setOperator(null);
        }
        original.receive(airplane.value());
        buyer.pay(airplane.value());
    } 
}
