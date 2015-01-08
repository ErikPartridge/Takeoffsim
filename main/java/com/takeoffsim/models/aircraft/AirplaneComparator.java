/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-Partridge 2014
 */

package com.takeoffsim.models.aircraft;

import java.security.InvalidParameterException;
import java.util.Comparator;

/**
 * @author Erik Malmstrom-Partridge
 * @since 0.3 Alpha
 */
final class AirplaneComparator implements Comparator {

    /**
     *
     * @param o1 an airplane
     * @param o2 an airplane
     * @return if of different types, compare names of types, then by names
     */
    @Override
    public int compare(Object o1, Object o2) throws InvalidParameterException{
        Airplane a1 = (Airplane) o1;
        Airplane a2 = (Airplane) o2;
        if (a1.getType() != a2.getType()) {
            return a1.getType().getName().compareTo(a2.getType().getName());
        } else {
            return (int) Math.round(a2.getAge() - a1.getAge() * 1000);
        }
    }


}
