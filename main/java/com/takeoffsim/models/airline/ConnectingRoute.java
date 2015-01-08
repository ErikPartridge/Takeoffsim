/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * @author Erik
 */
public class ConnectingRoute {
    private ArrayList<Route> routes = new ArrayList<>();


    /**
     * @return the routes
     */
    public ArrayList<Route> getRoutes() {
        return routes;
    }


    /**
     * @param routes the routes to set
     */
    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }


    @NotNull
    @Override
    public String toString() {
        return "ConnectingRoute{" +
                "routes=" + routes +
                '}';
    }
}
