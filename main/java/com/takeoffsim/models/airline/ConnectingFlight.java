/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline;

import com.takeoffsim.models.world.TimeUtils;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;


/**
 * @author Erik
 */
public
@Data
class ConnectingFlight implements Serializable {
    //A connecting flight


    @NotNull
    private Flight[] flights = new Flight[2];

    private double price;

    private int time;

    /**
     * @param one the first flight
     * @param two the second flight
     */
    public ConnectingFlight(@NotNull Flight one, @NotNull Flight two) {
        this.price = one.getRoute().getEcoPrice() * one.getAirline().getConnectionRate() + two.getRoute().getEcoPrice() * two.getAirline().getConnectionRate();
        this.time = TimeUtils.layover(one, two);
        flights[0] = one;
        flights[1] = two;
    }


    @NotNull
    @Override
    public String toString() {
        return "ConnectingFlight{" +
                "flights=" + getFlights() +
                ", price=" + getPrice() +
                ", time=" + getTime() +
                '}';
    }

    /**
     * @return the length of the layover in minutes
     */
    public int getLayover() {
        return TimeUtils.layover(flights[0], flights[1]);
    }
}
