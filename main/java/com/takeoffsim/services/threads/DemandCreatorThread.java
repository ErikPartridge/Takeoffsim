/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services.threads;

import com.takeoffsim.services.demand.DemandCreator;

/**
 * @since version 0.7.2-alpha. (c) Erik Partridge 2015
 */
public class DemandCreatorThread extends Thread {
    
    public void run(){
        new DemandCreator().createAllDemand();
    }
}
