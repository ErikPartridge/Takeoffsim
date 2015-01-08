/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
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
    public ArrayList<Gate> makeGates(int number) {
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
