/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.intelligence;

import com.google.common.collect.HashBiMap;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.pnn.BasicPNN;

import java.util.HashMap;

/**
 * Created by Erik on 10/17/14.
 */
public class DataSets {

    private static HashMap<String, MLDataSet> datasets = new HashMap<>(60, .7f);

    private static HashBiMap<String, BasicPNN> networks = HashBiMap.create();

    public static MLDataSet getDataSet(String str) {
        return datasets.get(str);
    }

    public static BasicPNN getNN(String str) {
        return networks.get(str);
    }

    public static HashBiMap<String, BasicPNN> getNetworks() {
        return networks;
    }


}
