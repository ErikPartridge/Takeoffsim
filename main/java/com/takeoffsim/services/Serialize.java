/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */


package com.takeoffsim.services;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.jcabi.aspects.Cacheable;
import com.jcabi.aspects.RetryOnFailure;
import com.takeoffsim.models.aircraft.AircraftType;
import com.takeoffsim.models.aircraft.AircraftTypes;
import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.airline.Airlines;
import com.takeoffsim.models.airline.GlobalRoute;
import com.takeoffsim.models.airline.GlobalRoutes;
import com.takeoffsim.models.airport.Airport;
import com.takeoffsim.models.airport.Airports;
import com.takeoffsim.models.economics.Bill;
import com.takeoffsim.models.economics.Bills;
import com.takeoffsim.models.economics.Companies;
import com.takeoffsim.models.economics.Company;
import com.takeoffsim.models.manufacturers.AircraftManufacturer;
import com.takeoffsim.models.manufacturers.AircraftManufacturers;
import com.takeoffsim.models.world.Countries;
import com.takeoffsim.models.world.Country;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;

import java.io.*;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("ResultOfMethodCallIgnored")
@CommonsLog
public class Serialize {


    private Serialize() {
    }

    public static void writeAll() {

        try {
            long time = System.nanoTime();
            Kryo kryo = new Kryo();
            FileOutputStream out = new FileOutputStream(new File(homeDirectory() + "saves/" + Config.nameOfSim + ".tss"));
            Output obj = new Output(out);
            kryo.writeObject(obj, new World());
            out.close();
            time = System.nanoTime() - time;
            System.out.println("Serialization took:" + time);
        } catch (IOException e) {
            log.fatal(e);
        }
    }


    /**
     * @param fileOut the fileoutput stream to setup
     * @return an ObjectOutputStream contained in an optional
     */
    @RetryOnFailure
    private static Optional<ObjectOutputStream> setup(FileOutputStream fileOut) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(fileOut);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        //noinspection ConstantConditions
        return Optional.of(out);
    }

    /**
     * @return if this operating system is Windows
     */
    @Cacheable(forever = true)
    private static boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    /**
     * @return if this operating system is Mac
     */
    @Cacheable(forever = true)
    private static boolean isMac() {
        return System.getProperty("os.name").startsWith("Mac");
    }

    /**
     * @return if this operating system isn't Mac or Windows
     */
    @Cacheable(forever = true)
    public static boolean isLinux() {
        return !(isMac() || isWindows());
    }

    /**
     * @return the directory in which all data should be stored
     */
    @Cacheable(forever = true)
    public static String homeDirectory() {
        if (isMac()) {
            return System.getProperty("user.home") + "/Library/" + "TakeoffSim/";
        } else if (isWindows()) {
            return System.getenv("ProgramFiles") + "/TakeoffSim/";
        } else {
            return System.getProperty("user.home") + "/.takeoffsim/";
        }
    }

}

@Data
class World implements Serializable{

    private final Collection<Airport> airports = Airports.getAirports().values();

    private final Collection<Airline> airlines = Airlines.getMap().values();

    private final Collection<Company> companies = Companies.getCompanies().values();

    private final Collection<AircraftManufacturer> manufacturers = AircraftManufacturers.manufacturers();

    private final Collection<Country> countries = Countries.getCountries().values();

    private final BlockingQueue<Bill> bills = Bills.bills;

    private final Collection<GlobalRoute> routes = GlobalRoutes.globalRoutes.values();

    private final Collection<AircraftType> aircraftTypes = AircraftTypes.getMap().values();

}
