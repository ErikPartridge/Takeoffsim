package com.takeoffsim.views.server;

import fi.iki.elonen.NanoHTTPD;
import lombok.extern.apachecommons.CommonsLog;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Copyright Erik Partridge, 2015. All rights reserved.
 */
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
    
    public final InputStream getResource(String url){
        switch(url){
            case "/" : return null;
            default : return stringToInputStream("<b>Page not found</b>");
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
    
    protected final InputStream getHtml(String url){
        InputStream in = new ByteArrayInputStream(url.getBytes(Charset.defaultCharset()));
        return in;
    }

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
