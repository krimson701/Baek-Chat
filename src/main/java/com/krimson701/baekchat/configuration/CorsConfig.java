package com.krimson701.baekchat.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry cr) {
        cr.addMapping("/**")
                .allowedOrigins("*")  // 허용할 주소 및 포트
                .allowCredentials(true);
    }
}
