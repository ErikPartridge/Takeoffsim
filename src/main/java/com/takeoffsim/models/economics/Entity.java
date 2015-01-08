/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.economics;

import com.takeoffsim.models.world.Country;
import org.joda.money.Money;


public interface Entity {

    public Money getValuation();

    public Country getCountry();

    public Money getEarnings();

    public Money getCosts();

    public Money getCash();

    public String getName();

    public boolean isPrivate();

    public void goPublic();

}
