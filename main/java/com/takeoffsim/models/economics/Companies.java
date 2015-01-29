/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 *(c) Erik Malmstrom-com.erikpartridge 2013, 2014
 */

package com.takeoffsim.models.economics;


import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;


public final class Companies implements Serializable {


    static final long serialVersionUID = -443008900668L;


    private static final Map<String, Company> companies = new ConcurrentHashMap<>(400);

    public static void clear(){
        companies.clear();
    }

    private Companies() {
    }

    public static void update(){
        Stream<Company> companyStream = companies.values().stream();
        companyStream.parallel().forEach(Companies::bill);
    }

    public static List<Company> companyList(){
        ArrayList<Company> comps = new ArrayList<>();
        companies.values().forEach(comps::add);
        return comps;
    }

    public static Map<String, Company> getCompanies(){
        return Collections.unmodifiableMap(companies);
    }

    /**
     * @param key  the key for this company- a String
     * @param corp the corporation
     */
    public static void put(String key, Company corp) {
        companies.put(key, corp);
    }

    @Nullable
    public static Company get(String key) {
        return companies.get(key);
    }

    public static ArrayList<Contract> getAllContracts(){
        ArrayList<Contract> contracts = new ArrayList<>();
        companies.values().stream().parallel().forEach(company -> contracts.addAll(company.getContracts()));
        return contracts;
    }

    static void bill(Company c){
        throw new UnsupportedOperationException("no payment today");
    }

    public static void put(Company company) {
        put(company.getName(), company);
    }
}
