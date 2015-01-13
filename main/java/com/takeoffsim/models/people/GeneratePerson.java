/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
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

    private static final Fairy fairy = Fairy.create();

    @Nullable
    public static FlightAttendant createFlightAttendant(@NotNull Airline a) {
        Person person = fairy.person(PersonProperties.minAge(19));
        return new FlightAttendant(0, a.getFlightAttendantPay().getAmountMinorInt(), person.firstName(), person.lastName(), person.age(), a);
    }

    @Nullable
    public static Mechanic createMechanic(@NotNull Airline a) {
        Person person = fairy.person(PersonProperties.minAge(19));
        return new Mechanic(a.getMechanicPay().getAmountMinorInt(), person.firstName(), person.lastName(), person.age(), a);
    }

    @Nullable
    public static Pilot createPilot(@NotNull Airline a) {
        Person person = fairy.person(PersonProperties.minAge(19));
        return new Pilot(a.getMechanicPay().getAmountMinorInt(), person.firstName(), person.lastName(), person.age(), a);
    }

    @Nullable
    public static Executive createExecutive(@NotNull Company c) {
        Person person = fairy.person(PersonProperties.minAge(19));
        return new Executive(100000, person.firstName(), person.lastName(), person.age(), c);
    }

    public static Investor createInvestor(){
        Person person = fairy.person();
        return new Investor(person.fullName());
    }

}
