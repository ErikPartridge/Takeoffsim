/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.manufacturers;

import com.takeoffsim.models.economics.Company;
import com.takeoffsim.models.economics.Delivery;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.concurrent.LinkedTransferQueue;

/**
 *
 * @author Erik
 */

/**
 * This is your Airbus, your Boeing, your Bombardier, Cessna, Embraer, Beechcraft
 * <p>
 * This class holds the properties, but in particular holds critical methods.
 * <p>
 * These methods are used in AircraftManufacturers.java to self-analyze
 */
@SuppressWarnings("ALL")
public class AircraftManufacturer extends Company implements Serializable {

    static final long serialVersionUID = -551032041203413L;

    @NotNull
    public LinkedTransferQueue<Delivery> toDeliver = new LinkedTransferQueue<>();

    @NotNull
    @Override
    public String toString() {
        return "AircraftManufacturer{" +
                "toDeliver=" + toDeliver +
                '}';
    }
}
