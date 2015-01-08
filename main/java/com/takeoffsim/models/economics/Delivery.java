/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.economics;

import com.takeoffsim.models.aircraft.AircraftType;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;


public class Delivery implements Serializable {
    static final long serialVersionUID = -200023213499L;

    @NonNull
    private AircraftType type;
    private Company airline;


    public Delivery(Company air, AircraftType at) {
        this.type = at;
        this.airline = air;
    }

    /**
     * @return the type
     */
    public AircraftType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(AircraftType type) {
        this.type = type;
    }

    /**
     * @return the airline
     */
    public Company getAirline() {
        return airline;
    }

    /**
     * @param airline the airline to set
     */
    public void setAirline(Company airline) {
        this.airline = airline;
    }

    public void deliver() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public String toString() {
        return "Delivery{" +
                "type=" + type +
                ", airline=" + airline +
                '}';
    }
}
