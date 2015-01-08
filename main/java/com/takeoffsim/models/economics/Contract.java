/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.models.economics;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

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
        for(Bill bill : billList){
            bills.add(bill);
        }
    }

    public Contract(){
        throw new UnsupportedOperationException();
    }

}
