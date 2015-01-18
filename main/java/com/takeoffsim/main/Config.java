/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/** (c) Erik Malmstrom-Partridge 2014
 This code is provided as-is without any warranty
 This source code or its compiled program may not be redistributed in any way
 **/
package com.takeoffsim.main;


import com.jcabi.aspects.Cacheable;
import com.takeoffsim.services.Serialize;
import lombok.extern.apachecommons.CommonsLog;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

@CommonsLog
public final class Config {

    public static String nameOfSim;

    private Config() {
    }


    public static String themePath(){return "/home/erik/Takeoffsim/themes/TakeoffSim-Themes/default/";}
    public static String getPath() {
        return Serialize.homeDirectory();
    }
    
    @Cacheable(lifetime = 70, unit = TimeUnit.SECONDS, forever = false)
    public static boolean isWebConnected(){
        try{
            InetAddress ip = Inet4Address.getByName("takeoffsim.com");
            return ip.isReachable(450);
        } catch (UnknownHostException e) {
            log.info("TakeoffSim is down. time to call the web guy.");
            return false;
        } catch(IOException ignored){
            log.info("Cannot connect to TakeoffSim.com");
            return false;
        }
    }
}
