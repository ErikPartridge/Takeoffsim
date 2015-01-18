/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.threads;

import com.takeoffsim.demand.DemandCreator;
import com.takeoffsim.models.world.City;
import com.takeoffsim.models.world.Region;
import com.takeoffsim.models.world.Regions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Erik in 11, 2014.
 */
public class AllocateDemandThread extends Thread {

    @SuppressWarnings("StaticNonFinalField")
    private static List<Region> regionsToGo = null;

    public AllocateDemandThread(){
        if(regionsToGo == null){
            regionsToGo = new ArrayList<>();
            for(Region region: Regions.getRegionsList()){
                regionsToGo.add(region);
            }
        }
    }

    @Override
    public void run() {
        while(!regionsToGo.isEmpty()){
            Region region = regionsToGo.remove(new Random().nextInt(regionsToGo.size()));
            for(City city: region.getPoints()){
                new DemandCreator().allocateDemand(region.getAirportsInRegion(), city);
            }
        }
    }
}
