/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/** (c) Erik Malmstrom-Partridge 2014
 This code is provided as-is without any warranty
 This source code or its compiled program may not be redistributed in any way
 **/
package com.takeoffsim.airport;

import com.takeoffsim.models.economics.Entity;

import java.io.Serializable;


public class Slot implements Serializable{

    private int hour;

    private int week;

    private int year;

    private Entity owner;

    public Slot(int hour, int week, int year, Entity owner) {
        this.hour = hour;
        this.week = week;
        this.owner = owner;
    }


    /**
     * @return the hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * @param hour the hour to set
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * @return the week
     */
    public int getWeek() {
        return week;
    }

    /**
     * @param week the week to set
     */
    public void setWeek(int week) {
        this.week = week;
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
