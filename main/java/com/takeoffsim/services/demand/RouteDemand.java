/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.services.demand;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.takeoffsim.models.airport.Airport;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.math3.util.FastMath;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erik
 */
@CommonsLog
public final class RouteDemand{

    /**
     *
     * @param aptOne the departure airport
     * @param aptTwo the arrival airport
     * @return the demand as a double WEEKLY
     */
    public static double demand(@NotNull Airport aptOne, @NotNull Airport aptTwo) {
        final double one = aptOne.getAllocatedDemand();
        final double two = aptTwo.getAllocatedDemand();
        double dist = LatLngTool.distance(new LatLng(aptOne.getLatitude(),aptOne.getLongitude()), new LatLng(aptTwo.getLatitude(), aptTwo.getLongitude()), LengthUnit.MILE);
        dist = (dist < 10) ? dist = 25000 : dist;
        final double powOne = FastMath.pow(one, 2.5d);
        final double powTwo = FastMath.pow(two, 2.5d);
        final double multiplied = powOne * powTwo / FastMath.pow(dist, 2);
        final double logged = FastMath.log(multiplied);
        final double coefficiented = (logged * .155710d) - 7.65;
        final double ajusted = (coefficiented < 0) ? .5 : coefficiented;
        final double raw = FastMath.pow(FastMath.pow(Math.E, ajusted), 2);
        return raw + 3;
    }

}
