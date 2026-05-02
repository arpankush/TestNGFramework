package com.ray.pages;

import com.ray.utils.managers.DriverManager;
import com.ray.utils.readers.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait webDriverWait;

    public BasePage(){
        this.driver = DriverManager.getDriver();
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("implicit.wait"))));
        PageFactory.initElements(driver, this);
    }
    protected WebElement waitForVisible(By locator){
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    protected WebElement waitForVisible(WebElement webElement){
        return webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
    }
    protected void click(By locator) {
        driver.findElement(locator).click();
    }
    protected void click(WebElement webElement){
        webElement.click();
    }
    protected void typeText(By locator, String text) {
        WebElement el = waitForVisible(locator);
        el.clear();
        el.sendKeys(text);
    }
    protected void typeText(WebElement webElement, String text) {
        WebElement el = waitForVisible(webElement);
        el.clear();
        el.sendKeys(text);
    }
    protected String getText(By locator) {
        return waitForVisible(locator).getText();
    }
    protected String getText(WebElement webElement) {
        return waitForVisible(webElement).getText();
    }

}
