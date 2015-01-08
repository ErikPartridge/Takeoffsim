/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/** (c) Erik Malmstrom-Partridge 2014
 This code is provided as-is without any warranty
 This source code or its compiled program may not be redistributed in any way
 **/
package com.takeoffsim.models.people;

import com.takeoffsim.models.aircraft.AircraftType;
import com.takeoffsim.models.economics.Company;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author Erik
 */


public class Pilot extends Employee implements Serializable{


    private ArrayList<AircraftType> typeCertifications;

    public Pilot(int sal, String fName, String last, int years, Company corp) {
        super(sal, fName, last, years, corp);
    }


    @NotNull
    @Override
    public String toString() {
        return "Pilot{" +
                "typeCertifications=" + typeCertifications +
                '}';
    }
}
