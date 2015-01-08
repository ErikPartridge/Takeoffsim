/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/** (c) Erik Malmstrom-Partridge 2014
 This code is provided as-is without any warranty
 This source code or its compiled program may not be redistributed in any way
 **/
package com.takeoffsim.main;


import com.takeoffsim.services.Serialize;

public class Config {

    public static String nameOfSim;


    public static String getPath() {
        return Serialize.homeDirectory();
    }
}
