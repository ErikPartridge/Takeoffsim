/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 *(c) Erik Malmstrom-com.erikpartridge 2013, 2014
 */

package com.takeoffsim.models.economics;


import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;


public class Companies implements Serializable {


    static final long serialVersionUID = -443008900668L;


    static ConcurrentHashMap<String, Company> companies = new ConcurrentHashMap<>(400);


    private Companies() {
    }

    public static void update(){
        Stream<Company> companyStream = companies.values().stream();
        companyStream.parallel().forEach(Companies::bill);
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

    public static void bill(Company c){

    }

}
