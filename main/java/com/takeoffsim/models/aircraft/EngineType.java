/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.aircraft;

import com.takeoffsim.models.manufacturers.EngineManufacturer;
import lombok.extern.apachecommons.CommonsLog;

import java.io.Serializable;

/**
 * Created by Erik in 11, 2014.
 */
@CommonsLog
public class EngineType implements Serializable {

    static final long serialVersionUID = 518310003913L;

    private double sfc;

    private int lbf;

    private String name;

    private EngineManufacturer manufacturer;

    private double rangeFactor;

    private EngineType(double sfc, EngineManufacturer manufacturer, int lbf, String name, double rangeFactor) {
        this.setSfc(sfc);
        this.setManufacturer(manufacturer);
        this.setLbf(lbf);
        this.setName(name);
        this.setRangeFactor(rangeFactor);
    }


    /**
     *Engine has properties. Doesn't do much but needs to be there.
     *Fuel burn per lbf
     */
    public double getSfc() {
        return sfc;
    }

    void setSfc(double sfc) {
        this.sfc = sfc;
    }

    /**
     * Max lbf
     */
    public int getLbf() {
        return lbf;
    }

    void setLbf(int lbf) {
        this.lbf = lbf;
    }

    /**
     * Name of engine- example RR- Trent 700
     */
    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    /**
     *     Who made it. It will actually be an engine manufacturer
     */
    public EngineManufacturer getManufacturer() {
        return manufacturer;
    }

    void setManufacturer(EngineManufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getRangeFactor() {
        return rangeFactor;
    }

    void setRangeFactor(double rangeFactor) {
        this.rangeFactor = rangeFactor;
    }

    @Override
    public String toString() {
        return "EngineType{" +
                "sfc=" + sfc +
                ", lbf=" + lbf +
                ", name='" + name + '\'' +
                ", manufacturer=" + manufacturer +
                ", rangeFactor=" + rangeFactor +
                '}';
    }
}
