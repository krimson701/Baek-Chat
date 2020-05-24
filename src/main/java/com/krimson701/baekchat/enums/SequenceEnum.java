package com.krimson701.baekchat.enums;

import lombok.Getter;

/**
 * 메신저에 사용하는 시퀀스
 * @author "jason jason@collab.ee"
 * @since Jan 17, 2020 6:37:10 PM
 */
@Getter
public enum SequenceEnum {
    /**
     * 채널 시퀀스
     */
    channel("channel_sequence"),
    /**
     * 메시지 번호 시퀀스
     */
    message("message_sequence"),
    /**
     * 파일번호 시퀀스
     */
    file("file_sequence"),
    ;

    private String value;

    private SequenceEnum(String refValue) {
        this.value = refValue;
    }
}