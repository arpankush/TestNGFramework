package com.ray.base;

import com.ray.utils.config.SauceLabsConfig;
import com.ray.utils.readers.ConfigReader;
import com.ray.utils.managers.DriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    @BeforeMethod
    public void setup(){
        DriverManager.setDriverThread(ConfigReader.getProperty("browser"));
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("implicit.wait"))));
    }

    @AfterMethod
    public void tearDown(ITestResult result){
        reportTestStatusToSauceLabs(result);
        if (result.getStatus() == ITestResult.FAILURE){
            attachScreenshot();
        }
        DriverManager.quitDriverThread();
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    private byte[] attachScreenshot(){
        return ((TakesScreenshot)DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    private void reportTestStatusToSauceLabs(ITestResult result){
        if (System.getenv("SAUCE") == null) return;
        WebDriver driver = DriverManager.getDriver();
        if (driver instanceof RemoteWebDriver){
            String status = result.isSuccess()? "passed" : "failed";
            ((RemoteWebDriver)driver).executeScript("sauce:job-result=" + status);
        }
    }

}
