package com.krimson701.baekchat.configuration.common.spring;

import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Properties;

public class SpringProfileDocumentMatcher implements YamlProcessor.DocumentMatcher, EnvironmentAware {

    private static final String[] DEFAULT_PROFILES = new String[]{"default"};

    private String[] activeProfiles = new String[0];

    public SpringProfileDocumentMatcher() {
    }

    public SpringProfileDocumentMatcher(String... profiles) {
        addActiveProfiles(profiles);
    }

    public void addActiveProfiles(String... profiles) {
        LinkedHashSet<String> set = new LinkedHashSet<String>(Arrays.asList(this.activeProfiles));
        Collections.addAll(set, profiles);
        this.activeProfiles = set.toArray(new String[set.size()]);
    }

    @Override
    public YamlProcessor.MatchStatus matches(Properties properties) {
        String[] profiles = this.activeProfiles;
        if (profiles.length == 0) {
            profiles = DEFAULT_PROFILES;
        }
        return new ArrayDocumentMatcher("spring.profiles", profiles).matches(properties);
    }

    @Override
    public void setEnvironment(Environment environment) {
        if (environment != null) {
            addActiveProfiles(environment.getActiveProfiles());
        }
    }
}