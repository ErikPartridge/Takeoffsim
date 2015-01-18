/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/** (c) Erik Malmstrom-Partridge 2014
 This code is provided as-is without any warranty
 This source code or its compiled program may not be redistributed in any way
 **/
package com.takeoffsim.airport;

import com.takeoffsim.models.economics.Entity;

import java.io.Serializable;


@Deprecated
class Slot implements Serializable{

    private final int hour;

    private final int week;

    private final int year;

    private Entity owner;

    public Slot(int hour, int week, int year, Entity owner) {
        this.hour = hour;
        this.week = week;
        this.owner = owner;
        this.year = year;
    }


    /**
     * @return the hour
     */
    public int getHour() {
        return hour;
    }
    
    /**
     * @return the week
     */
    public int getWeek() {
        return week;
    }

    /**
     * @return the owner
     */
    public Entity getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Entity owner) {
        this.owner = owner;
    }

}
