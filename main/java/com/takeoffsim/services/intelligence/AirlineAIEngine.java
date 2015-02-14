/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services.intelligence;

import com.takeoffsim.models.airline.Airline;
import lombok.extern.apachecommons.CommonsLog;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;

/**
 * @since version 0.7-alpha. (c) Erik Partridge 2015
 */
@CommonsLog
public class AirlineAIEngine {

    private final MLDataSet dataSet;
    
    private final Airline airline;
    
    private BasicNetwork network;
    
    private MLData start;
    
    public AirlineAIEngine(Airline a) {
        this.airline = a;
        this.dataSet = new BasicMLDataSet();
    }
    
    public AirlineAIEngine(Airline a, MLDataSet dataSet){
        this.airline = a;
        this.dataSet = dataSet;
        
    }
    
    public void runFleet(){
    }
    
    
    public AirlineAIEngine addDataPoint(MLData input, MLData output){
        return this;
    }
    
    public AirlineAIEngine addEndPoint(MLData ideal){
        return this;
    }
}
