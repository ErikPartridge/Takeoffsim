/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline;

import java.util.Comparator;

/**
 * @author Erik
 */
public class FlightDepartComparator implements Comparator {

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
