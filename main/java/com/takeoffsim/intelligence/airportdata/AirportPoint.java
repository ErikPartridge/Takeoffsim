/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.intelligence.airportdata;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.takeoffsim.airport.Airport;
import org.apache.commons.math3.ml.clustering.Clusterable;

import java.util.Arrays;

/**
 * Created by Erik in 11, 2014.
 */
public class AirportPoint implements Clusterable {

    private double[] data;

    private Airport airport;

    @Override
    public double[] getPoint() {
        return getData();
    }

    public AirportPoint(Airport apt, Airport hub){
        this.setAirport(apt);
        setData(new double[4]);
        getData()[0] = 1/ LatLngTool.distance(new LatLng(apt.getLatitude(), apt.getLongitude()), new LatLng(hub.getLatitude(), hub.getLongitude()), LengthUnit.NAUTICAL_MILE);
        getData()[1] = getAirport().getAllocatedDemand();
        getData()[2] = getAirport().getNumFlights();
        getData()[3] = hub.getCountry().equals(apt.getCountry()) ? 1 : .666666666666667d;
    }


    public double[] getData() {
        //noinspection ReturnOfCollectionOrArrayField
        return data;
    }

    void setData(double[] data) {
        //noinspection AssignmentToCollectionOrArrayFieldFromParameter
        this.data = data;
    }

    public Airport getAirport() {
        return airport;
    }

    void setAirport(Airport airport) {
        this.airport = airport;
    }

    @Override
    public String toString() {
        return "AirportPoint{" +
                "data=" + Arrays.toString(data) +
                ", airport=" + airport +
                '}';
    }
}
