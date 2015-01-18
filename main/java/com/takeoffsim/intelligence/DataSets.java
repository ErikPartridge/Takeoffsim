/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.intelligence;

import org.encog.ml.data.MLDataSet;
import org.encog.neural.pnn.BasicPNN;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Erik on 10/17/14.
 */
final class DataSets {

    private static final Map<String, MLDataSet> datasets = new ConcurrentHashMap<>(60, .7f);

    private static final Map<String, BasicPNN> networks = new ConcurrentHashMap<>();

    private DataSets() {
    }

    public static MLDataSet getDataSet(String str) {
        return datasets.get(str);
    }

    public static BasicPNN getNN(String str) {
        return networks.get(str);
    }

    public static Map<String, BasicPNN> getNetworks() {
        return Collections.unmodifiableMap(networks);
    }


}
