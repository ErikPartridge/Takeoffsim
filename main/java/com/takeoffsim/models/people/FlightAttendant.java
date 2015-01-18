/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/** (c) Erik Malmstrom-Partridge 2014
 This code is provided as-is without any warranty
 This source code or its compiled program may not be redistributed in any way
 * **/
package com.takeoffsim.models.people;

import com.takeoffsim.models.economics.Company;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author Erik
 */


public class FlightAttendant extends Employee implements Serializable {

    private int seniority;


    public FlightAttendant(int seniority, int sal, String fName, String last, int years, Company corp) {
        super(sal, fName, last, years, corp);
        this.seniority = seniority;
    }

    @NotNull
    @Override
    public String toString() {
        return "FlightAttendant{" +
                "salary=" + super.getSalary() +
                ", seniority=" + getSeniority() +
                '}';
    }

    int getSeniority() {
        return seniority;
    }

    public void setSeniority(int seniority) {
        this.seniority = seniority;
    }
}
