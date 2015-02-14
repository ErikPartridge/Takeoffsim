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

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
@CommonsLog
public class AircraftTypeLoader {

    public AircraftTypeLoader(){
        super();
    }

    public void makeAircraftTypes(){
        URL url = getClass().getClassLoader().getResource("aircraft_types/");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            log.error(e);
        }
        assert file != null;
        System.out.println(file);
        for(File f : file.listFiles()){
            System.out.println(f.getPath());
            try {
                makeAircraftTypeFromStream(new FileInputStream(f));
            } catch (FileNotFoundException e) {
                log.error(e);
            }
        }
    }

    void makeAircraftTypeFromStream(InputStream is){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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
        builder.setNumberOfEngines(Integer.parseInt(root.getChild("Engines").getAttributeValue("number")));
        builder.setFuelBurn(Integer.parseInt(root.getChildTextTrim("Fuel")));
        builder.setMlw(Integer.parseInt(root.getChildTextTrim("MLW")));
        builder.setOew(Integer.parseInt(root.getChildTextTrim("OEW")));
        builder.setMtow(Integer.parseInt(root.getChildTextTrim("MTOW")));
        builder.setMzfw(Integer.parseInt(root.getChildTextTrim("MZFW")));
        builder.setRange(Integer.parseInt(root.getChildTextTrim("Range")));
        builder.setCargoCapacity(Integer.parseInt(root.getChildTextTrim("CargoCapacity")));
        builder.setTurntime(Integer.parseInt(root.getChildTextTrim("TurnTime")));
        builder.setIcao(root.getChildTextTrim("Icao"));
        builder.setMaintenanceProfile(new AircraftTypeMaintenance());
        builder.setEntry(LocalDate.parse(root.getChildTextTrim("IntroToService"), formatter));
        AircraftType type = builder.createAircraftType();
        type.setManufacturer(AircraftManufacturers.get(root.getChildTextTrim("Manufacturer")));
        type.setPrice(Money.parse(root.getChildTextTrim("ListPrice")));
        type.setWingletsAvailable(Boolean.parseBoolean(root.getChildTextTrim("Winglets")));
        type.setMaxEconomySeats(Integer.parseInt(root.getChildTextTrim("MaxPaxCapacity")));
        if(root.getChildTextTrim("Exit").equals("null")){
            type.setStop(null);
        }else{
            type.setStop(LocalDate.parse(root.getChildTextTrim("Exit"),formatter));
        }
        type.setProductionRate(Integer.parseInt(root.getChildTextTrim("Production")));
        AircraftTypes.put(type);
        System.out.println();
    }
}
