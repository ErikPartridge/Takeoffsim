/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */


package com.takeoffsim.services;

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

    public static void write(String file) {

        try {
            long time = System.nanoTime();
            FileOutputStream out = new FileOutputStream(new File(homeDirectory() + "saves/" + file));
            ObjectOutputStream outputStream = new ObjectOutputStream(out);
            outputStream.writeObject(new World());
            out.close();
            outputStream.close();
            time = System.nanoTime() - time;
            System.out.println("Serialization took:" + time);
        } catch (IOException e) {
            log.fatal(e);
        }
    }

    public static void readAll(){
        read(Config.nameOfSim + ".tss");
    }

    public static void writeAll(){
        write(Config.nameOfSim + ".tss");
    }


    public static void read(String file){
        World world = null;
        try {
            FileInputStream in = new FileInputStream(new File(homeDirectory() + "saves/" + file));
            ObjectInputStream obj = new ObjectInputStream(in);
            world = (World) obj.readObject();
            in.close();
            obj.close();
        }catch (Exception e){
            log.fatal(e);
            log.fatal(e.getCause());
        }
        if(world == null){
            log.fatal("Could not load world");
            System.exit(-100);
        }
        Airlines.clear();
        Airports.clear();
        Countries.clear();
        GlobalRoutes.clear();
        Companies.clear();
        AircraftManufacturers.clear();
        Bills.clear();
        AircraftTypes.clear();
        world.getAirlines().forEach(Airlines::put);
        world.getAirports().forEach(Airports::put);
        world.getRoutes().forEach(GlobalRoutes::put);
        world.getCountries().forEach(Countries::put);
        world.getCompanies().forEach(Companies::put);
        world.getManufacturers().forEach(AircraftManufacturers::put);
        world.getAircraftTypes().forEach(AircraftTypes::put);
        world.getBills().forEach(Bills::add);
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

    private Collection<Airport> airports = Airports.getAirports().values();

    private Collection<Airline> airlines = Airlines.getMap().values();

    private Collection<Company> companies = Companies.getCompanies().values();

    private Collection<AircraftManufacturer> manufacturers = AircraftManufacturers.manufacturers();

    private Collection<Country> countries = Countries.getCountries().values();

    private BlockingQueue<Bill> bills = Bills.bills;

    private Collection<GlobalRoute> routes = GlobalRoutes.globalRoutes.values();

    private Collection<AircraftType> aircraftTypes = AircraftTypes.getMap().values();

    public World(){}
}
