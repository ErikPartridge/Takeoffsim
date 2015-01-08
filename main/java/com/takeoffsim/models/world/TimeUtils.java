/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/** (c) Erik Malmstrom-Partridge 2014
 This code is provided as-is without any warranty
 This source code or its compiled program may not be redistributed in any way
 **/
package com.takeoffsim.models.world;

import com.takeoffsim.models.airline.Flight;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;


public class TimeUtils {


    public static int minutesBetween(@NotNull LocalDateTime one, @NotNull LocalDateTime two) {
        final int MIN_PER_DAY = 1440;
        int minutes = 0;
        int dayOne = one.getDayOfYear();
        int dayTwo = two.getDayOfYear();
        if (dayOne == dayTwo) {
            minutes = 60 * two.getHour() + two.getMinute() - 60 * one.getHour() - one.getMinute();
            return minutes;
        }
        if (dayOne > dayTwo) {
            while (dayOne > dayTwo + 1) {
                minutes -= MIN_PER_DAY;
                dayOne--;
                minutes -= 60 * one.getHour() + one.getMinute();
                minutes -= 60 * (23 - two.getHour()) + (60 - two.getMinute());
            }
        } else {
            while (dayTwo > (dayOne + 1)) {
                minutes += MIN_PER_DAY;
                dayTwo--;
            }
            minutes += 60 * two.getHour() + two.getMinute();
            minutes += 60 * (23 - one.getHour()) + (60 - one.getMinute());
        }

        return minutes;
    }

    public static int layover(@NotNull Flight a, @NotNull Flight b) {
        return minutesBetween(a.getArrivesGmt(), b.getDepartsGmt());
    }


}
