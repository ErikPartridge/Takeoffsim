/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
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

import java.util.ArrayList;
import java.util.List;


public
@Data @CommonsLog
class Country {

    private String iso;

    private String tapCode;

    private String regex;

    @NotNull
    private ArrayList<Country> banned = new ArrayList<>();

    @NotNull
    private ArrayList<City> cities = new ArrayList<>();

    private List<String> registrations;

    public Country(String iso, String tapCode, String regex) {
        this.iso = iso;
        this.tapCode = tapCode;
        this.regex = regex;
        RegistrationThread.setRegistrations(this);
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
