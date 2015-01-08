/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.models.airline;

import lombok.Data;

import java.util.List;

/**
 * Created by Erik on 10/7/14.
 */
@Data
public class Alliance {

    private List<Airline> members;
}
