/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.threads;

import com.takeoffsim.demand.DemandCreator;
import com.takeoffsim.models.airline.GlobalRoute;
import com.takeoffsim.models.airline.GlobalRoutes;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Erik in 11, 2014.
 */
public class DemandRouteThread extends Thread {

    @SuppressWarnings("StaticNonFinalField")
    private static List<GlobalRoute> routes;

    public DemandRouteThread(){
        routes.addAll(GlobalRoutes.globalRoutes.values().stream().collect(Collectors.toList()));
    }

    public void run(){
        while(!routes.isEmpty()) {
            GlobalRoute route = routes.remove(new SecureRandom().nextInt(routes.size()));
            route.setAvailableDemand(route.getAvailableDemand() + Math.round((float) new DemandCreator().createDemandOnRoute(route)));
        }
    }
}
