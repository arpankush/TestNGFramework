package com.ray.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{

    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton   = By.id("submit");
    private final By errorMessage  = By.id("error");

        public LoginPage open(String url){
            driver.get(url);
            return this;
        }

    public LoginPage enterUsername(String username) {
        type(usernameField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    public void clickLogin() {
        click(loginButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return !driver.findElements(errorMessage).isEmpty();
    }

}
