/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.economics;

import com.rits.cloning.Cloner;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.joda.money.Money;

import java.time.LocalDate;
import java.util.Collection;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
@CommonsLog @Data
public class FinancialRecord implements Comparable<FinancialRecord> {

    private final Company company;

    private final LocalDate date;

    private final Money cash;

    private final Money earnings;

    private final Money costs;

    private final Collection<Stock> shares;

    private final double earningsPerShare;

    private final double dividend;

    public FinancialRecord(Company company, LocalDate date, Collection<Stock> shares, Money cash, Money earnings, Money costs, double earningsPerShare, double dividend){
        this.company = company;
        this.date = date;
        this.cash = cash;
        this.earnings = earnings;
        this.costs = costs;
        this.shares = new Cloner().shallowClone(shares);
        this.earningsPerShare = earningsPerShare;
        this.dividend = dividend;
    }

    @Override
    public int compareTo(FinancialRecord o) {
        return o.getDate().compareTo(date);
    }
}
