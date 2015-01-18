/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.messages;

import com.takeoffsim.models.aircraft.Airplane;

/**
 * Created by erik on 12/5/14.
 */
class MaintenanceMessage {

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

    @Override
    public String toString() {
        return "MaintenanceMessage{" +
                "airplane=" + airplane +
                ", type='" + type + '\'' +
                '}';
    }
}
