/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-Partridge 2014
 */

package com.takeoffsim.models.aircraft;

import com.takeoffsim.models.world.Time;
import lombok.extern.apachecommons.CommonsLog;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

/**
 * @author Erik
 */
@CommonsLog
public class Engine implements Serializable {

    static final long serialVersionUID = 51839414913L;

    private EngineType type;

    private LocalDate made;

    private Engine(EngineType type) {
        this.setType(type);
        setMade(Time.getDateInstance());
    }

    private Engine(){

    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Engine)) return false;

        Engine engine = (Engine) obj;

        if (getMade() != null ? !getMade().equals(engine.getMade()) : engine.getMade() != null) return false;
        return !(getType() != null ? !getType().equals(engine.getType()) : engine.getType() != null);

    }

    @Override
    public int hashCode() {
        int result = getType() != null ? getType().hashCode() : 0;
        result = 31 * result + (getMade() != null ? getMade().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "type=" + getType() +
                ", made=" + getMade() +
                '}';
    }


    /**
    *Engine has properties. Doesn't do much but needs to be there.
    *Fuel burn per lbf
     */
    public EngineType getType() {
        return type;
    }

    void setType(EngineType type) {
        this.type = type;
    }

    ChronoLocalDate getMade() {
        return made;
    }

    void setMade(LocalDate made) {
        this.made = made;
    }
}
