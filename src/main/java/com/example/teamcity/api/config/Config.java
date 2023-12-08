package com.example.teamcity.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final static String CONFIG_PROPERTIES = "config.properties";
    private static Config config;
    private Properties properties;

    private Config() {
        properties = new Properties();
        loadProperties(CONFIG_PROPERTIES);
    }

    public static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public void loadProperties(String filename) {
        try (InputStream stream = Config.class.getClassLoader().getResourceAsStream(filename)) {
            if (stream == null) {
                System.err.println("File not found " + filename);
                return;
            }
            properties.load(stream);
        } catch (IOException e) {
            System.err.println("Error during file reading " + filename);
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String host) {
        return getConfig().properties.getProperty(host);
    }

}
