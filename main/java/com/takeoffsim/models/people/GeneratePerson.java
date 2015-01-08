/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.models.people;

import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.economics.Company;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jfairy.Fairy;
import org.jfairy.producer.person.Person;
import org.jfairy.producer.person.PersonProperties;


/**
 * @author Erik
 */
public final class GeneratePerson {


    @Nullable
    public static FlightAttendant createFlightAttendant(@NotNull Airline a) {
        Fairy fairy = Fairy.create();
        Person person = fairy.person(PersonProperties.minAge(19));
        return new FlightAttendant(0, a.getFlightAttendantPay().getAmountMinorInt(), person.firstName(), person.lastName(), person.age(), a);
    }

    @Nullable
    public static Mechanic createMechanic(@NotNull Airline a) {
        Fairy fairy = Fairy.create();
        Person person = fairy.person(PersonProperties.minAge(19));
        return new Mechanic(a.getMechanicPay().getAmountMinorInt(), person.firstName(), person.lastName(), person.age(), a);
    }

    @Nullable
    public static Pilot createPilot(@NotNull Airline a) {
        Fairy fairy = Fairy.create();
        Person person = fairy.person(PersonProperties.minAge(19));
        return new Pilot(a.getMechanicPay().getAmountMinorInt(), person.firstName(), person.lastName(), person.age(), a);
    }

    @Nullable
    public static Executive createExecutive(@NotNull Company c) {
        Fairy fairy = Fairy.create();
        Person person = fairy.person(PersonProperties.minAge(19));
        return new Executive(100000, person.firstName(), person.lastName(), person.age(), c);
    }

}
