/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-Partridge 2014
 */

package com.takeoffsim.models.aircraft;

import com.takeoffsim.models.world.Time;
import lombok.extern.apachecommons.CommonsLog;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Erik
 */
@CommonsLog
public class Engine implements Serializable {

    static final long serialVersionUID = 51839414913L;

    private EngineType type;

    private LocalDate made;

    public Engine(EngineType type) {
        this.setType(type);
        setMade(Time.getDateInstance());
    }

    public Engine(){

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Engine)) return false;

        Engine engine = (Engine) o;

        if (getMade() != null ? !getMade().equals(engine.getMade()) : engine.getMade() != null) return false;
        if (getType() != null ? !getType().equals(engine.getType()) : engine.getType() != null) return false;

        return true;
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

    public void setType(EngineType type) {
        this.type = type;
    }

    public LocalDate getMade() {
        return made;
    }

    public void setMade(LocalDate made) {
        this.made = made;
    }
}
