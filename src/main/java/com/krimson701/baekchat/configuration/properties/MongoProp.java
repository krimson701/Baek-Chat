package com.krimson701.baekchat.configuration.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

/**
 * fcm 설정
 */
@Repository
@ConfigurationProperties("spring.data.mongodb")
@Data
@EqualsAndHashCode(callSuper=false)
public class MongoProp {

    /**
     * 호스트
     */
    private String host;

    /**
     * uri
     */
    private String uri;

    /**
     * 포트
     */
    private int port;

    /**
     * 데이터 베이스
     */
    private String database;

    /**
     * 계정 이름
     */
    private String username;

    /**
     * 계정 비밀번호
     */
    private String password;
}
