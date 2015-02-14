/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.server;

import com.jcabi.aspects.Async;
import com.takeoffsim.models.aircraft.AircraftTypes;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.models.airline.GlobalRoutes;
import com.takeoffsim.models.economics.Companies;
import com.takeoffsim.models.world.Cities;
import com.takeoffsim.models.world.Countries;
import com.takeoffsim.models.world.Regions;
import com.takeoffsim.services.Config;
import com.takeoffsim.services.demand.DemandCreator;
import com.takeoffsim.services.threads.SerializeThread;
import com.takeoffsim.services.xml.CityLoader;
import com.takeoffsim.services.xml.CountryLoader;
import com.takeoffsim.services.xml.RegionLoader;
import com.takeoffsim.services.xml.TAPAirport;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import lombok.extern.apachecommons.CommonsLog;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ForkJoinPool;

@CommonsLog
public class Main {

    public static Server SERVER = new Server();

    public void start() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new CountryLoader().createCountries();
        new TAPAirport().createAirports();
        new RegionLoader().createRegions();
        new CityLoader().loadCities();
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.submit(() -> new DemandCreator().createAllDemand());
        log.trace(Config.themePath());
        pool.submit(this::diagnostics);
        try {
            SERVER.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void clearAll(){
        Airlines.clear();
        AircraftTypes.clear();
        Countries.clear();
        Airlines.clear();
        GlobalRoutes.clear();
        Cities.clear();
        Regions.clear();
        Companies.clear();
    }

    private static void exit(){
        SerializeThread.waitToFinish();
        System.exit(1);
    }


    @Async
    void diagnostics(){
        log.info(System.getProperties());
        log.info("Desktop supported? " + Desktop.isDesktopSupported());
        log.info("Web enabled? " + Platform.isSupported(ConditionalFeature.WEB));
        try {
            log.info("Is connected to the web? " + InetAddress.getByName("takeoffsim.com").isReachable(1000));
        } catch (UnknownHostException e) {
            log.info(e);
        } catch(IOException ignored){
            log.info("Is connected to the web? false");
        }
    }

}
