/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */


package com.takeoffsim.services;

import com.jcabi.aspects.Cacheable;
import com.jcabi.aspects.RetryOnFailure;
import com.takeoffsim.airport.Airport;
import com.takeoffsim.airport.Airports;
import com.takeoffsim.main.Config;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.views.server.Main;
import lombok.extern.apachecommons.CommonsLog;

import java.io.*;
import java.rmi.NoSuchObjectException;
import java.util.Collection;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@SuppressWarnings("ResultOfMethodCallIgnored")
@CommonsLog
public class Serialize {


    @SuppressWarnings("StaticNonFinalField")
    private static boolean isExecuting = false;
    private Serialize() {
    }

    public static void writeAll(){
        writeAirlines();
        writeAirports();
    }

    public static void loadWorld(String worldName) throws NoSuchObjectException{
        while(isExecuting){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.info(e);
            }
        }
        isExecuting = true;
        System.out.println("Begun executing");
        Main.clearAll();
        File directory = new File(homeDirectory() + "saves/" + worldName + "/");
        if(!directory.exists()){
            throw new NoSuchObjectException("No world with name " + worldName);
        }

        loadAirlines(new File(directory.getPath() + "/Airlines.tss"));
        loadAirports(new File(directory.getPath() + "/Airports.tss"));

        isExecuting = false;

    }

    public static void loadAirports(File file){
        Collection<Airport> airports = (Collection<Airport>) rawRead(file);
        System.out.println("Loading Airports");
        long time = System.currentTimeMillis();
        Airports.clear();
        for(Airport a: airports){
            Airports.put(a.getIcao(), a);
        }
        System.out.println("Took " + (System.currentTimeMillis() - time));
    }


    public static void loadAirlines(File file){
        System.out.println("Loading airlines");
        Collection<Airline> airlines = (Collection<Airline>) rawRead(file);
        Airlines.clear();
        for(Airline a: airlines){
            Airlines.put(a.getIcao(), a);
            Airlines.putIcao(a.getName(), a.getIcao());
        }
    }

    public static Object rawRead(File file){
        FileInputStream stream = null;
        ObjectInputStream in = null;
        Object o = null;
        try{
            stream = new FileInputStream(file);
            GZIPInputStream wrapper = new GZIPInputStream(stream);
            in = new ObjectInputStream(wrapper);
            o = in.readObject();
            in.close();
            wrapper.close();
            stream.close();
        } catch (IOException | ClassNotFoundException e) {
            log.error(e);
        }
        return o;
    }


    public static void writeAirlines(){
        while(isExecuting){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.info(e);
            }
        }
        isExecuting = true;
        File directory = new File(homeDirectory() + "saves/" + Config.nameOfSim + "/");
        directory.mkdirs();
        File file = new File(homeDirectory() + "saves/" + Config.nameOfSim + "/Airlines.tss");
        try{
            FileOutputStream fs = new FileOutputStream(file);
            GZIPOutputStream wrapper = new GZIPOutputStream(fs);
            ObjectOutputStream out = new ObjectOutputStream(wrapper);
            out.writeObject(Airlines.getMap().values());
            out.flush();
            wrapper.finish();
            wrapper.close();
            out.close();
            fs.close();
        }catch (IOException e){
            log.error(e);
        }
        isExecuting = false;
    }

    /**
     * This will serialize all the airports
     */
    public static void writeAirports(){
        isExecuting = true;
        File directory = new File(homeDirectory() + "saves/" + Config.nameOfSim + "/");
        directory.mkdirs();
        File file = new File(homeDirectory() + "saves/" + Config.nameOfSim + "/Airports.tss");
        try{
            FileOutputStream fs = new FileOutputStream(file);
            GZIPOutputStream wrapper = new GZIPOutputStream(fs);
            ObjectOutputStream out = new ObjectOutputStream(wrapper);
            out.writeObject(Airports.getAirports().values());
            out.flush();
            wrapper.finish();
            wrapper.close();
            out.close();
            fs.close();
        }catch (IOException e){
            log.error(e);
        }
        isExecuting = false;
    }

    /**
     *
     * @param fileOut the fileoutput stream to setup
     * @return an ObjectOutputStream contained in an optional
     */
    @RetryOnFailure
    private static Optional<ObjectOutputStream> setup(FileOutputStream fileOut) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(fileOut);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        //noinspection ConstantConditions
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
            return System.getProperty("user.home") + "/.takeoffsim/";
        }
    }

}
