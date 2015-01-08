/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.threads;

import com.takeoffsim.models.airline.GlobalRoute;
import com.takeoffsim.models.airline.GlobalRoutes;
import com.takeoffsim.demand.DemandCreator;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by Erik in 11, 2014.
 */
public class DemandRouteThread extends Thread {

    private static ArrayList<GlobalRoute> routes;

    public DemandRouteThread(){
        for(GlobalRoute route : GlobalRoutes.globalRoutes.values()){
            routes.add(route);
        }
    }

    public void run(){
        while(!routes.isEmpty()) {
            GlobalRoute route = routes.remove(new SecureRandom().nextInt(routes.size()));
            route.setAvailableDemand(route.getAvailableDemand() + Math.round((float) new DemandCreator().createDemandOnRoute(route)));
        }
    }
}
