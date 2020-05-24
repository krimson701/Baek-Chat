package com.krimson701.baekchat.configuration.common.spring;

import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.Properties;

public class NewYamlResourcePropertySource extends PropertiesPropertySource {
    public NewYamlResourcePropertySource(String name, EncodedResource resource) throws IOException {
        super(name, properties(resource.getResource()));
    }

    private static Properties properties(Resource resource){
        CustomYamlPropertiesFactoryBean factoryBean = new CustomYamlPropertiesFactoryBean();
        factoryBean.setDocumentMatchers(new SpringProfileDocumentMatcher());
        factoryBean.setResources(
                new Resource[]{
                        resource
                }
        );
        factoryBean.createProperties();
        return factoryBean.getObject();
    }

}
