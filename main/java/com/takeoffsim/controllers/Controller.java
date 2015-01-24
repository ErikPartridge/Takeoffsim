/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
interface Controller {

    /**
     * @param url the url to the page
     * @param params the parameters passed
     * @return an inputstream with the data
     * @throws IOException if there is an error
     */
    public static InputStream manage(String url, Map<String, String> params) throws IOException{
        throw new IOException("Must override this method");
    }

    static InputStream manage(String url, Map<String, String> params) throws IOException;
}
