/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.world;

import com.mifmif.common.regex.Generex;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Erik on 9/12/14.
 */
public class RegistrationGenerator {

    public RegistrationGenerator() {
        super();
    }

    /**
     * Synchronized, re-processes TAP Regex and make a Generex object out of it.
     */
    @NotNull
    public Generex reformatString(String s) {
        String regex = s;
        if (regex.contains("\\\\d")) {
            regex = regex.replace("\\\\d", ",");
            regex = regex.replace(",", "[0-9]!");
            int index = regex.indexOf("!");
            regex = regex.substring(0, index + 2) + "}" + regex.substring(index + 2);
            regex = regex.replace("!", "{");
        }
        if (regex.contains("\\\\s")) {
            regex = regex.replace("\\\\s", ",");
            regex = regex.replace(",", "[A-Z]!");
            int index = regex.indexOf("!");
            regex = regex.substring(0, index + 2) + "}" + regex.substring(index + 2);
            regex = regex.replace("!", "{");
        }
        if (regex.contains("\\d")) {
            regex = regex.replace("\\d", ",");
            regex = regex.replace(",", "[0-9]!");
            int index = regex.indexOf("!");
            regex = regex.substring(0, index + 2) + "}" + regex.substring(index + 2);
            regex = regex.replace("!", "{");
        }
        if (regex.contains("\\s")) {
            regex = regex.replace("\\s", ",");
            regex = regex.replace(",", "[A-Z]!");
            int index = regex.indexOf("!");
            regex = regex.substring(0, index + 2) + "}" + regex.substring(index + 2);
            regex = regex.replace("!", "{");
        }
        int index = regex.indexOf("-");
        return new Generex(regex);
    }
}
