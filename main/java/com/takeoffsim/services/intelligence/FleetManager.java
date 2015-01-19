/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.services.intelligence;

import com.takeoffsim.models.aircraft.AircraftType;
import com.takeoffsim.models.aircraft.AircraftTypes;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Route;
import com.takeoffsim.models.airline.Subfleet;
import lombok.extern.apachecommons.CommonsLog;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.training.pnn.TrainBasicPNN;
import org.encog.neural.pnn.BasicPNN;
import org.encog.neural.pnn.PNNKernelType;
import org.encog.neural.pnn.PNNOutputMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * @author Erik
 */
@CommonsLog
class FleetManager {
    private final Airline airline;

    private FleetManager(Airline a) {
        this.airline = a;
    }

    public int expand() {
        log.trace("analyzing expand" + airline.getName());
        double funds = airline.getCash().getAmount().doubleValue();
        int fleet = airline.getFleet().getFleet().size();
        if (fleet == 0) {
            return 1;
        }
        if (fleet < 6 && funds > 50000000) {
            return 1;
        }

        if ((funds / fleet >= 7000000) && getOnOrder() < (airline.getFleet().getFleet().size() / 3) && airline.getCash().getAmountMajorInt() > 0) {
            return 2;
        }

        return 0;
    }

    @Nullable
    /**
     * @returns The aircraft type that the neural network recommends
     */
    public AircraftType type() {
        BasicPNN network = DataSets.getNN(airline.getIcao() + "+expand_fleet");
        if (network == null || network.getOutputCount() != AircraftTypes.getMap().size()) {
            network = new BasicPNN(PNNKernelType.Gaussian, PNNOutputMode.Classification, 10 + AircraftTypes.getMap().values().size(), AircraftTypes.getMap().values().size());
            DataSets.getNetworks().remove(airline.getIcao() + "+expand_fleet");
            DataSets.getNetworks().put(airline.getIcao() + "+expand_fleet", network);
            log.info("Had to create a new neural network for expanding fleet for airline: " + airline.getIcao());
        }
        MLDataSet data = DataSets.getDataSet(airline.getIcao() + "_expand_fleet");
        if (data == null)
            data = new BasicMLDataSet();
        MLTrain trainer = new TrainBasicPNN(network, data);
        for (int i = 0; i < 10; i++) {
            trainer.iteration();
        }

        int index = network.classify(new BasicMLData(getData()));
        return (AircraftType) AircraftTypes.getMap().values().toArray()[index];
    }

    double[] getData() {
        AircraftType[] types = (AircraftType[]) AircraftTypes.getMap().values().toArray();
        double[] array = new double[25];
        array[0] = airline.getCash().getAmountMajorInt();
        array[1] = airline.getAggressiveness();
        array[2] = airline.getHubPercent();
        array[3] = airline.getHubs().size();
        array[4] = airline.getEarnings().getAmountMajorInt();
        array[5] = airline.getFleet().getFleet().size();
        array[6] = airline.getRoutes().size();
        double totalDistance = 0;
        int totalRoutes = airline.getRoutes().size();
        for (Route r : airline.getRoutes()) {
            totalDistance += r.getDistance();
        }
        array[7] = totalDistance / totalRoutes;
        array[8] = 0.0;
        array[9] = 0.0;
        for (int i = 0; i < types.length; i++) {
            //noinspection ConstantConditions
            array[10 + i] = airline.getFleet().getSubFleet(types[i].getIcao()) == null ? 0.0 : airline.getFleet().getSubFleet(types[i].getIcao()).getAircraft().values().size();
        }
        log.trace("Made data :" + Arrays.toString(array));
        return array;
    }

    public int getNumber(AircraftType type) {
        double available = airline.getCash().getAmount().doubleValue() * .2;
        int num = (int) (type.getPrice().getAmountMajorInt() / available);
        num -= getOnOrder();
        if (num < 0) {
            num = 0;
        }
        return num;
    }

    int getOnOrder() {
        int sum = 0;
        for (Subfleet s : airline.getFleet().getFleet()) {
            sum += s.getOrders().size();
        }
        return sum;
    }

    public int downsizeAmount(){
        throw new UnsupportedOperationException("Failed");
    }
    @NotNull
    @Override
    public String toString() {
        return "ExpandFleet{" +
                "airline=" + airline +
                '}';
    }
}
