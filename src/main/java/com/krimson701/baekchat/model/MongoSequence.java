package com.krimson701.baekchat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 메신저에서 사용하는 시퀀스
 * @author "BChoon"
 */
@Data
@Document(collection = "messenger_sequence")
public class MongoSequence {
    public MongoSequence() {}

    public MongoSequence(String id) {
        this.id = id;
    }

    /**
     * 시퀀스 이름
     */
    @Id
    private String id;

    /**
     * 시퀀스 번호
     */
    private long seq;
}
