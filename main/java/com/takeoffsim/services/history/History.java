/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services.history;

import com.rits.cloning.Cloner;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.world.Time;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.SerializationException;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Erik in 11, 2014.
 */
@CommonsLog
@Data
public class History<T> implements Serializable, Comparable{

    public static final String path = ".takeoffsim/archives";

    private final T object;

    private final LocalDateTime timeStamp;

    public History(T item){
        final Cloner cloner = new Cloner();
        this.object = cloner.shallowClone(item);
        if(!(item instanceof Serializable)){
            throw new SerializationException(item.getClass().toString() + " is not serializable");
        }
        timeStamp = Time.getDateTimeInstance();
    }

    public History(T item, LocalDateTime time){
        final Cloner cloner = new Cloner();
        this.object = cloner.shallowClone(item);
        if(!(item instanceof Serializable)){
            throw new SerializationException(item.getClass().toString() + " is not serializable");
        }
        timeStamp = time;

    }

    public History(T item, LocalDateTime time, boolean test){
        this.object = item;
        this.timeStamp = time;

    }


    public History<Airline> getClosest(List<History<Airline>> histories, LocalDateTime time){
        assert histories != null;
        assert histories.size() > 0;
        History<Airline> ideal = new History<>(histories.get(0).getObject(), time);
        Comparator<History<Airline>> comparator = (o1, o2) -> o1.getTimeStamp().compareTo(o2.getTimeStamp());
        histories.sort(comparator);
        return histories.get(Collections.binarySearch(histories, ideal, comparator));
    }

    @Override
    public int compareTo(@NotNull Object o) {
        assert o instanceof History;
        return this.getTimeStamp().compareTo(((History) o).getTimeStamp());
    }
}
