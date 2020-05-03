package com.krimson701.baekchat.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class ChattingMessage implements Serializable {
    private String message;
    private String user;
    private Long timeStamp;
    private Long roomId;

    private String fileName;
    private String rawData;

    public ChattingMessage() {
    }

    public ChattingMessage(String message, String user) {
        this.user = user;
        this.message = message;
    }

    public ChattingMessage(String fileName, String rawData, String user) {

        this.fileName = fileName;
        this.rawData = rawData;
        this.user = user;
    }

    public ChattingMessage(String message) {
        this.message = message;
    }
}
