package com.ray.utils.readers;

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

    public static String get(String key){
        return PROPS.getProperty(key);
    }

    public static String getBrowser() {
        // System property from -Dbrowser=chrome overrides config.properties
        String sysProp = System.getProperty("browser");
        return sysProp != null ? sysProp : get("browser");
    }

}
