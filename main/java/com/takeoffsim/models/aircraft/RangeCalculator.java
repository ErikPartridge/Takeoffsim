/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.aircraft;

/**
 * Created by Erik in 09, 2014.
 */
final class RangeCalculator {

    private RangeCalculator() {
    }

    /**
     *
     * @param airplane the airplane to calculate the range for
     * @param payload the payload (lbs) to calculate the range for
     * @return the maximum range in NM
     */
    public static double range(Airplane airplane, double payload) {
        //Calibrate the range for the range factor
        final double calibratedRange = airplane.getType().getRange() * airplane.getEngine().getType().getRangeFactor();
        final double mew = airplane.getType().getMtow() - airplane.getType().getOew();
        if (payload > mew + 1) {
            return 0;
        } else if (payload > mew - 2) {
            return airplane.getType().getRange() * .65d;
        } else if (payload > mew * .72) {
            return (.65 * calibratedRange) + .075d * (airplane.getType().getMtow() - payload) * calibratedRange;
        } else return payload > airplane.getType().getOew() ? calibratedRange - .02272727272 * mew : 0;
    }

    /**
     * @param airplane the airplane to calculate payload for
     * @param range the range in NM to calculate the payload for
     * @return the maximum payload in lbs
     */
    public static double payload(Airplane airplane, double range) {
        final double mew = airplane.getType().getMtow() - airplane.getType().getOew();
        if (range < airplane.getType().getRange() * airplane.getEngine().getType().getRangeFactor() * .650) {
            return airplane.getType().getMtow();
        } else if (range < airplane.getType().getRange() * airplane.getEngine().getType().getRangeFactor() * .840) {
            final double rangeValue = airplane.getType().getRange() * airplane.getEngine().getType().getRangeFactor();
            final double rangePercent = (range - rangeValue * .650) / rangeValue;
            return mew - mew * 2 * rangePercent;
        } else if (range < airplane.getType().getRange()) {
            final double rangeValue = airplane.getType().getRange() * airplane.getEngine().getType().getRangeFactor();
            final double rangePercent = (range - rangeValue * .840) / rangeValue;
            if (mew * .72 - mew * 3 * rangePercent < 0) {
                return 0;
            }
            return mew * .72 - mew * 3 * rangePercent;
        } else {
            return 0;
        }
    }


}
