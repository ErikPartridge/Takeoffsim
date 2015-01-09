/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
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
import java.util.concurrent.TimeUnit;

@CommonsLog
public class Config {

    public static String nameOfSim;


    public static String getPath() {
        return Serialize.homeDirectory();
    }
    
    @Cacheable(lifetime = 70, unit = TimeUnit.SECONDS, forever = false)
    public static boolean webConnected(){
        try{
            InetAddress ip = Inet4Address.getByName("takeoffsim.com");
            return ip.isReachable(450);
        }catch(IOException e){
            log.info("Cannot connect to TakeoffSim.com");
            return false;
        }
    }
}
