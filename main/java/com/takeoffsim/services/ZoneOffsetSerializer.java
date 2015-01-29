/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.time.ZoneOffset;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
public class ZoneOffsetSerializer extends Serializer<ZoneOffset> {

    @Override
    public void write(Kryo kryo, Output output, ZoneOffset zoneOffset) {
        output.writeString(zoneOffset.getId());
    }

    @Override
    public ZoneOffset read(Kryo kryo, Input input, Class<ZoneOffset> aClass) {
        return ZoneOffset.of(input.readString());
    }
}
