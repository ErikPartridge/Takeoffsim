/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.manufacturers;

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
