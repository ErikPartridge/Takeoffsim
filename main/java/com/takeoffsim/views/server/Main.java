/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.views.server;

import com.jcabi.aspects.Async;
import com.jcabi.aspects.Timeable;
import com.takeoffsim.main.Config;
import com.takeoffsim.services.xml.CountryLoader;
import com.takeoffsim.services.xml.TAPAirport;
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
import java.util.concurrent.TimeUnit;

@CommonsLog
public class Main extends Application {
    
    private static WebView view;
    
    private static WebEngine engine;

    public static Server server;

    @Override
    public void start(Stage primaryStage) throws Exception {
        new CountryLoader().createCountries();
        new TAPAirport().createAirports();
        log.trace(Config.themePath());
        diagnostics();
        if(!Platform.isSupported(ConditionalFeature.WEB)){
            log.fatal("Web support is not enabled in this version of JavaFX");
            System.exit(-1);
        }
        
        server = new Server();
        server.start();
        view = new WebView();
        engine = view.getEngine();
        engine.load("http://localhost:40973/landing.html");
        engine.locationProperty().addListener(new ChangeListener<String>() {
            @Override
            @Timeable(limit = 5, unit = TimeUnit.SECONDS)
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.contains("http://localhost")) {
                    Platform.runLater(() -> engine.load(oldValue));
                    /*
                    if(Desktop.isDesktopSupported()){
                        try{
                            Desktop.getDesktop().browse(new URI("http://takeoffsim.com"));
                        }catch (IOException | URISyntaxException e){
                            log.debug(e);
                        }
                    }*/
                }
            }
        });
        view.setPrefWidth(1920);
        view.setPrefHeight(1200);
        Scene scene = new Scene(view);
        //Don't let them leave the program
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    protected static String url(){
        return engine.getLocation();
    }
    
    public static void load(String url){
        engine.load(url);
    }

    @Async
    public void diagnostics(){
        log.info(System.getProperties());
        log.info("Desktop supported? " + Desktop.isDesktopSupported());
        log.info("Web enabled? " + Platform.isSupported(ConditionalFeature.WEB));
        try {
            log.info("Is connected to the web? " + InetAddress.getByName("takeoffsim.com").isReachable(1000));
        }catch(IOException e){
            log.info("Is connected to the web? false");
        }
    }
    
    public static String back(){
        int index = engine.getHistory().getCurrentIndex();
        ObservableList<WebHistory.Entry> entries = engine.getHistory().getEntries();
        if(index > 0 && index < entries.size() -1){
            return entries.get(index + 1).getUrl();
        }else if(index == entries.size() - 1 && entries.size() > 1){
            return entries.get(index - 1).getUrl();
        }else{
            return "http://localhost:40973/landing.html";
        }
    }
    
}
