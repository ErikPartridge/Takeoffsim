/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.airline;


import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.PriorityQueue;
import java.util.stream.Stream;


final class FlightQueue {


    @NotNull
    private static final PriorityQueue<Flight> toDepart = new PriorityQueue<>();


    @NotNull
    private static final PriorityQueue<Flight> toArrive = new PriorityQueue<>();


    private FlightQueue() {
    }

    @NotNull
    public static PriorityQueue<Flight> getQueue() {
        //noinspection ReturnOfCollectionOrArrayField
        return toDepart;

    }

    @NotNull
    public static PriorityQueue<Flight> inAir() {
        return toArrive;
    }

    /**
     *
     * @return a stream of flights that need to depart, ordered by time
     */
    public static Stream<Flight> asStreamToDepart() {
        return toDepart.stream();
    }


    public static void removeFromDepartQuere(Flight f){toDepart.remove(f);}
    /**
     *
     * @param f puts a flight in the queue to leave
     */
    public static void addToDepartQueue(Flight f) {
        toDepart.add(f);
    }

    /**
     *
     * @param f puts a flight in the queue to arrive
     */
    public static void addToArriveQueue(Flight f) {
        toArrive.add(f);
    }

    /**
     *
     * @param dt execute this every minute, will put planes in the air land them.
     */
    public static void execute(LocalDateTime dt) {
        while (toDepart.peek().getActualDeparture().compareTo(dt) <= 0) {
            Flight f = toDepart.poll();
            f.depart(dt);
        }
        while (toArrive.peek().getArrivesGmt().compareTo(dt) <= 0) {
            Flight f = toArrive.poll();
            f.arrive(dt);
        }
    }

}
