package com.ray.utils.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties PROPS = new Properties();
    private static final String FILE_PATH = "src/test/resources/config.properties";

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
