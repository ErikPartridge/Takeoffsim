/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services.threads;

import com.google.common.io.Files;
import com.takeoffsim.services.Config;
import com.takeoffsim.services.Serialize;
import lombok.extern.apachecommons.CommonsLog;

import java.io.File;
import java.io.IOException;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
@CommonsLog
public class SerializeThread extends Thread {

    private static boolean executing = false;

    public void run(){
        while(true){
            try {
                Thread.sleep(60000);
                executing = false;
            }catch (InterruptedException e){
                log.debug(e);
            }
            executing = true;
            if(Config.nameOfSim != null) {
                Serialize.write(".temp-save.tss");
                File tmp = new File(Serialize.homeDirectory() + "saves/" + ".temp-save.tss");
                File dest = new File(Serialize.homeDirectory() + "saves/" + Config.nameOfSim + ".tss");
                try {
                    Files.move(tmp, dest);
                    tmp.delete();
                } catch (IOException e) {
                    log.error(e);
                }
            }
            executing = false;
        }
    }

    public static void waitToFinish(){
        while(true){
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                log.debug(e);
            }
            if(!executing){
                return;
            }
        }
    }
}
