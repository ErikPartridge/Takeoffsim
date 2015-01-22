/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.airline;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
@Data
public class Alliance {

    private final List<Airline> members = new ArrayList<>();

    private String name;

    public Alliance(String name){
        this.name = name;
        Alliances.put(this);
    }
}
