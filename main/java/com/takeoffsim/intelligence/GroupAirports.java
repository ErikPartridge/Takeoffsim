/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.intelligence;

import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Route;
import com.takeoffsim.airport.Airport;
import com.takeoffsim.airport.Airports;
import com.takeoffsim.intelligence.airportdata.AirportPoint;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.apache.commons.math3.random.MersenneTwister;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Erik in 09, 2014.
 */
public final class GroupAirports {


    private Airline airline;


    /**
     *
     * @param hub the airport to calculate the distance from
     * @return a cluster of airports
     */
    public ArrayList<Airport> bestOptions(Airport hub){

        List<CentroidCluster<AirportPoint>> points = getClusters(hub);
        List<Integer> ints = new ArrayList<>();
        assert ints.size() == points.size();
        //Get the last point and add them to results.
        points.forEach(pt -> ints.add(matches(pt.getPoints(), hub)));
        int selection = new MersenneTwister().nextInt(getScore(ints, ints.size()-1));
        for(int i = 0; i < ints.size(); i++){
            if(selection < getScore(ints, i)){
                return getList(points.get(i));
            }
        }
        return getList(points.get(points.size()-1));
    }

    public ArrayList<Airport> getList(CentroidCluster<AirportPoint> point){
        ArrayList<Airport> airports = new ArrayList<>();
        point.getPoints().forEach(pt -> airports.add(pt.getAirport()));
        return airports;
    }
    public int getScore(List<Integer> ints, int index){
        int sum = 0;
        for(int i = 0; i <= index; i++){
            sum += ints.get(i);
        }
        return sum;
    }

    private int matches(List<AirportPoint> list, Airport departs){
        int sum = 0;
        Stream<Route> routes = airline.getRoutes().stream().filter(new Predicate<Route>() {
            @Override
            public boolean test(Route route) {
                return route.getDeparts().equals(departs);
            }
        });

        List<Airport> apts = new ArrayList<>();
        list.forEach(ap -> apts.add(ap.getAirport()));
        for(Airport apt : apts){
            boolean match = false;
            match = !routes.noneMatch(new Predicate<Route>() {
                @Override
                public boolean test(Route route) {
                    return route.getArrives().equals(apt);
                }
            });
            if(match){
                sum++;
            }
        }

        return sum;
    }

    /**
     *
     * @param hub the airport to calculate the distances from
     * @return a sorted list of clusters
     */
    public List<CentroidCluster<AirportPoint>> getClusters(Airport hub){
        //Build a KMeansClusterer with a custom distance formula
        KMeansPlusPlusClusterer<AirportPoint> clusterer = new KMeansPlusPlusClusterer<>(2000, 500, new DistanceMeasure() {
            @Override
            public double compute(double[] a, double[] b) {
                double data0 = a[0] - b[0];
                double data1 = a[1] - b[1];
                double data2 = a[2] - b[2];
                double data3 = a[3] - b[3];
                return Math.pow(data0 * data1 * data2 * data3, .75);
            }
        }, new MersenneTwister(), KMeansPlusPlusClusterer.EmptyClusterStrategy.LARGEST_VARIANCE);

        //give me a sortable list of AirpointPoints
        ArrayList<AirportPoint> list = new ArrayList<>();
        List<Airport> apts = Airports.cloneAirports();
        apts.remove(hub);
        for(Airport apt : apts){
            list.add(new AirportPoint(apt, hub));
        }
        //Cluster the Points and put them in a list
        List<CentroidCluster<AirportPoint>> points = clusterer.cluster(list);

        //Sort the list from smallest distance to largest.
        points.sort(new Comparator<CentroidCluster<AirportPoint>>() {
            @Override
            public int compare(CentroidCluster<AirportPoint> o1, CentroidCluster<AirportPoint> o2) {
                double score1 = getScore(o1.getPoints().get(new SecureRandom().nextInt(o1.getPoints().size())).getData());
                double score2 =getScore(o2.getPoints().get(new SecureRandom().nextInt(o2.getPoints().size())).getData());
                if(score1 - score2 > .1){
                    return 1;
                }else if(score1 - score2 < -.1){
                    return -1;
                }else{
                    return 0;
                }
            }
        });

        return points;
    }

    /**
     *
     * @param a the array to calculate the score of
     * @return the product of the first four elements of the array
     */
    private double getScore(double[] a){
        return a[0] * a[1] * a[2] * a[3];
    }

}