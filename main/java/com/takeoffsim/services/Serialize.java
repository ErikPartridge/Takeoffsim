/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */


package com.takeoffsim.services;

import com.jcabi.aspects.Cacheable;
import com.jcabi.aspects.RetryOnFailure;
import com.takeoffsim.airport.Airport;
import com.takeoffsim.airport.Airports;
import com.takeoffsim.main.Config;
import lombok.Cleanup;
import lombok.extern.apachecommons.CommonsLog;

import java.io.*;
import java.util.Optional;

@CommonsLog
public class Serialize {

    private static boolean isExecuting = false;
    private Serialize() {
    }


    /**
     * This will serialize all the airports
     */
    public void writeAirports(){
        isExecuting = true;
        File file = null;
        if(isMac()){
            File directory = new File(homeDirectory() + "saves/" + Config.nameOfSim + "/");
            //noinspection ResultOfMethodCallIgnored
            directory.mkdirs();
            file = new File(homeDirectory() + "saves/" + Config.nameOfSim + "/" + "Airports.tss");
        }else if(isWindows()){
            File directory = new File(homeDirectory() + "saves/" + Config.nameOfSim + "/");
            directory.mkdirs();
            file = new File(homeDirectory() + "saves/" + Config.nameOfSim + "/" + "Airports.tss");
        }else{
            File directory = new File(homeDirectory() + "saves/" + Config.nameOfSim + "/");
            directory.mkdirs();
            file = new File(homeDirectory() + "saves/" + Config.nameOfSim + "/" + "Airports.tss");
        }
        ObjectOutputStream out = null;
        try{
            @Cleanup FileOutputStream fos = new FileOutputStream(file);
            out = setup(fos).get();
        } catch (FileNotFoundException e) {
            log.error(e);
        }
        assert out != null;
        for(Airport airport : Airports.getAirports().values()){
            try{
                out.writeObject(airport);
                log.trace("Wrote airport " + airport.getIcao());
            } catch (IOException e) {
                log.error(e);
            }
        }
        try{
            out.close();
        }catch(IOException e ){
            log.error(e.getMessage());
        }
        isExecuting = false;
    }

    /**
     *
     * @param fileOut the fileoutput stream to setup
     * @return an ObjectOutputStream contained in an optional
     */
    @RetryOnFailure
    private Optional<ObjectOutputStream> setup(FileOutputStream fileOut) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(fileOut);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return Optional.of(out);
    }

    /**
     * @return if this operating system is Windows
     */
    @Cacheable(forever = true)
    private static boolean isWindows(){
        return System.getProperty("os.name").startsWith("Windows");
    }

    /**
     * @return if this operating system is Mac
     */
    @Cacheable(forever = true)
    private static boolean isMac(){
        return System.getProperty("os.name").startsWith("Mac");
    }

    /**
     * @return if this operating system isn't Mac or Windows
     */
    @Cacheable(forever = true)
    public static boolean isLinux(){
        return !(isMac() || isWindows());
    }

    /**
     * @return the directory in which all data should be stored
     */
    @Cacheable(forever = true)
    public static String homeDirectory(){
        if(isMac()){
            return System.getProperty("user.home") + "/Library/" + "TakeoffSim/";
        }else if(isWindows()){
            return System.getenv("ProgramFiles") + "/TakeoffSim/";
        }else{
            return System.getProperty("user.home") + "/TakeoffSim/";
        }
    }

}
