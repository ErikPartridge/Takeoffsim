/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.world;

import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Erik
 */
@CommonsLog
public class Countries {

    @NotNull
    private static ConcurrentHashMap<String, Country> countries = new ConcurrentHashMap<>();

    @NotNull
    private static ConcurrentHashMap<String, Country> tapCodes = new ConcurrentHashMap<>();

    public static Country getCountry(String s) {
        return countries.get(s);
    }

    public static Country getTapCountry(String s) {
        return tapCodes.get(s);
    }

    public static void putCountry(String s, Country country) {
        if(s != null)
            countries.put(s, country);
    }

    public static void putTapCountry(String s, Country country) {
        tapCodes.put(s, country);
    }

    @NotNull
    public static ConcurrentHashMap<String, Country> getCountries() {
        return countries;
    }

}
