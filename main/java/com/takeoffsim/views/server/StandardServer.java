/*
 * Copyright (c) Erik Malmstrom-Partridge 2015. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

package com.takeoffsim.views.server;

import com.takeoffsim.graphics.Main;
import com.takeoffsim.main.Config;
import lombok.extern.apachecommons.CommonsLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;

/**
 * Created by erik on 1/8/15.
 */
@CommonsLog
public class StandardServer {

    public static StandardServer server = null;

    public static void create(){
        Server server = new Server(40973);

        ResourceHandler handler = new ResourceHandler();
        handler.setDirectoriesListed(false);
        handler.setWelcomeFiles(new String[]{"landing.html"});
        handler.setResourceBase(Config.getPath() + "theme/");
        server.setHandler(handler);

        try{
            server.start();
        }catch(Exception e){
            server = new Server(14563);

            try{server.start();}
            catch(Exception e1){
                log.fatal("Failed to start the graphics server on ports 40973 or 14563. Exiting.");
                System.exit(-9);
            }
        }
        try{
            server.join();
        }catch(InterruptedException e){
            log.error("Failed to join the server");
        }


    }
}
