package com.krimson701.baekchat.configuration.common.spring;

import com.google.common.collect.Sets;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Set;

public class NewYamlPropertySourceFactory implements PropertySourceFactory {
    private static final Set<String> YML_FILE_EXTENSIONS = Sets.newHashSet("yaml", "yml");

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        String filename = resource.getResource().getFilename();
        String[] filenameSplit = filename.split("\\.");
        if (filename != null && YML_FILE_EXTENSIONS.contains(filenameSplit[filenameSplit.length-1])) {
            return name != null ? new NewYamlResourcePropertySource(name, resource) : new NewYamlResourcePropertySource(getNameForResource(resource.getResource()), resource);
        }
        return (name != null ? new ResourcePropertySource(name, resource) : new ResourcePropertySource(resource));
    }

    private String getNameForResource(Resource resource) {
        String name = resource.getDescription();
        if (!StringUtils.hasText(name)) {
            name = resource.getClass().getSimpleName() + "@" + System.identityHashCode(resource);
        }
        return name;
    }
}
