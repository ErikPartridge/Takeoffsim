/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services;

import com.javadocmd.simplelatlng.util.LatLngConfig;
import com.javadocmd.simplelatlng.util.LengthUnit;
import org.apache.commons.math3.util.FastMath;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
public class GreatCircle {

    private static final double NM = LatLngConfig.getEarthRadius(LengthUnit.NAUTICAL_MILE);
    
    public static double distance(double lat1, double lon1, double lat2, double lon2){
        final double dLat = Math.toRadians(lat2 - lat1);
        final double dLon = Math.toRadians(lon2 - lon1);
        final double lat1R = Math.toRadians(lat1);
        final double lat2R = Math.toRadians(lat2);
        final double sinLat = Math.sin(dLat / 2);
        final double sinLon = Math.sin(dLon / 2);
        final double a = sinLat * sinLat + sinLon * sinLon * Math.cos(lat1R) * Math.cos(lat2R);
        final double c = 2 * FastMath.asin(Math.sqrt(a));
        return NM * c;
    }
    
    public static double fastDistance(double lat1, double lon1, double lat2, double lon2){
        final double lon1R = Math.toRadians(lon1);
        final double lon2R = Math.toRadians(lon2);
        final double lat1R = Math.toRadians(lat1);
        final double lat2R = Math.toRadians(lat2);
        final double x = (lon2R - lon1R) * Math.cos( 0.5*(lat2R+lat1R));
        final double y = lat2R - lat1R;
        return NM * Math.sqrt(x * x + y*y);
    }
}
