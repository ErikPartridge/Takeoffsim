/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services.xml;

import com.takeoffsim.models.aircraft.AircraftType;
import com.takeoffsim.models.aircraft.AircraftTypeBuilder;
import com.takeoffsim.models.aircraft.AircraftTypeMaintenance;
import com.takeoffsim.models.aircraft.AircraftTypes;
import com.takeoffsim.models.manufacturers.AircraftManufacturers;
import lombok.extern.apachecommons.CommonsLog;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.joda.money.Money;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
@CommonsLog
public class AircraftTypeLoader {

    public AircraftTypeLoader(){
        super();
    }

    public void makeAircraftTypes(){
        ExecutorService pool = Executors.newFixedThreadPool(5);
    }

    void makeAircraftTypeFromStream(InputStream is){
        Document doc = null;
        try{
            doc = new SAXBuilder().build(is);
        }catch(IOException | JDOMException e){
            log.error(e);
        }
        Element root = doc.getRootElement();
        AircraftTypeBuilder builder = new AircraftTypeBuilder();
        builder.setName(root.getChildTextTrim("Name"));
        builder.setCruiseSpeed(Integer.parseInt(root.getChildTextTrim("Speed")));
        builder.setArrivalRunway(Integer.parseInt(root.getChildTextTrim("ArrRunway")));
        builder.setDepartureRunway(Integer.parseInt(root.getChildTextTrim("DeptRunway")));
        builder.setNumberOfEngines(Integer.parseInt(root.getChild("engines").getAttributeValue("number")));
        builder.setFuelBurn(Integer.parseInt(root.getChildTextTrim("fuelBurn")));
        builder.setMlw(Integer.parseInt(root.getChildTextTrim("MLW")));
        builder.setOew(Integer.parseInt(root.getChildTextTrim("OEW")));
        builder.setMtow(Integer.parseInt(root.getChildTextTrim("MTOW")));
        builder.setMzfw(Integer.parseInt(root.getChildTextTrim("MZFW")));
        builder.setRange(Integer.parseInt(root.getChildTextTrim("Range")));
        builder.setCargoCapacity(Integer.parseInt(root.getChildTextTrim("CargoCapacity")));
        builder.setTurntime(Integer.parseInt(root.getChildTextTrim("TurnTime")));
        builder.setIcao(root.getChildTextTrim("Icao"));
        builder.setMaintenanceProfile(new AircraftTypeMaintenance());
        builder.setEntry(LocalDate.parse(root.getChildTextTrim("IntroToService")));
        AircraftType type = builder.createAircraftType();
        type.setManufacturer(AircraftManufacturers.get(root.getChildTextTrim("Manufacturer")));
        type.setPrice(Money.parse(root.getChildTextTrim("price")));
        type.setWingletsAvailable(Boolean.parseBoolean(root.getChildTextTrim("Winglets")));
        type.setMaxEconomySeats(Integer.parseInt(root.getChildTextTrim("seats")));
        if(root.getChildTextTrim("Exit").equals("null")){
            type.setStop(null);
        }else{
            type.setStop(LocalDate.parse(root.getChildTextTrim("Exit")));
        }
        type.setProductionRate(Integer.parseInt(root.getChildTextTrim("rate")));
        AircraftTypes.put(type);
    }
}
