package com.krimson701.baekchat.model;


import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "messenger_message")
public class ChattingMessage implements Serializable {

    /**
     * 메시지 번호
     */
    @Indexed(name = "idx_messageno")
    private long messageNo;

    /**
     * 유저번호
     */
    @Indexed(name = "idx_userno")
    private long userNo;

    /**
     * 채널 번호
     */
    @Indexed(name = "idx_channel")
    private Long channelNo;

    /**
     * 메시지
     */
    private String message;

    private Long timestamp;

    private String fileName;
    private String rawData;

    public ChattingMessage() {
    }

    public ChattingMessage(String message, long userNo) {
        this.userNo = userNo;
        this.message = message;
    }

    public ChattingMessage(String fileName, String rawData, long userNo) {

        this.fileName = fileName;
        this.rawData = rawData;
        this.userNo = userNo;
    }

    public ChattingMessage(String message) {
        this.message = message;
    }
}
