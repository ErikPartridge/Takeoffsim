/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Erik in 11, 2014.
 */
public class ThreadManager {


    private static ExecutorService exe = Executors.newFixedThreadPool(4);

    public static void submit(Runnable runnable){
        exe.submit(runnable);
    }

    public static void shutdown(){
        exe.shutdown();
    }

    public static void kill(){
        exe.shutdownNow();
    }

}
