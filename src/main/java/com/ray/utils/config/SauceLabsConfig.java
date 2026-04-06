package com.ray.utils.config;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public class SauceLabsConfig {
    private SauceLabsConfig() {}

    public static String getUsername() {
        return System.getenv("SAUCE_USERNAME");
    }

    public static String getAccessKey() {
        return System.getenv("SAUCE_ACCESS_KEY");
    }

    public static String getRegionUrl() {
        return "https://ondemand.eu-central-1.saucelabs.com:443/wd/hub";
    }

    public static MutableCapabilities buildCapabilities(String browser) {
        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("username", getUsername());
        sauceOptions.put("accessKey", getAccessKey());
        sauceOptions.put("build", getBuildName());
        sauceOptions.put("name", Thread.currentThread().getName());

        return switch (browser.toLowerCase()) {
            case "firefox" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPlatformName("Windows 11");
                firefoxOptions.setBrowserVersion("latest");
                firefoxOptions.setCapability("sauce:options", sauceOptions);
                yield firefoxOptions;
            }
            default -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPlatformName("Windows 11");
                chromeOptions.setBrowserVersion("latest");
                chromeOptions.setCapability("sauce:options", sauceOptions);
                yield chromeOptions;
            }
        };
    }

    private static String getBuildName() {
        String ci = System.getenv("GITHUB_RUN_NUMBER");
        return ci != null
                ? "GitHub Actions #" + ci
                : "selenium-build-R2NA2";
    }
}