package com.krimson701.baekchat.configuration;

import com.krimson701.baekchat.configuration.properties.InterceptorMappingProps;
import com.krimson701.baekchat.configuration.properties.InterceptorProps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.krimson701.baekchat")
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private InterceptorProps interceptorProps;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    /**
     * 추후 Cors 설정 여기서 통합해야함 (파일명 : CorsConfig)
     */
//    @Override
//    protected void addCorsMappings(CorsRegistry registry) {
//        log.info("addCorsMappings {}", corsProp.toString());
//
//        for (String mapping : corsProp.getMappings()) {
//            registry.addMapping(mapping).allowedOrigins(corsProp.getAllowOrigins())
//                    .allowedHeaders(corsProp.getAllowHeaders()).allowedMethods(corsProp.getAllowMethods())
//                    .allowCredentials(true);
//        }
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("addINterceptors 실행됐다=========================================================================");
        log.info("addInterceptors {}", interceptorProps.toString());

        {
            InterceptorMappingProps interceptorMappingProps = interceptorProps.getAuthCheck();
            InterceptorRegistration interceptorRegistration = registry.addInterceptor((HandlerInterceptor) applicationContext.getBean(interceptorMappingProps.getInterceptorName()));
            interceptorRegistration.addPathPatterns(interceptorMappingProps.getMappings());
            interceptorRegistration.excludePathPatterns(interceptorMappingProps.getExcludeMappings());
            interceptorRegistration.order(Ordered.HIGHEST_PRECEDENCE);
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
