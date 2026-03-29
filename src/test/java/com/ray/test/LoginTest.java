package com.ray.test;

import com.ray.base.BaseTest;
import com.ray.pages.LoginPage;
import com.ray.utils.config.ConfigReader;
import com.ray.utils.driver.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void TestValidLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.open(ConfigReader.getProperty("url"));
        loginPage.enterUsername("student").enterPassword("Password123").clickLogin();

        Assert.assertEquals(DriverManager.getDriverForCurrentThread().getCurrentUrl(), "https://practicetestautomation.com/logged-in-successfully/");
    }

    @Test(description = "Invalid password shows error")
    public void testInvalidPassword() {
        LoginPage loginPage = new LoginPage()
                .open(ConfigReader.getProperty("url"));
        loginPage.enterUsername("student")
                .enterPassword("wrongpass")
                .clickLogin();

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be shown");
        Assert.assertEquals(loginPage.getErrorMessage(), "Your password is invalid!");

    }
}
