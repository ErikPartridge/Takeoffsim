/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.threads;

import com.takeoffsim.models.world.Country;
import com.takeoffsim.models.world.RegistrationGenerator;

/**
 * Created by Erik.
 */
public class RegistrationThread extends Thread {

    private final Country country;


    public RegistrationThread(Country country) {
        this.country = country;
    }

    @Override
    public void run() {
        country.setRegistrations(new RegistrationGenerator().reformatString(country.getRegex()).getAllMatchedStrings());
    }

    public static void setRegistrations(Country c){
        c.setRegistrations(new RegistrationGenerator().reformatString(c.getRegex()).getAllMatchedStrings());
    }

    @Override
    public String toString() {
        return "RegistrationThread{" +
                "country=" + country +
                '}';
    }
}
