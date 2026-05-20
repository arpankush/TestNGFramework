package com.ray.test;

import com.ray.base.BaseTest;
import com.ray.pages.LoginPage;
import com.ray.utils.readers.ConfigReader;
import com.ray.utils.managers.DriverManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Authentication")
@Feature("Login Page")
public class LoginTest extends BaseTest {

    @Test(description = "Valid password shows no error")
    @Story("User can log in with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.open(ConfigReader.get("url"));
        loginPage.enterUsername("student").enterPassword("Password123").clickLogin();

        Assert.assertEquals(DriverManager.getDriver().getCurrentUrl(), "https://practicetestautomation.com/logged-in-successfully/");
    }

    @Test(description = "Invalid password to failed test case")
    @Story("User cannot log in with invalid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidLoginToFailTest() {
        LoginPage loginPage = new LoginPage();
        loginPage.open(ConfigReader.get("url"));
        loginPage.enterUsername("student").enterPassword("Password123QQQQ").clickLogin();

        Assert.assertEquals(DriverManager.getDriver().getCurrentUrl(), "https://practicetestautomation.com/logged-in-successfully/");
    }

    @Test(description = "Invalid password shows error")
    @Story("User sees error on wrong password")
    @Severity(SeverityLevel.NORMAL)
    public void testInvalidPassword() {
        LoginPage loginPage = new LoginPage()
                .open(ConfigReader.get("url"));
        loginPage.enterUsername("student")
                .enterPassword("wrongpass")
                .clickLogin();

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be shown");
        Assert.assertEquals(loginPage.getErrorMessage(), "Your password is invalid!");

    }
}
