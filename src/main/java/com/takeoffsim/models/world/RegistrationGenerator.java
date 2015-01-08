/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
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
        String string = s;
        if (string.contains("\\\\d")) {
            string = string.replace("\\\\d", ",");
            string = string.replace(",", "[0-9]!");
            int index = string.indexOf("!");
            string = string.substring(0, index + 2) + "}" + string.substring(index + 2);
            string = string.replace("!", "{");
        }
        if (string.contains("\\\\s")) {
            string = string.replace("\\\\s", ",");
            string = string.replace(",", "[A-Z]!");
            int index = string.indexOf("!");
            string = string.substring(0, index + 2) + "}" + string.substring(index + 2);
            string = string.replace("!", "{");
        }
        if (string.contains("\\d")) {
            string = string.replace("\\d", ",");
            string = string.replace(",", "[0-9]!");
            int index = string.indexOf("!");
            string = string.substring(0, index + 2) + "}" + string.substring(index + 2);
            string = string.replace("!", "{");
        }
        if (string.contains("\\s")) {
            string = string.replace("\\s", ",");
            string = string.replace(",", "[A-Z]!");
            int index = string.indexOf("!");
            string = string.substring(0, index + 2) + "}" + string.substring(index + 2);
            string = string.replace("!", "{");
        }
        int index = string.indexOf("-");
        return new Generex(string);
    }
}
