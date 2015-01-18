/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.models.world;

import com.takeoffsim.models.aircraft.Airplane;
import org.jetbrains.annotations.NotNull;

import static java.time.LocalDateTime.of;

/**
 * @author Erik
 */
final class Maintenance {
    private Maintenance() {
    }

    public static void aCheck(@NotNull Airplane a) {
        a.getOwner().pay(a.getType().getMaintenanceProfile().getPriceA());
        a.setaCheck(0.0d);
        a.setUntil(of(Time.currentTime.toLocalDate(), Time.currentTime.toLocalTime()).plusHours(6));
    }

    public static void bCheck(@NotNull Airplane a) {
        a.getOwner().pay(a.getType().getMaintenanceProfile().getPriceB());
        a.setbCheck(0.0d);
        a.setUntil(of(Time.currentTime.toLocalDate(), Time.currentTime.toLocalTime()).plusDays(2));
    }

    public static void cCheck(@NotNull Airplane a) {
        a.getOwner().pay(a.getType().getMaintenanceProfile().getPriceC());
        a.setcCheck(0.0d);
        a.setUntil(of(Time.currentTime.toLocalDate(), Time.currentTime.toLocalTime()).plusDays(55));

    }
}
