/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.server;

import com.jcabi.aspects.Async;
import com.jcabi.aspects.Timeable;
import com.takeoffsim.models.aircraft.AircraftTypes;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.models.airline.GlobalRoutes;
import com.takeoffsim.models.economics.Companies;
import com.takeoffsim.models.world.Cities;
import com.takeoffsim.models.world.Countries;
import com.takeoffsim.models.world.Regions;
import com.takeoffsim.services.Config;
import com.takeoffsim.services.demand.RouteDemand;
import com.takeoffsim.services.xml.CountryLoader;
import com.takeoffsim.services.xml.TAPAirport;
import com.takeoffsim.threads.SerializeThread;
import com.takeoffsim.threads.ThreadManager;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lombok.extern.apachecommons.CommonsLog;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

@CommonsLog
public class Main extends Application {

    private static WebView view;
    
    private static WebEngine engine;

    public static final Server SERVER = new Server();

    @Override
    public void start(Stage primaryStage) throws IOException {
        new CountryLoader().createCountries();
        new TAPAirport().createAirports();
        log.trace(Config.themePath());
        diagnostics();
        if(!Platform.isSupported(ConditionalFeature.WEB)){
            log.fatal("Web support is not enabled in this version of JavaFX");
            System.exit(-1);
        }
        //Thread serialization = new SerializeThread();
       // serialization.setDaemon(true);
        //serialization.setName("Serialization thread");
        //serialization.start();
        ThreadManager.submit(RouteDemand::launch);
        view = new WebView();
        engine = view.getEngine();
        SERVER.start();
        engine.load("http://localhost:40973/index.html");
        engine.locationProperty().addListener(new StringChangeListener());
        view.setPrefWidth(1920);
        view.setPrefHeight(1200);
        Scene scene = new Scene(view);
        primaryStage.setOnCloseRequest(event -> {
            log.fatal("Closing per user request");
            event.consume();
            if(Config.nameOfSim != null){
                primaryStage.hide();
                exit();
            }
            System.exit(2);
        });
        //Don't let them leave the program
        primaryStage.setScene(scene);
        primaryStage.show();
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
    protected static String url(){
        return engine.getLocation();
    }
    
    public static void load(String url){
        engine.load(url);
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
    
    public static String back(){
        int index = engine.getHistory().getCurrentIndex();
        ObservableList<WebHistory.Entry> entries = engine.getHistory().getEntries();
        if(index > 0 && index < entries.size() -1){
            return entries.get(index + 1).getUrl();
        }else
            return index == entries.size() - 1 && entries.size() > 1 ? entries.get(index - 1).getUrl() : "http://localhost:40973/index.html";
    }

    public static void goBack(){
        load(back());
    }

    private static class StringChangeListener implements ChangeListener<String> {
        @Override
        @Timeable(limit = 5, unit = TimeUnit.SECONDS)
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.contains("http://localhost")) { Platform.runLater(() -> engine.load(oldValue)); }
        }
    }
}
