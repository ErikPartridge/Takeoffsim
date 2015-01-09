package com.takeoffsim.views.server;

import fi.iki.elonen.NanoHTTPD;

import java.util.Map;

/**
 * Created by erik on 1/8/15.
 */
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
        String uri = session.getUri();
        String trimmed = uri.replaceFirst("localhost:40973", "").replace("http://", "").replace("127.0.0.1:40973","");
        trimmed = trimmed.split("\\?")[0];
        return new Response("I think you tried to visit the url " + trimmed);
    }
}
