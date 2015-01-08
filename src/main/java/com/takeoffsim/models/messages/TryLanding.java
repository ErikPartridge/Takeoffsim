/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.models.messages;

import com.takeoffsim.models.aircraft.Airplane;
import com.takeoffsim.airport.Airport;
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
