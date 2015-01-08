/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.models.messages;

import com.takeoffsim.models.aircraft.Airplane;

/**
 * Created by erik on 12/5/14.
 */
public class MaintenanceMessage {

    private final Airplane airplane;

    private final String type;

    public MaintenanceMessage(Airplane airplane, String type) {
        this.airplane = airplane;
        this.type = type;
    }


    public Airplane getAirplane() {
        return airplane;
    }

    public String getType() {
        return type;
    }
}
