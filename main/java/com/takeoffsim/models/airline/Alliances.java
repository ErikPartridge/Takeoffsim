/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.airline;

import java.util.HashMap;
import java.util.Map;

public final class Alliances {


    private static Map<String, Alliance> alliances = new HashMap<>();

    private Alliances() {
    }

    public static Alliance getAlliance(String alliance) {
        return alliances.get(alliance);
    }

    public static void clear(){
        alliances.clear();
    }
}
