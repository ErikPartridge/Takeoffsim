/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.time.ZoneId;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
public class ZoneIdSerializer extends Serializer<ZoneId> {

    @Override
    public void write(Kryo kryo, Output output, ZoneId zoneId) {
        output.writeString(zoneId.getId());
    }

    @Override
    public ZoneId read(Kryo kryo, Input input, Class<ZoneId> aClass) {
        return ZoneId.of(input.readString());
    }
}
