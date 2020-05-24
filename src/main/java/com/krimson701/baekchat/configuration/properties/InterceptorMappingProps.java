package com.krimson701.baekchat.configuration.properties;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * BC-hoon 인터셉터의 Mapping 과 Exception 설정 가지고있음
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InterceptorMappingProps {

    private String interceptorName;

    private String[] mappings;

    private String[] excludeMappings;

    private int order;
}
