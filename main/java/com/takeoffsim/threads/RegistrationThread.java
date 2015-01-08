/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.threads;

import com.takeoffsim.models.world.Country;
import com.takeoffsim.models.world.RegistrationGenerator;

/**
 * Created by Erik.
 */
public class RegistrationThread extends Thread {

    private Country country;


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
}
