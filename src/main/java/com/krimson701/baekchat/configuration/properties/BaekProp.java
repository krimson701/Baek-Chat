package com.krimson701.baekchat.configuration.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

/**
 * 백챗 설정 property
 * Created by hoon on 2020. 6. 18..
 * 추후에 이것도 다 Spring Config로 옮겨야하는데......
 */
@Repository
@ConfigurationProperties("baekchat")
@Data
@EqualsAndHashCode(callSuper=false)
public class BaekProp {

    private String runningType;

    private String uosApiHost;

    private String elkHost;
}
