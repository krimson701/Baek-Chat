package com.krimson701.baekchat.configuration.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

@Repository
@ConfigurationProperties("interceptor")
@Data
@EqualsAndHashCode(callSuper=false)
public class InterceptorProps {

    private InterceptorMappingProps authCheck;
}
