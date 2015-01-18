/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Erik
 */
class ConnectingRoute {
    private final List<Route> routes = new ArrayList<>();


    /**
     * @return the routes
     */
    public List<Route> getRoutes() {
        return Collections.unmodifiableList(routes);
    }


    @NotNull
    @Override
    public String toString() {
        return "ConnectingRoute{" +
                "routes=" + routes +
                '}';
    }
}
