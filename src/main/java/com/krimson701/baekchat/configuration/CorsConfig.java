package com.krimson701.baekchat.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
public class CorsConfig extends WebMvcConfigurationSupport {

//    @Override
//    public void addCorsMappings(CorsRegistry cr) {
//        cr.addMapping("/**")
//                .allowedOrigins("*")  // 허용할 주소 및 포트
//                .allowCredentials(true)
//                .allowedHeaders("*");
//                // allowed method 는 default로 GET, POST, DELETE 만 설정 PUT을 직접넣어줘야함
//    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Allow anyone and anything access. Probably ok for Swagger spec
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
