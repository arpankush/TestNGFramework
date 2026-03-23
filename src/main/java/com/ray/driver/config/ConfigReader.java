package com.ray.driver.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties PROPS = new Properties();
    private static final String FILE_PATH = "config.properties";

    static {
        try (InputStream inputStream = new FileInputStream(FILE_PATH)){
            PROPS.load(inputStream);
            } catch(Exception e){
                throw new RuntimeException("Failed to load config file.", e);
            }
        }

    public static String getProperty(String key){
        return PROPS.getProperty(key);
    }

}
