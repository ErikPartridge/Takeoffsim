/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.economics;

import com.takeoffsim.models.world.Time;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Erik in 11, 2014.
 */
class Bills implements Serializable {

    public static final BlockingQueue<Bill> bills = new PriorityBlockingQueue<>();

    public static void execute(){
        while(bills.peek().getTime().compareTo(Time.currentTime) <= 0){
            bills.poll().execute();
        }
    }

    public static void add(Bill bill){
        bills.add(bill);
    }
}
