package com.krimson701.baekchat.configuration.common.spring;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.core.CollectionFactory;
import org.springframework.lang.Nullable;

import java.util.Properties;

public class CustomYamlPropertiesFactoryBean extends YamlProcessor implements FactoryBean<Properties>, InitializingBean {

    @Getter
    @Setter
    private boolean singleton = true;

    @Nullable
    private Properties properties;

    @Override
    public void afterPropertiesSet() {
        if (isSingleton()) {
            this.properties = createProperties();
        }
    }

    @Override
    @Nullable
    public Properties getObject() {
        return (this.properties != null ? this.properties : createProperties());
    }

    @Override
    public Class<?> getObjectType() {
        return Properties.class;
    }


    /**
     * Template method that subclasses may override to construct the object
     * returned by this factory. The default implementation returns a
     * properties with the content of all resources.
     * <p>Invoked lazily the first time {@link #getObject()} is invoked in
     * case of a shared singleton; else, on each {@link #getObject()} call.
     * @return the object returned by this factory
     * @see #process(MatchCallback) ()
     */
    protected Properties createProperties() {
        Properties result = CollectionFactory.createStringAdaptingProperties();
        process((properties, map) -> result.putAll(properties));
        return result;
    }

}
