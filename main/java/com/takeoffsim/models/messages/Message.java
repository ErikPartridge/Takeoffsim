/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

package com.takeoffsim.models.messages;

import lombok.Data;

import java.io.Serializable;

/**
 * @since version 0.3-alpha. (c) Erik Partridge 2015
 */
@Data
public class Message implements Serializable{

    private String subject;

    private String from;

    private String content;

    public Message(String subject, String from, String content) {
        this.subject = subject;
        this.from = from;
        this.content = content;
    }


}
