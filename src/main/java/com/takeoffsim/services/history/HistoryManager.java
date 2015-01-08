/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.services.history;

import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.main.Config;
import lombok.Cleanup;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utilities class that handles the storage of histories on disk. Good place for optimization.
 */
@CommonsLog
public class HistoryManager {

    public static boolean isExecuting = false;
    /**
     * The path to the archives folder. OS Specific. Default is for linux.
     */
    public static String path = "~/.takeoffsim/archives";

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
            ObjectOutputStream out = new ObjectOutputStream(new ZipArchiveOutputStream(file));
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
     * @return
     */
    public static History<Airline> getAirlineHistory(Airline a, LocalDateTime time){
        File folder = new File(path + "/" + Config.nameOfSim + "/" + "airline/");
        if(!folder.isDirectory()){
            log.error("Path for the airline history folder is not valid: " + path + "/" + Config.nameOfSim + "/airline/");
        }
        List<File> files = Arrays.asList(folder.listFiles());
        //TODO
        String regex = a.getIcao() + "-" + "\\\\s+";

        return null;
    }

    private static List<File> filter(List<File> files, String regex){
        ArrayList<File> actualFiles = new ArrayList<>();
        for(File f: files){
            if(f.isFile()){
                actualFiles.add(f);
            }
        }
        ArrayList<File> good = new ArrayList<>();
        for(File f: actualFiles){
            if(f.getName().matches(regex)){
                good.add(f);
            }
        }
        return good;
    }
}
