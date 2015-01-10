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
        Map<String, String> params = session.getParms();
        //Which file are they trying to access
        String uri = session.getUri();
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
        Response response = new Response(Response.Status.ACCEPTED, getMimeType(trimmed), getResource(trimmed));
        return response;
    }

    /**
     * * 
     * @param url the url for the resource
     * @return an inputstream for the resource
     */
    public final InputStream getResource(String url){
        switch(url){
            default : return resourceAtPath(url);
        }
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
            return stringToInputStream("<b>Could not find " + path);
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
