/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.server;

import java.io.IOException;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
public class Launcher {

    public static void main(String[] args) throws IOException {
        new Main().start();
        while(true){
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
