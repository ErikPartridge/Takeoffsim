/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */


package com.takeoffsim.services;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.jcabi.aspects.Cacheable;
import com.jcabi.aspects.RetryOnFailure;
import com.takeoffsim.models.aircraft.AircraftType;
import com.takeoffsim.models.aircraft.AircraftTypes;
import com.takeoffsim.models.airline.*;
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
import de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.joda.money.Money;

import java.io.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@SuppressWarnings("ResultOfMethodCallIgnored")
@CommonsLog
public class Serialize {

    private Serialize() {
    }

    public static void write(String file) {
        Kryo kryo = new Kryo();
        kryo.setReferences(true);
        kryo.register(Money.class, new MoneySerializer());
        SynchronizedCollectionsSerializer.registerSerializers(kryo);
        kryo.register(TimeZone.class, new TimeZoneSerializer());
        kryo.register(ZoneId.class, new ZoneIdSerializer());
        kryo.register(ZoneOffset.class, new ZoneOffsetSerializer());
        try {
            long time = System.nanoTime();
            GzipCompressorOutputStream out = new GzipCompressorOutputStream(new FileOutputStream(new File(homeDirectory() + "saves/" + file)));
            Output output = new Output(out);
            kryo.writeClassAndObject(output, new World());
            output.flush();
            output.close();
            out.close();
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
        Kryo kryo = new Kryo();
        kryo.setReferences(true);
        kryo.register(Money.class, new MoneySerializer());
        SynchronizedCollectionsSerializer.registerSerializers(kryo);
        kryo.register(TimeZone.class, new TimeZoneSerializer());
        kryo.register(ZoneId.class, new ZoneIdSerializer());
        kryo.register(ZoneOffset.class, new ZoneOffsetSerializer());
        try {
            GzipCompressorInputStream in = new GzipCompressorInputStream(new FileInputStream(new File(homeDirectory() + "saves/" + file)));
            Input input = new Input(in);
            String result = "";
            world = (World) kryo.readClassAndObject(input);
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
        Alliances.clear();
        world.getAirlines().forEach(Airlines::put);
        world.getAirports().forEach(Airports::put);
        world.getRoutes().forEach(GlobalRoutes::put);
        world.getCountries().forEach(Countries::put);
        world.getCompanies().forEach(Companies::put);
        world.getManufacturers().forEach(AircraftManufacturers::put);
        world.getAircraftTypes().forEach(AircraftTypes::put);
        world.getBills().forEach(Bills::add);
        world.getAlliances().forEach(Alliances::put);
        GameProperties.setNameOfSim(Config.nameOfSim);
        GameProperties.setInvestorDifficulty(world.getDifficulty());
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

    private List<Airport> airports = Airports.sortedValuesList();

    private List<Airline> airlines = Airlines.cloneAirlines();

    private List<Company> companies = Companies.companyList();

    private List<AircraftManufacturer> manufacturers = AircraftManufacturers.listManufacturers();

    private List<Country> countries = Countries.countryList();

    private List<Bill> bills = Bills.billList();

    private List<GlobalRoute> routes = GlobalRoutes.listRoutes();

    private List<AircraftType> aircraftTypes = AircraftTypes.listTypes();

    private List<Alliance> alliances = Alliances.allianceList();

    private int difficulty = GameProperties.getInvestorDifficulty();

    public World(){}

}
