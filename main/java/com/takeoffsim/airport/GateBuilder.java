/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.airport;


import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Erik
 */
public class GateBuilder implements Serializable {

    @NonNull
    private final Airport airport;

    private int gateNumber = 1;


    public GateBuilder(Airport apt) {
        this.airport = apt;
    }


    /**
     *
     * @param number how many gates to make
     * @return a list of created gates
     */
    @NotNull
    public Iterable<Gate> makeGates(int number) {
        int gatesToMake = number;
        ArrayList<Gate> gates = new ArrayList<>();
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
