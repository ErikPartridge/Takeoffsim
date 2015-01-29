/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airport;


import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Erik
 */
class GateBuilder implements Serializable {

    @NonNull
    private Airport airport;

    private int gateNumber = 1;


    GateBuilder(Airport apt) {
        this.airport = apt;
    }

    public GateBuilder(){
        airport = null;
    }
    /**
     *
     * @param number how many gates to make
     * @return a list of created gates
     */
    @NotNull
    public Iterable<Gate> makeGates(int number) {
        int gatesToMake = number;
        Collection<Gate> gates = new ArrayList<>();
        while (gatesToMake > 0) {
            Gate gate = new Gate(null, gateNumber, airport);
            gates.add(gate);
            gateNumber++;
            gatesToMake--;
        }
        return gates;
    }


    /**
     *
     * @return the string representation of the gate builder
     */
    @NotNull
    @Override
    public String toString() {
        return "GateBuilder{" +
                "airport=" + airport +
                ", gateNumber=" + gateNumber +
                '}';
    }
}
