/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/**
 * (c) Erik Malmstrom-Partridge 2014 This code is provided as-is without any
 * warranty This source code or its compiled program may not be redistributed in
 * any way
 *
 */
package com.takeoffsim.models.people;

import com.takeoffsim.models.economics.Company;

import java.io.Serializable;

/**
 * @author Erik
 */
public class Executive extends Employee implements Serializable {

    public Executive(int sal, String fName, String last, int years, Company corp) {
        super(sal, fName, last, years, corp);
    }


}
