/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.economics;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Erik in 11, 2014.
 */
@Data
public class Contract implements Serializable {

    static final long serialVersionUID = -212012033;

    private Company from;

    private Company to;

    private ArrayList<Bill> bills;

    public Contract(Company from, Company to, Bill ... billList){
        bills.addAll(Arrays.asList(billList));
    }

    Contract(){
        throw new UnsupportedOperationException("Unsupported");
    }

}
