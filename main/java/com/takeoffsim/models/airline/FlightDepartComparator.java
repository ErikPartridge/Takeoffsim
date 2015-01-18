/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline;

import java.util.Comparator;

/**
 * @author Erik
 */
class FlightDepartComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        if (!(o1 instanceof Flight)) {
            throw new IllegalArgumentException("Not of type flight");
        }
        if (!(o2 instanceof Flight)) {
            throw new IllegalArgumentException("Not of type flight");
        }

        Flight f1 = (Flight) o1;
        Flight f2 = (Flight) o2;

        return (f1.getActualDeparture().compareTo(f2.getActualDeparture()));
    }

}
