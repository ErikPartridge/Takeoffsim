/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.graphics;

/**
 * Created by Erik on 10/5/14.
 */

import com.takeoffsim.models.aircraft.Airplane;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.models.airline.Subfleet;
import com.takeoffsim.airport.Airport;
import com.takeoffsim.services.xml.GeneralLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lombok.extern.apachecommons.CommonsLog;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.IOException;
import java.util.ArrayList;

@CommonsLog
public class ExistingAirlineController {

    @FXML
    private Button startButton;

    @FXML
    private Text stockPrice;

    @FXML
    private Text hubs;

    @FXML
    private Text shares;

    @FXML
    private Text headquarters;

    @FXML
    private Button cancelButton;

    @FXML
    private Text valuation;

    @FXML
    private Text fleetSize;

    @FXML
    private TextField simulationName;

    @FXML
    private ImageView logo;

    @FXML
    private Text isSubsidiary;

    @FXML
    private Text funds;

    @FXML
    private Text subsidiaries;

    @FXML
    private TextField ceoName;

    @FXML
    private Text publiclyHeld;

    @FXML
    private Text airlineName;

    @FXML
    public void loadAction(ActionEvent event) {
        if (simulationName.getText().equals("")) {
            event.consume();
            return;
        }
        GeneralLoader.loadAllButAirlines(simulationName.getText());

        AnchorPane page = null;
        try{
            FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SimulationLanding.fxml"));
        }catch (IOException e){
            log.error(e.getMessage());
        }
    }

    @FXML
    protected void cancel(ActionEvent event) {
        AnchorPane page = null;
        try{
            FXMLLoader.load(getClass().getClassLoader().getResource("fxml/NewOrOld.fxml"));
        }catch (IOException e){
            log.error(e.getMessage());
        }
        event.consume();
    }

    @FXML
    public void initialize() {
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'ExistingAirlineSettings.fxml'.";
        assert stockPrice != null : "fx:id=\"stockPrice\" was not injected: check your FXML file 'ExistingAirlineSettings.fxml'.";
        assert hubs != null : "fx:id=\"hubs\" was not injected: check your FXML file 'ExistingAirlineSettings.fxml'.";
        assert shares != null : "fx:id=\"shares\" was not injected: check your FXML file 'ExistingAirlineSettings.fxml'.";
        assert headquarters != null : "fx:id=\"headquarters\" was not injected: check your FXML file 'ExistingAirlineSettings.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'ExistingAirlineSettings.fxml'.";
        assert valuation != null : "fx:id=\"valuation\" was not injected: check your FXML file 'ExistingAirlineSettings.fxml'.";
        assert fleetSize != null : "fx:id=\"fleetSize\" was not injected: check your FXML file 'ExistingAirlineSettings.fxml'.";
        assert isSubsidiary != null : "fx:id=\"isSubsidiary\" was not injected: check your FXML file 'ExistingAirlineSettings.fxml'.";
        assert funds != null : "fx:id=\"funds\" was not injected: check your FXML file 'ExistingAirlineSettings.fxml'.";
        assert subsidiaries != null : "fx:id=\"subsidiaries\" was not injected: check your FXML file 'ExistingAirlineSettings.fxml'.";
        assert ceoName != null : "fx:id=\"ceoName\" was not injected: check your FXML file 'ExistingAirlineSettings.fxml'.";
        assert publiclyHeld != null : "fx:id=\"publiclyHeld\" was not injected: check your FXML file 'ExistingAirlineSettings.fxml'.";
        assert airlineName != null : "fx:id=\"airlineName\" was not injected: check your FXML file 'ExistingAirlineSettings.fxml'.";
        Airline airline = null;
        for (Airline a : Airlines.getMap().values()) {
            if (a.isHuman()) {
                airline = a;
            }
        }
        if (airline == null) {
            AnchorPane page = null;
            try{
                FXMLLoader.load(getClass().getClassLoader().getResource("fxml/NewOrOld.fxml"));
            }catch (IOException e){
                log.error(e.getMessage());
            }

        } else {
            //Load the logo
            try {
                Image image = new Image(getClass().getClassLoader().getResourceAsStream("logos/" + "DAL" + ".jpg"), 140, 140, false, true);
                logo.imageProperty().setValue(image);
            } catch (Exception e) {
                log.error("Unable to read logo for airline " + airline.getIcao());
            }

            airlineName.setText(airline.getName());
            //Properly formatted stockprice
            Money money = Money.of(CurrencyUnit.USD, 98000000);
            stockPrice.setText(money.toString());
            //list the hubs
            String string = "";
            for (Airport airport : airline.getHubs()) {
                string += airline.getIcao() + " ";
            }
            hubs.setText(string.trim());
            shares.setText(airline.getNumShares() + " shares");
            headquarters.setText(airline.getHeadquarters());
            valuation.setText(airline.getValuation().toString());
            int fleet = 0;
            if(airline.getFleet() != null) {
                for (Subfleet sf : airline.getFleet().getFleet()) {
                    for (Airplane airplane : sf.getAircraft().values()) {
                        fleet++;
                    }
                }
            }
            fleetSize.setText(fleet + " planes");
            isSubsidiary.setText(airline.isSubsidiary() ? "Yes" : "No");
            funds.setText(airline.getCash().toString());
            publiclyHeld.setText(airline.isPrivate() ? "No" : "Yes");
            //
            ArrayList<Airline> airlines = airline.getSubsidiaries();
            String subs = "";
            if(airlines != null) {
                for (Airline a : airlines) {
                    subs += a.getIcao() + " ";
                }
            }
            subsidiaries.setText(subs.trim());
        }
    }

    @FXML
    public void cancel(){
        throw new UnsupportedOperationException();
    }

}
