/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge
 */

package com.takeoffsim.models.world;

import com.rits.cloning.Cloner;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static java.time.LocalDateTime.of;


public class Time implements Serializable {

    static final long serialVersionUID = 610041035904L;
    public static final LocalDateTime currentTime = of(2014, Month.JANUARY, 1, 0, 0);

    private Time() {
    }

    public static LocalDate getDateInstance(){
        return LocalDate.of(currentTime.getYear(), currentTime.getMonth(),currentTime.getDayOfMonth());
    }

    public static LocalDateTime getDateTimeInstance(){
        return new Cloner().shallowClone(currentTime);
    }

    public static int getQuarter(){
        switch(currentTime.getMonth()){
            case JANUARY: return 1;
            case FEBRUARY: return 1;
            case MARCH: return 1;
            case APRIL: return 2;
            case MAY: return 2;
            case JUNE: return 2;
            case JULY: return 3;
            case AUGUST: return 3;
            case SEPTEMBER: return 3;
            case OCTOBER: return 4;
            case NOVEMBER: return 4;
            case DECEMBER: return 4;
        }
        return 1;
    }

}
