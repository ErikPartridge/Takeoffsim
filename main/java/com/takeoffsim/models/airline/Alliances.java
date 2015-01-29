/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.airline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Alliances {


    private static Map<String, Alliance> alliances = new ConcurrentHashMap<>();

    private Alliances() {
    }

    public static List<Alliance> allianceList(){
        ArrayList<Alliance> list = new ArrayList<>();
        alliances.values().forEach(list::add);
        return list;
    }

    public static Alliance getAlliance(String alliance) {
        return alliances.get(alliance);
    }

    public static void clear(){
        alliances.clear();
    }

    public static void put(Alliance a){
        alliances.put(a.getName(), a);
    }

    public static Map<String, Alliance> getAlliances(){
        return Collections.unmodifiableMap(alliances);
    }
}
