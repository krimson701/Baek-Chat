package com.krimson701.baekchat.configuration;

import com.krimson701.baekchat.configuration.common.spring.NewYamlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class PropConfig {
    @Configuration
    @Profile({"default", "local"})
    @PropertySource(value = {
            "classpath:/property/baekchat.yml",
            "classpath:/property/corsProp.yml",
            "classpath:/property/interceptorMapping.yml"
    }, factory = NewYamlPropertySourceFactory.class)
    public class PropLocalConfig {
    }

    @Configuration
    @Profile("dev")
    @PropertySource(value = {
            "classpath:/property/baekchat-dev.yml",
            "classpath:/property/corsProp.yml",
            "classpath:/property/interceptorMapping.yml"
    }, factory = NewYamlPropertySourceFactory.class)
    public class PropDevConfig {
    }
}
