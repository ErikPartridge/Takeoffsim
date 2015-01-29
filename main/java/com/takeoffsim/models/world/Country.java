/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge
 */

package com.takeoffsim.models.world;

import com.neovisionaries.i18n.CountryCode;
import com.takeoffsim.threads.RegistrationThread;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public
@Data @CommonsLog
class Country implements Serializable {

    private String iso;

    private String tapCode;

    private String regex;

    @NotNull
    private List<Country> banned = new ArrayList<>();

    @NotNull
    private Collection<City> cities = new ArrayList<>();

    private List<String> registrations;

    public Country(String iso, String tapCode, String regex) {
        this.iso = iso;
        this.tapCode = tapCode;
        this.regex = regex;
        RegistrationThread.setRegistrations(this);
    }

    public Country(){

    }

    public void addCity(City city){
        cities.add(city);
    }

    @NotNull
    @Override
    public String toString() {
        return "Country{" +
                "name='" + CountryCode.getByCode(iso) + '\'' +
                ", iso='" + iso + '\'' +
                ", banned=" + banned +
                ", cities=" + cities +
                '}';
    }
}
