/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.graphics;

/**
 * Created by Erik in 10, 2014.
 */

import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.services.xml.AirlineLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import lombok.extern.apachecommons.CommonsLog;

import java.io.IOException;
import java.util.ArrayList;
@CommonsLog
public class NewOrOldController {

    @FXML // fx:id="startAirlineButton"
    private Button startAirlineButton; // Value injected by FXMLLoader

    @FXML // fx:id="choseAirline"
    private Button choseAirline; // Value injected by FXMLLoader

    @FXML // fx:id="airlineChooser"
    private ChoiceBox<String> airlineChooser; // Value injected by FXMLLoader

    @FXML
    protected void startAirline(ActionEvent event) {
        AnchorPane page = null;
        try{
            page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/LaunchAirline.fxml"));
            System.out.println(page);
            ScreenHandler.pane.getChildren().add(page);
        }catch (IOException e){
            log.error(e.getMessage());
        }
        event.consume();
    }

    @FXML
    protected void choseAirline(ActionEvent event) {
        String[] split = airlineChooser.getValue().split(" ");
        Airline user = Airlines.getAirline(split[0].trim());
        assert user != null;
        user.setHuman(true);
        Window stage = ((Node) event.getSource()).getScene().getWindow();
        AnchorPane page = null;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/ExistingAirlineSettings.fxml"));
            page = loader.load();
        }catch (IOException e){
            System.out.println(e.getCause() + " " + e.getMessage());
        }
        assert page != null;
        if (page != null) {
            ScreenHandler.pane.getChildren().clear();
            ScreenHandler.pane.getChildren().add(page);
        }
        event.consume();
    }

    public void initialize() {
        ArrayList<String> list = new ArrayList<String>();
        new AirlineLoader().createAirlines();
        System.out.println(Airlines.getMap());
        for (Airline a : Airlines.getMap().values()) {
            String s = a.getIcao() + " - " + a.getName();
            list.add(s);
        }
        airlineChooser.getItems().setAll(list);
    }

}
