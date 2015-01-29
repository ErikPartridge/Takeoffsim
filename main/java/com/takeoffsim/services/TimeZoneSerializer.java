/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.util.TimeZone;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
public class TimeZoneSerializer extends Serializer<TimeZone> {

    @Override
    public void write(Kryo kryo, Output output, TimeZone timeZone) {
        output.writeString(timeZone.getID());
    }

    @Override
    public TimeZone read(Kryo kryo, Input input, Class<TimeZone> aClass) {
        return TimeZone.getTimeZone(input.readString());
    }
}

