/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.views.server;

import com.jcabi.aspects.Async;
import com.jcabi.aspects.Timeable;
import com.takeoffsim.demand.RouteDemand;
import com.takeoffsim.main.Config;
import com.takeoffsim.services.xml.CountryLoader;
import com.takeoffsim.services.xml.TAPAirport;
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
    
    private static final WebView VIEW = new WebView();
    
    private static final WebEngine ENGINE = VIEW.getEngine();

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
        ThreadManager.submit(() -> RouteDemand.launch());

        SERVER.start();
        ENGINE.load("http://localhost:40973/landing.html");
        ENGINE.locationProperty().addListener(new StringChangeListener());
        VIEW.setPrefWidth(1920);
        VIEW.setPrefHeight(1200);
        Scene scene = new Scene(VIEW);
        //Don't let them leave the program
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    protected static String url(){
        return ENGINE.getLocation();
    }
    
    public static void load(String url){
        ENGINE.load(url);
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
        int index = ENGINE.getHistory().getCurrentIndex();
        ObservableList<WebHistory.Entry> entries = ENGINE.getHistory().getEntries();
        if(index > 0 && index < entries.size() -1){
            return entries.get(index + 1).getUrl();
        }else
            return index == entries.size() - 1 && entries.size() > 1 ? entries.get(index - 1).getUrl() : "http://localhost:40973/landing.html";
    }

    private static class StringChangeListener implements ChangeListener<String> {
        @Override
        @Timeable(limit = 5, unit = TimeUnit.SECONDS)
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.contains("http://localhost")) {
                Platform.runLater(() -> ENGINE.load(oldValue));
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
    }
}
