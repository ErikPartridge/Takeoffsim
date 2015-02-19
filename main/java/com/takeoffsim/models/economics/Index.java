/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.economics;

import com.takeoffsim.services.history.History;
import org.apache.commons.math3.random.MersenneTwister;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
public final class Index implements Serializable {

    private transient final MersenneTwister random = new MersenneTwister();

    private double value;

    private transient static ArrayList<History<Index>> histories = new ArrayList<>();

    public Index(double val){
        this.value = val;
    }

    /**
     * Should be called once a minute
     */
    public void tick(){
        double factor  = random.nextDouble() / 1200 - (0.00041666666);
        value *= 1 + factor;
        histories.add(new History<>(this));
    }

    public double getIndex(){
        return value;
    }

    public String toString(){
        return "{\"values\":" + historiesToJson() + "," + "key: Dow Jones Industrial Average," + "color: #00F}";
    }

    private String historiesToJson(){
        String json = "[";
        for(History<Index> h : histories){
            LocalDateTime time = h.getTimeStamp();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy,MM,dd,hh,mm");
            json += "{x:new Date(" + time.format(format) + "), y:" + h.getObject().getIndex() + "},";
        }
        json = json.substring(0, json.length() - 1);
        json += "]";
        return json;
    }

}
