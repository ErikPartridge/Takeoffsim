/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
public class MoneySerializer extends Serializer<Money> {


    public MoneySerializer(){
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public Money read(final Kryo kryo, final Input input, final Class<Money> type){
        try {
            String in = input.readString();
            String[] split= in.split(" ");
            double d = Double.parseDouble(split[1]);
            return Money.of(CurrencyUnit.of(split[0]), d);
        } catch (final Exception e) {
            throw new RuntimeException( e );
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void write(final Kryo kryo, final Output output, final Money obj){
        output.writeString(obj.getCurrencyUnit().getCurrencyCode() + " " + obj.getAmount().doubleValue());
    }
}
