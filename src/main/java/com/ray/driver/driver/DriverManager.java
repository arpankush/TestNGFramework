package com.ray.driver.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    private DriverManager(){}

    public static WebDriver getDriverForCurrentThread(){
        return driverThread.get();
    }

    public static void setDriverThread(String browser){
        WebDriver driver;
        switch (browser.toLowerCase()){
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                driver = null;
                Assert.fail("Browser is not recognized.");
        }
        driverThread.set(driver);
    }

    public static void quitDriverThread(){
        if (driverThread.get() != null){
            driverThread.get().quit();
            driverThread.remove();
        }
    }

}
