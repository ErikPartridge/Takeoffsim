/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/** (c) Erik Malmstrom-Partridge 2014
 This code is provided as-is without any warranty
 This source code or its compiled program may not be redistributed in any way
 **/
package com.takeoffsim.main;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

import static java.lang.System.getProperty;
import static java.util.logging.Logger.getLogger;


public final class MLog {

    /**
     *
     */

    private static final File f = new File(getProperty("user.home" + "/StormySkies/log"));
    private static FileWriter log;
    private static boolean instantiated;

    private MLog() {
        try {
            log = new FileWriter(f);
        } catch (IOException ex) {
            getLogger(MLog.class.getName()).log(Level.SEVERE, null, ex);
        }
        instantiated = true;
    }

    public static void log(@NotNull String message) {
        if (!instantiated)
            instantiate();

        try {
            log.write(message);
        } catch (IOException ex) {
            getLogger(MLog.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void instantiate() {
        try {
            log = new FileWriter(f);
        } catch (IOException ex) {
            getLogger(MLog.class.getName()).log(Level.SEVERE, null, ex);
        }
        instantiated = true;
    }

    private static void close() {
        try {
            log.close();
        } catch (IOException ex) {
            getLogger(MLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

