/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.aircraft;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.joda.money.Money;

import java.io.Serializable;

/**
 * Created by Erik in 11, 2014.
 */
@CommonsLog
@Data
public class EngineType implements Serializable {

    static final long serialVersionUID = 518310003913L;

    private final String name;
    
    private final double fuelBurnFactor;
    
    private final double speedFactor;
    
    private final double runwayFactor;
    
    private final double rangeFactor;
    
    private final Money price;
    
}
