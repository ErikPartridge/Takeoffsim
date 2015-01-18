/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-Partridge 2014
 */

package com.takeoffsim.models.aircraft;

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
    public int compare(Object o1, Object o2) {
        Airplane a1 = (Airplane) o1;
        Airplane a2 = (Airplane) o2;
        return a1.getType() != a2.getType() ? a1.getType().getName().compareTo(a2.getType().getName()) : (int) Math.round(a2.getAge() - a1.getAge() * 1000);
    }


}
