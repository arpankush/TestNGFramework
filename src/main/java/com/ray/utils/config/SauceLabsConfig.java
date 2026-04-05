package com.ray.utils.config;

import org.openqa.selenium.MutableCapabilities;

public class SauceLabsConfig {
    private SauceLabsConfig() {}

    public static String getUsername() {
        return System.getenv("SAUCE_USERNAME");
    }

    public static String getAccessKey() {
        return System.getenv("SAUCE_ACCESS_KEY");
    }

    public static String getRegionUrl() {
        return "https://ondemand.eu-central-1.saucelabs.com/wd/hub";
    }

    public static MutableCapabilities buildCapabilities(String browser) {
        MutableCapabilities caps = new MutableCapabilities();

        switch (browser.toLowerCase()) {
            case "firefox" -> {
                caps.setCapability("browserName", "firefox");
                caps.setCapability("browserVersion", "latest");
            }
            default -> {
                caps.setCapability("browserName", "chrome");
                caps.setCapability("browserVersion", "latest");
            }
        }

        caps.setCapability("platformName", "Windows 11");

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", getUsername());
        sauceOptions.setCapability("accessKey", getAccessKey());
        sauceOptions.setCapability("build", getBuildName());
        sauceOptions.setCapability("name", Thread.currentThread().getName());
        sauceOptions.setCapability("tunnelName", "");

        caps.setCapability("sauce:options", sauceOptions);
        return caps;
    }

    private static String getBuildName() {
        String ci = System.getenv("GITHUB_RUN_NUMBER");
        return ci != null
                ? "GitHub Actions #" + ci
                : "Local Run — " + java.time.LocalDateTime.now();
    }
}
