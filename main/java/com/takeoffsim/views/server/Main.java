/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.views.server;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Server server = new Server();
        server.start();
        WebView view = new WebView();
        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
