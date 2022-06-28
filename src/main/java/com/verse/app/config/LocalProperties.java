package com.verse.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;


public class LocalProperties {

    private static Properties properties;

    static {
        try {
            ClassPathResource resource = new ClassPathResource("application.properties");
            properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getLocalProperty(String key) throws IOException {
        String property = null;
        property = properties.getProperty(key);
        if(property == null) {
            throw new RuntimeException("application.properties配置文件不存在配置项" + key);
        }
        return property;
    }


}
