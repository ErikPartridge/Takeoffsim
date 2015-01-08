/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline.enums;

import org.jetbrains.annotations.NotNull;

/**
 * @author Erik
 */
public enum Phase {
    BANKRUPT, LOSING, REBUILDING, STABILIZING, SLOWGROWTH, EXPANSION, TAKETHEWORLD;


    @NotNull
    public static Phase find(@NotNull String name) {
        return valueOf(Phase.class, name);

    }
}