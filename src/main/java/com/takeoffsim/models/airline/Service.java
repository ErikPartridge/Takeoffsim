/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
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
        double percentChoosing = choosing;
    }
}
