package com.project.donate.core.helpers;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class PropertiesUtils {

    private final static Logger logger = Logger.getLogger(PropertiesUtils.class);

    public static Optional<String> getPropertyFromConfig(String property){

        Optional<String> value = Optional.empty();

        try (InputStream input = PropertiesUtils.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {

                logger.warn("Config.properties stream should not be null");
                return value;
            }

            Properties properties = new Properties();
            properties.load(input);

            String propertyValue = properties.getProperty(property);

            value = Optional.of(propertyValue);

        } catch (IOException ioException) {

            logger.warn("IOException while .properties file opening");
        }

        return value;
    }
}
