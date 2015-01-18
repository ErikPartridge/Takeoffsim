/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline;

/**
 * @author Erik
 */
public enum Service {
    FIRST(.05), BUSINESS(.2), ECONOMYPLUS(.15), ECONOMY(.6);


    Service(double choosing) {
    }
}
