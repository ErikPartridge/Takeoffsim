/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.world;

import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Erik
 */
@CommonsLog
public final class Countries {

    @NotNull
    private static final Map<String, Country> countries = new ConcurrentHashMap<>();

    @NotNull
    private static final Map<String, Country> tapCodes = new ConcurrentHashMap<>();

    private Countries() {
    }

    public static void clear(){
        countries.clear();
        tapCodes.clear();
    }

    public static void put(Country country){
        putCountry(country.getIso(), country);
    }

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
    public static Map<String,Country> getCountries() {
        return Collections.unmodifiableMap(countries);
    }

}
