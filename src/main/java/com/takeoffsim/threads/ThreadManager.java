/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Erik in 11, 2014.
 */
public class ThreadManager {


    private static ExecutorService exe = Executors.newFixedThreadPool(6);

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
