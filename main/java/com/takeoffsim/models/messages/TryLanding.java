/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.messages;

import com.takeoffsim.models.aircraft.Airplane;
import com.takeoffsim.models.airport.Airport;
import lombok.Data;

/**
 * Created by erik on 12/6/14.
 */
@Data
public class TryLanding {

    private final Airplane airplane;

    private final Airport airport;

    public TryLanding(Airplane airplane, Airport apt){
        this.airplane = airplane;
        this.airport = apt;
    }

}
