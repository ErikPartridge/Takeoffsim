/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.models.aircraft;

import com.takeoffsim.manufacturers.EngineManufacturer;
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

    public EngineType(double sfc, EngineManufacturer manufacturer, int lbf, String name, double rangeFactor) {
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

    public void setSfc(double sfc) {
        this.sfc = sfc;
    }

    /**
     * Max lbf
     */
    public int getLbf() {
        return lbf;
    }

    public void setLbf(int lbf) {
        this.lbf = lbf;
    }

    /**
     * Name of engine- example RR- Trent 700
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *     Who made it. It will actually be an engine manufacturer
     */
    public EngineManufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(EngineManufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getRangeFactor() {
        return rangeFactor;
    }

    public void setRangeFactor(double rangeFactor) {
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
