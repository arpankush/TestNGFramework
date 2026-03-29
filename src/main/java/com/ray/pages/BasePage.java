package com.ray.pages;

import com.ray.utils.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait webDriverWait;

    public BasePage(){
        this.driver = DriverManager.getDriverForCurrentThread();
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitForVisible(By locator){
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    protected void click(By locator){
        driver.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement el = waitForVisible(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitForVisible(locator).getText();
    }

}
