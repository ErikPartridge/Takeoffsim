/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.graphics;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Created by Erik in 10, 2014.
 */
public class LandingController {

    @FXML // fx:id="loadButton"
    private Button loadButton; // Value injected by FXMLLoader

    @FXML // fx:id="createButton"
    private Button createButton; // Value injected by FXMLLoader

    @FXML
    private Text text;

    @FXML
    protected void createNewGame(ActionEvent event) throws IOException{
        AnchorPane page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/NewOrOld.fxml"));
        ScreenHandler.pane.getChildren().clear();
        ScreenHandler.pane.getChildren().add(page);
        event.consume();
    }

    protected void bind(){
        text.layoutXProperty().bind(new ObjectBinding<Number>() {
            @Override
            protected Number computeValue() {
                return new Double(.21d * ScreenHandler.pane.widthProperty().get());
            }
        });
        text.layoutYProperty().bind(new ObjectBinding<Number>() {
            @Override
            protected Number computeValue() {
                return null;
            }
        });
    }

    @FXML
    protected void listAvailable(ActionEvent event) throws IOException{
        AnchorPane page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/LoadSaved.fxml"));
        ScreenHandler.pane.getChildren().clear();
        ScreenHandler.pane.getChildren().add(page);
        event.consume();
        event.consume();
    }

}

