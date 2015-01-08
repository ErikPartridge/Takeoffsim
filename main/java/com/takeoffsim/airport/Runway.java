/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.airport;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Created by Erik on 9/11/14.
 */
public class Runway implements Serializable{

    private final double METERS_TO_FEET = 3.28084;

    private double length;

    private Airport airport;

    private String title;

    private String surface;

    private int heading;

    private boolean helipad;

    private boolean available;

    /**
     *
     * @param title the name of the runway, eg "13"
     * @param meters the length of the runway
     * @param surface the surface of the runway, eg. Asphalt
     * @param airport the airport that owns this runway
     */
    public Runway(@NotNull String title, int meters, String surface, Airport airport) {
        this.length = meters * METERS_TO_FEET;
        this.airport = airport;
        this.title = title;
        this.surface = surface;
        this.heading = Integer.parseInt(title.split("/")[0].replaceAll("[^\\d.]", "")) * 10;
        this.helipad = false;
        this.available = true;
    }

    /**
     *
     * @param title the name of the runway, eg "13"
     * @param meters the length of the runway
     * @param surface the surface of the runway, eg. Asphalt
     * @param airport the airport that owns this runway
     * @param helipad is this a helipad
     */
    public Runway(String title, int meters, String surface, Airport airport, boolean helipad) {
        this.length = meters * METERS_TO_FEET;
        this.airport = airport;
        this.title = title;
        this.surface = surface;
        this.heading = 0;
        this.helipad = helipad;
        this.available = true;
    }

    public void tick(){
        this.available = true;
    }

    public void reserve(){
        this.available = false;
    }

    public boolean isAvailable(){
        return this.available;
    }

    public double length(){
        return this.length;
    }
}
