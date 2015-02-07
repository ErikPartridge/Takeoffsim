/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.server;

import com.takeoffsim.services.GreatCircle;

import java.io.IOException;
import java.util.Random;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
public class Launcher {

    public static void main(String[] args) throws IOException {
        double latOne = (new Random().nextDouble() - .5) * 360;
        double latTwo = (new Random().nextDouble() - .5) * 360;
        double lonTwo = (new Random().nextDouble() - .5) * 180;
        double lonOne = (new Random().nextDouble() - .5) * 180;
        long time = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            new GreatCircle().distance(latOne, latTwo, lonOne, lonTwo);
        }
        time = System.nanoTime() - time;
        System.out.println(time);
        System.exit(3);
        Thread t = new Thread(() -> new Main().start());
        t.setDaemon(false);
        t.start();
        while(true){
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
