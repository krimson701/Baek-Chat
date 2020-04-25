package com.krimson701.baekchat.configuration;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.Set;

@Profile({"local", "dev"})
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Profile("local")
    @Bean
    public Docket localApi() {
        String info = "-----------------------------\n"
                + "-- swagger api for [local] --\n"
                + "-----------------------------\n";
        return getDefaultDocket(null, null);
    }

    @Profile("dev")
    @Bean
    public Docket devApi() {
        String info = "-----------------------------\n"
                + "-- swagger api for [ dev ] --\n"
                + "-----------------------------\n";
        Set<String> protocols = new HashSet<String>();
        protocols.add("https");
        return getDefaultDocket(protocols, "baek-chat.openur.biz"); // openur.biz 할지 말지 아직안정함
    }

    private Docket getDefaultDocket(Set<String> protocols, String host){
        ApiInfo apiInfo = new ApiInfoBuilder().title("baek-chat-api").description("baek-chat API 명세").build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        if(protocols != null) {
            docket.protocols(protocols);
        }

        if(host != null) {
            docket.host(host);
        }
        return docket
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.krimson701.baekchat.controller"))
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(apiInfo);

    }
}
