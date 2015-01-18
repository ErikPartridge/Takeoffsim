/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.services.history;

import com.takeoffsim.main.Config;
import com.takeoffsim.models.airline.Airline;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utilities class that handles the storage of histories on disk. Good place for optimization.
 */
@CommonsLog
final class HistoryManager {

    public static boolean isExecuting = false;
    /**
     * The path to the archives folder. OS Specific. Default is for linux.
     */
    private static final String path = "~/.takeoffsim/archives";

    private HistoryManager() {
    }

    /**
     * This compresses and serializes an airline history, which is required because they use a lot of memory.
     * @param history the history to write to the file
     */
    public static void writeAirlineHistory(History<Airline> history){
        isExecuting = true;
        File folder = new File(path + "/" + Config.nameOfSim + "/airline");
        folder.mkdirs();
        File file = new File(path + "/" + Config.nameOfSim + "/" + "airlines/" + history.getObject().getIcao() + "-" + history.getTimeStamp().toString() + "zip");
        try{
            ObjectOutput out = new ObjectOutputStream(new ZipArchiveOutputStream(file));
            out.writeObject(history);
        }
        catch( IOException e){
            log.error(e);
        }
        isExecuting = false;
    }

    /**
     *
     * @param a the airline to get the history of
     * @param time the ideal time of the history (will get closest)
     * @return the histories around that time for that airline
     */
    public static History<Airline> getAirlineHistory(Airline a, LocalDateTime time){
        File folder = new File(path + "/" + Config.nameOfSim + "/" + "airline/");
        if(!folder.isDirectory()){
            log.error("Path for the airline history folder is not valid: " + path + "/" + Config.nameOfSim + "/airline/");
        }
        assert folder != null;
        @SuppressWarnings("ConstantConditions") List<File> files = Arrays.asList(folder.listFiles());
        //TODO
        String regex = a.getIcao() + "-" + "\\\\s+";

        return null;
    }

    private static List<File> filter(Collection<File> files, String regex){
        Collection<File> actualFiles = files.stream().filter(File::isFile).collect(Collectors.toList());
        return actualFiles.stream().filter(f -> f.getName().matches(regex)).collect(Collectors.toList());
    }
}
