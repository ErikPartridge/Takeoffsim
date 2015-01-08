/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.models.economics;

import com.takeoffsim.models.world.Time;

import java.io.Serializable;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Erik in 11, 2014.
 */
public class Bills implements Serializable {

    public static PriorityBlockingQueue<Bill> bills = new PriorityBlockingQueue<>();

    public static void execute(){
        while(bills.peek().getTime().compareTo(Time.currentTime) <= 0){
            bills.poll().execute();
        }
    }

    public static void add(Bill b){
        bills.add(b);
    }
}
