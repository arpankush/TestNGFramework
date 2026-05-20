package com.ray.utils.managers;

import com.ray.utils.config.SauceLabsConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.net.URL;
import java.util.Map;
import java.util.HashMap;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    private DriverManager(){}

    public static WebDriver getDriver(){
        return driverThread.get();
    }

    public static void setDriverThread(String browser){
        WebDriver driver;
        boolean isSauce = System.getenv("SAUCE") != null;
        boolean isCI = System.getenv("CI") != null;

        if (isSauce){
            driver = initSauceLabsDriver(browser);
        }
        else {
            driver = initLocalDriver(browser, isCI);
        }
        driverThread.set(driver);
    }

    private static WebDriver initSauceLabsDriver(String browser){
        try {
            return new RemoteWebDriver(new URL (SauceLabsConfig.getRegionUrl()), SauceLabsConfig.buildCapabilities(browser));
        } catch (Exception e){
            throw new RuntimeException("Failed to init Sauce Labs Driver", e);
        }
    }
    private static WebDriver initLocalDriver(String browser, boolean headless){
        return switch (browser.toLowerCase()){
            case "firefox" -> new FirefoxDriver();
            default -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
//                if (headless) {
//                    chromeOptions.addArguments(
//                            "--headless=new",
//                            "--no-sandbox",
//                            "--disable-dev-shm-usage",
//                            "--disable-gpu",
//                            "--window-size=1920,1080"
//                    );
//                }
                yield new ChromeDriver(chromeOptions);
            }
        };
    }

    public static void quitDriverThread(){
        if (driverThread.get() != null){
            driverThread.get().quit();
            driverThread.remove();
        }
    }

}
