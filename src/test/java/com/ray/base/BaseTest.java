package com.ray.base;

import com.ray.utils.config.ConfigReader;
import com.ray.utils.driver.DriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
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
    public void tearDown(ITestResult result){
        if (result.getStatus() == ITestResult.FAILURE){
            attachScreenshot();
        }
        DriverManager.quitDriverThread();
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    private byte[] attachScreenshot(){
        return ((TakesScreenshot)DriverManager.getDriverForCurrentThread()).getScreenshotAs(OutputType.BYTES);
    }

}
