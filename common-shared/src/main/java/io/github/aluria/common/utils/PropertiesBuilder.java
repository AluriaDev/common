package io.github.aluria.common.utils;

import java.util.Properties;

public class PropertiesBuilder {

    private final Properties properties;

    public PropertiesBuilder() {
        this.properties = new Properties();
    }

    public PropertiesBuilder(Properties properties) {
        this.properties = properties;
    }

    public PropertiesBuilder with(String key, String value) {
        properties.put(key, value);
        return this;
    }

    public Properties wrap() {
        return this.properties;
    }
}
