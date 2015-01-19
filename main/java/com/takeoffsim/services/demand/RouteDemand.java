/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.services.demand;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.takeoffsim.models.airport.Airport;
import lombok.Cleanup;
import lombok.extern.apachecommons.CommonsLog;
import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.ForecastingModel;
import net.sourceforge.openforecast.Observation;
import net.sourceforge.openforecast.input.CSVBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;

import static java.util.logging.Logger.getLogger;
import static net.sourceforge.openforecast.EvaluationCriteria.MAD;
import static net.sourceforge.openforecast.Forecaster.getBestForecast;

/**
 * @author Erik
 */
@CommonsLog
public final class RouteDemand implements Serializable {


    @NotNull
    private static final ForecastingModel model = getModel();


    private RouteDemand() {
        //model = getModel();
    }

    public static int launch(){
        return model.hashCode();
    }


    /**
     *
     * @return the forecasting model
     */
    private static ForecastingModel getModel() {
        //Get an input stream
        Scanner scanner = new Scanner(RouteDemand.class.getClassLoader().getResourceAsStream("forecaster_training/training.csv"));
        File file = null;
        //Need a temporary file for CSV constructor
        try {
            file = File.createTempFile("training", ".tmp");
        } catch (IOException e) {
            log.debug(e);
        }
        assert file != null;
        //OutputStream, so I can read in stream and write to temporary file (crazy, I know)
        @Cleanup PrintWriter out = null;
        try {
            out = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            log.debug(e);
        }
        assert out != null;
        //Write to the temporary file
        while (scanner.hasNextLine()) {
            out.write(scanner.nextLine());
        }
        //Build the CSVBuilder
        CSVBuilder csv = null;
        try {
            csv = new CSVBuilder(file);
        } catch (FileNotFoundException ex) {
            getLogger(RouteDemand.class.getName()).log(Level.SEVERE, null, ex);
        }
        assert csv != null;
        //Create a dataset
        DataSet ds = null;
        //Build said dataset
        try {
            ds = csv.build();
        } catch (IOException ex) {
            getLogger(RouteDemand.class.getName()).log(Level.SEVERE, null, ex);
        }
        assert ds != null;
        //Build the forecasting model
        ForecastingModel fm = getBestForecast(ds, MAD);
        fm.init(ds);
        file.delete();
        return fm;
    }

    /**
     *
     * @param aptOne the departure airport
     * @param aptTwo the arrival airport
     * @return the demand as a double
     */
    public static synchronized double demand(@NotNull Airport aptOne, @NotNull Airport aptTwo) {
        DataPoint o = new Observation(0);
        double dist = LatLngTool.distance(new LatLng(aptOne.getLatitude(), aptOne.getLongitude()), new LatLng( aptTwo.getLatitude(),
                aptTwo.getLongitude()), LengthUnit.MILE);
        o.setIndependentValue("x1", aptOne.getAllocatedDemand());
        o.setIndependentValue("x2", aptTwo.getAllocatedDemand());
        o.setIndependentValue("x3", dist);
        return model.forecast(o);
    }

}
