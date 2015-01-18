/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.views.server;

import fi.iki.elonen.NanoHTTPD;
import lombok.extern.apachecommons.CommonsLog;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

@CommonsLog
public class Server extends NanoHTTPD {

    public Server(){
        super(40973);
    }

    public Server(int port) {
        super(port);
    }

    public Server(String hostname, int port) {
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
        String mimeType ="";
        try{
            mimeType = getMimeType(trimmed);
        }catch (SecurityException e){
            log.error("Tried to access a protected file.");
            return new Response(Response.Status.FORBIDDEN, "text/text", "You tried to access a forbidden file");
        }
        
        //If it's valid, parse it.
        Response response;
        try{
            response = new Response(Response.Status.ACCEPTED, getMimeType(trimmed), getResource(trimmed, files));
        }catch(Exception e){
            log.error(e);
            response = new Response(Response.Status.NOT_FOUND, "text/html","<b>Could not find</b> <a href=\"" + Main.back() + "\">back" + "</a>");
        }

        return response;
    }

    /**
     * * 
     * @param url the url for the resource
     * @param params
     * @return an inputstream for the resource
     */
    public final InputStream getResource(String url, Map<String, String> params) throws Exception{
        switch(url){
            case "/create-airline.html" : return LoadPageGenerator.createAirlineView();
            case "/create-ceo.html" : return LoadPageGenerator.createCeoView(params);
            case "/create-world.html": return LoadPageGenerator.createWorldLoadView(params);
            case "/creation-results.html": return LoadPageGenerator.creationResultsView(params);
        }
        return resourceAtPath(url);
    }

    /**
     * * 
     * @param path the path to render the static page from
     * @return the static page's InputStream (FileInputStream)
     */
    public final InputStream resourceAtPath(String path){
        try{
            return new FileInputStream("/home/erik/Takeoffsim/themes/TakeoffSim-Themes/default/" + path);
        }catch (IOException e){
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
    protected final String getMimeType(String url) throws SecurityException{
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
        }else if(url.endsWith(".java")){
            throw new SecurityException();
        }else if(url.endsWith(".jar")){
            throw new SecurityException();
        }else if(url.endsWith(".xml")){
            throw new SecurityException();
        }else if(url.endsWith(".yml")){
            throw new SecurityException();
        }else if(url.endsWith(".class")){
            throw new SecurityException();
        }else if(url.endsWith(".pdf")) {
            return "application/pdf";
        }else{
            return "text/html";
        }
    }
    
}
