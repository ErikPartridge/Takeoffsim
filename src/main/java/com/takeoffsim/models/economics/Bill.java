/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.economics;

import lombok.Data;
import org.joda.money.Money;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author Erik
 */
@Data
public class Bill implements Serializable, Comparable {
    static final long serialVersionUID = -561935192013L;
    private final String description;

    private final Company pays;

    private final Company to;

    private final Money amount;

    private final LocalDateTime time;

    public Bill(Company payer, Company payee, Money amount, LocalDateTime time) {
        this.pays = payer;
        this.to = payee;
        this.amount = amount;
        this.description = " ";
        this.time = time;
        Bills.add(this);
    }


    public Bill(Company payer, Company payee, Money amount, String description, LocalDateTime time) {
        this.pays = payer;
        this.to = payee;
        this.amount = amount;
        this.description = description;
        this.time = time;
        Bills.add(this);
    }


    /**
     * Executes the bill. Charges & pays.
     */
    public void execute() {
        getPays().pay(getAmount());
        getTo().receive(getAmount());
    }


    public LocalDateTime getTime(){
        return this.time;
    }

    @Override
    public int compareTo(Object o) {
        return getTime().compareTo(((Bill) o).getTime());
    }
}
