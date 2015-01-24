/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.server;

import com.jcabi.aspects.Async;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.takeoffsim.controllers.AirlineController;
import com.takeoffsim.controllers.CreationController;
import com.takeoffsim.controllers.HumanController;
import com.takeoffsim.controllers.LoadController;
import com.takeoffsim.services.Serialize;
import fi.iki.elonen.NanoHTTPD;
import lombok.extern.apachecommons.CommonsLog;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

@CommonsLog
public class Server extends NanoHTTPD {

    public Server(){
        super(40973);
    }

    private Server(int port) {
        super(port);
    }

    private Server(String hostname, int port) {
        super(hostname, port);
    }

    /**
     * Serves the results of trying to view a page
     * @param session the http session
     * @return a response
     */
    @Override
    public Response serve(IHTTPSession session){
        //Which file are they trying to access
        String uri = session.getUri();
        Map<String, String> files = session.getParms();
        String trimmed = uri.replaceFirst("localhost:40973", "").replace("http://", "").replace("127.0.0.1:40973","");
        trimmed = trimmed.split("\\?")[0];
        //Don't let them get the source code!
        String mimeType = "";
        try{
            mimeType = getMimeType(trimmed);
        }catch (SecurityException ignored){
            log.error("Tried to access a protected file.");
            return new Response(Response.Status.FORBIDDEN, "text/text", "You tried to access a forbidden file");
        }
        
        //If it's valid, parse it.
        Response response;
        try{
            response = new Response(Response.Status.ACCEPTED, mimeType, getResource(trimmed, files));
        } catch (SecurityException | PebbleException | IOException e) {
            log.error(e);
            response = new Response(Response.Status.BAD_REQUEST, "text/html","<b>Could not find</b> <a href=\"" + Main.back() + "\">back" + "</a>");
        } catch (RuntimeException e){
            log.error(e);
            response = new Response(Response.Status.NOT_FOUND, "text/html","<b>Could not find</b> <a href=\"" + Main.back() + "\">back" + "</a>");
        }
        return response;
    }

    /**
     * * 
     * @param url the url for the resource
     * @param params the parameters for the query
     * @return an inputstream for the resource
     */
    @Async
    final InputStream getResource(String url, Map<String, String> params) throws com.mitchellbosecke.pebble.error.PebbleException, IOException {
        if(url.matches("/creation*")){
            return CreationController.manage(url, params);
        }else if(url.matches("/loading*")){
            return LoadController.manage(url, params);
        }else if(url.matches("/human*")){
            return HumanController.manage(url, params);
        }else if(url.matches("/airline*")){
            return AirlineController.manage(url, params);
        }
        switch(url){
            case "/index.html" : Main.clearAll(); return resourceAtPath("/index.html");
            case "/exit" :Serialize.writeAll();System.exit(3);
        }
        return resourceAtPath(url);
    }

    /**
     * * 
     * @param path the path to render the static page from
     * @return the static page's InputStream (FileInputStream)
     */
    public static final InputStream resourceAtPath(String path){
        try{
            return new FileInputStream("/home/erik/Takeoffsim/themes/TakeoffSim-Themes/default/" + path);
        }catch (FileNotFoundException e){
            log.error(e);
            return stringToInputStream("<b>Could not find " + path + "</b> <a href=\"" + Main.back() + "\">back" + "</a>");
        }
    }
    /**
     * *
     * @param in non-null string
     * @return an InputStream implemented as a ByteArrayInputStream containing the bytes of the string
     */
    public static InputStream stringToInputStream(String in){
        return new ByteArrayInputStream(in.getBytes(Charset.defaultCharset()));
    }


    /**
     * *
     * @param url the url to the resource
     * @return the formal mime type
     * @throws SecurityException if the page should never be viewed by users
     */
    final String getMimeType(String url) {
        if(url.endsWith(".jpg") || url.endsWith(".jpeg")){
            return "image/jpeg";
        }else if(url.endsWith(".css")){
            return "text/css";
        }else if(url.endsWith(".js")){
            return "application/javascript";
        }else if(url.endsWith(".json")){
            return "application/json";
        }else if(url.endsWith(".png")){
            return "image/png";
        }else if(url.endsWith(".svg")){
            return "image/svg+xml";
        }else if(url.endsWith(".java") || url.endsWith(".jar") || url.endsWith(".xml") || url.endsWith(".yml") || url.endsWith(".class")){
            throw new SecurityException("Protected");
        }else if(url.endsWith(".pdf")) {
            return "application/pdf";
        }else{
            return "text/html";
        }
    }
    
}
