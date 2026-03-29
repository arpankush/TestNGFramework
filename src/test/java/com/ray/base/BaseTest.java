package com.ray.base;

import com.ray.utils.config.ConfigReader;
import com.ray.utils.driver.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    @BeforeMethod
    public void setup(){
        DriverManager.setDriverThread(ConfigReader.getProperty("browser"));
        DriverManager.getDriverForCurrentThread().manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("implicit.wait"))));
    }

    @AfterMethod
    public void tearDown(){
        DriverManager.quitDriverThread();
    }

}
