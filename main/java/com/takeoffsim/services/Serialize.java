/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */


package com.takeoffsim.services;

import com.jcabi.aspects.Cacheable;
import com.jcabi.aspects.RetryOnFailure;
import com.takeoffsim.models.airline.*;
import com.takeoffsim.models.airport.Airport;
import com.takeoffsim.models.airport.Airports;
import com.takeoffsim.models.economics.Bill;
import com.takeoffsim.models.economics.Bills;
import com.takeoffsim.models.economics.Companies;
import com.takeoffsim.models.economics.Company;
import com.takeoffsim.models.world.Countries;
import com.takeoffsim.models.world.Country;
import com.takeoffsim.views.server.Main;
import lombok.extern.apachecommons.CommonsLog;

import java.io.*;
import java.rmi.NoSuchObjectException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@SuppressWarnings("ResultOfMethodCallIgnored")
@CommonsLog
public class Serialize {


    private Serialize() {
    }

    public static void writeAll() {
        writeAirlines();
        writeAirports();
        writeCountries();
    }

    public static void loadWorld(String worldName) throws NoSuchObjectException {
        Main.clearAll();
        File directory = new File(homeDirectory() + "saves/" + worldName + "/");
        if (!directory.exists()) {
            throw new NoSuchObjectException("No world with name " + worldName);
        }

        loadAirlines(new File(directory.getPath() + "/Airlines.tss"));
        loadAirports(new File(directory.getPath() + "/Airports.tss"));
        loadCountries(new File(directory.getPath() + "/Countries.tss"));
    }

    public static void loadCountries(File file) {
        Collection<Country> countries = (Collection<Country>) rawRead(file);
        Countries.clear();
        countries.forEach(c -> Countries.putCountry(c.getIso(), c));
    }

    public static void loadAirports(File file) {
        Collection<Airport> airports = (Collection<Airport>) rawRead(file);
        Airports.clear();
        for (Airport a : airports) {
            Airports.put(a.getIcao(), a);
        }
    }

    public static void loadAlliances(File file) {
        Collection<Alliance> alliances = (Collection<Alliance>) rawRead(file);
        Alliances.clear();
        alliances.forEach(Alliances::put);
    }


    public static void loadAirlines(File file) {
        Collection<Airline> airlines = (Collection<Airline>) rawRead(file);
        Airlines.clear();
        for (Airline a : airlines) {
            Airlines.put(a.getIcao(), a);
            Airlines.putIcao(a.getName(), a.getIcao());
        }
    }


    public static Object rawRead(File file) {
        FileInputStream stream = null;
        ObjectInputStream in = null;
        Object o = null;
        try {
            stream = new FileInputStream(file);
            GZIPInputStream wrapper = new GZIPInputStream(stream);
            in = new ObjectInputStream(wrapper);
            o = in.readObject();
            in.close();
            wrapper.close();
            stream.close();
        } catch (IOException | ClassNotFoundException e) {
            log.error(e);
        }
        return o;
    }

    public static void loadCompanies(File file){
        Collection<Company> companies = (Collection<Company>) rawRead(file);
        Companies.clear();
        companies.forEach(c -> Companies.put(c.getName(), c));
    }

    public static void loadGlobalRoutes(File file){
        Collection<GlobalRoute> routes = (Collection<GlobalRoute>) rawRead(file);
        GlobalRoutes.clear();
        routes.forEach(GlobalRoutes::put);
    }

    public static void readBills(File file){
        BlockingQueue<Bill> queue = (BlockingQueue<Bill>) rawRead(file);
        Bills.clear();
        queue.stream().forEachOrdered(Bills::add);
    }
    public static void writeAlliances(){
        write("Alliances.tss", out -> out.writeObject(Alliances.getAlliances().values()));
    }

    public static void writeCountries() {
        write("Countries.tss", out -> out.writeObject(Countries.getCountries().values()));
    }

    public static void writeAirlines() {
        write("Airlines.tss", out -> out.writeObject(Airlines.getMap().values()));

    }

    public static void writeGlobalRoutes(){
        write("GlobalRoutes.tss", out -> out.writeObject(GlobalRoutes.globalRoutes.values()));
    }

    public static void writeCompanies(){
        write("Companies.tss", out -> out.writeObject(Companies.getCompanies().values()));
    }


    public static void writeBills(){
        write("Bills.tss", out -> out.writeObject(Bills.bills));
    }
    /**
     * This will serialize all the airports
     */
    public static void writeAirports() {
        write("Airports.tss", out -> out.writeObject(Airports.getAirports().values()));
    }

    public static void write(String name, WritableInterface inter){
        File directory = new File(homeDirectory() + "saves/" + Config.nameOfSim + "/");
        directory.mkdirs();
        File file = new File(homeDirectory() + "saves/" + Config.nameOfSim + name);
        try {
            FileOutputStream fs = new FileOutputStream(file);
            BufferedOutputStream buf = new BufferedOutputStream(fs);
            GZIPOutputStream wrapper = new GZIPOutputStream(buf);
            ObjectOutputStream out = new ObjectOutputStream(wrapper);
            inter.write(out);
            out.flush();
            wrapper.finish();
            wrapper.close();
            out.close();
            fs.close();
        } catch (IOException e) {
            log.error(e);
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

interface WritableInterface{

    public void write(ObjectOutputStream out) throws IOException;
}
