/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.apachecommons.CommonsLog;

import java.io.IOException;

/**
 * Created by Erik in 10, 2014.
 */
@CommonsLog
public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        StackPane pane = new StackPane();
        ScreenHandler.pane = pane;
        AnchorPane page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/Landing.fxml"));
        pane.getChildren().add(page);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("TakeoffSim");
        stage.show();

    }


}
