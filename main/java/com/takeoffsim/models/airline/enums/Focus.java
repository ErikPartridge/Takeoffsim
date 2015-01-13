/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline.enums;

import org.jetbrains.annotations.NotNull;

/**
 * @author Erik
 */
public enum Focus {
    GLOBAL, CONTINENTAL, DOMESTIC, REGIONAL, STATE;


    @NotNull
    public static Focus find(@NotNull String name) {
        return valueOf(Focus.class, name);
    }
}

