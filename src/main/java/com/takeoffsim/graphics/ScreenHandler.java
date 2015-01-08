/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.graphics;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import lombok.extern.apachecommons.CommonsLog;

import java.util.HashMap;

/**
 * Created by Erik on 10/7/14.
 */
@CommonsLog
public class ScreenHandler extends StackPane {

    public static StackPane pane = null;


    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
            AnchorPane page = loader.load();
            pane.getChildren().clear();
            pane.getChildren().add(page);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public ScreenHandler(){
        super();
        pane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(pane.getChildren());
                for(Node c : pane.getChildren()){
                    AnchorPane pane = (AnchorPane) c;
                    for(Node n: pane.getChildren()){
                        double ratio = n.getLayoutX() * newValue.doubleValue() / oldValue.doubleValue();
                        n.setLayoutX(ratio);
                        n.setScaleX(ratio);
                    }
                    double ratio = c.getLayoutX() * newValue.doubleValue() / oldValue.doubleValue();
                    c.setLayoutX(ratio);
                    c.setScaleX(ratio);
                }
            }
        });
        pane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                for(Node c : pane.getChildren()){
                    AnchorPane pane = (AnchorPane) c;
                    for(Node n: pane.getChildren()){
                        n.setLayoutY(c.getLayoutX() * newValue.doubleValue() / oldValue.doubleValue());
                        n.setScaleY(newValue.doubleValue() / oldValue.doubleValue());
                    }
                    c.setLayoutY(c.getLayoutX() * newValue.doubleValue() / oldValue.doubleValue());
                    c.setScaleY(newValue.doubleValue() / oldValue.doubleValue());
                }
            }
        });
    }
}
