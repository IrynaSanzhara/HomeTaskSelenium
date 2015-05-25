package com.gl.training;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by iryna.sanzhara on 5/25/2015.
 */
public class Login {

    private static final String JENKINS_URL = "http://clm-aus-002258.bmc.com:8080";
    private static final String LOGIN = "//*[contains(@href,'/login')]";
    private static final String FULL_USER_NAME = "//*[@class='model-link inside inverse']/b";

    /**
     * Login page
     */
    private static final String LOGIN_USER_FIELD = "j_username";
    private static final String LOGIN_PASSWORD_FIELD = "j_password";
    private static final String LOGIN_REMEMBER_CHECKBOX = "remember_me";
    private static final String LOGIN_BUTTON = "yui-gen1-button";

    /**
     * Test data
     */
    private static final String USER = "Test_Iryna";
    private static final String PASSWORD = "password";
    private static final String TEST_FULL_NAME = "Test";

    private static WebDriver driver;

    @AfterClass
    public static void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeClass
    public void InitBrowser() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void open_login_page() {
        driver.get(JENKINS_URL);
        driver.findElement(By.xpath(LOGIN)).click();
        driver.findElement(By.id(LOGIN_USER_FIELD));
        driver.findElement(By.name(LOGIN_PASSWORD_FIELD));
        driver.findElement(By.id(LOGIN_REMEMBER_CHECKBOX));
        driver.findElement(By.id(LOGIN_BUTTON));
    }

    @Test(dependsOnMethods = "open_login_page")
    public void login_with_existing_user() {
        driver.findElement(By.id(LOGIN_USER_FIELD)).sendKeys(USER);
        driver.findElement(By.name(LOGIN_PASSWORD_FIELD)).sendKeys(PASSWORD);
        driver.findElement(By.id(LOGIN_BUTTON)).click();
        String actualUserFullName = driver.findElement(By.xpath(FULL_USER_NAME)).getText();
        Assert.assertEquals(TEST_FULL_NAME, actualUserFullName, "User name is not as expected!");
        driver.findElement(By.xpath("/*//*[@href='/logout']")).click();
    }

    @Test(dependsOnMethods = "open_login_page")
    public void login_with_incorrect_user() {
        final String expectedErrorMessage = "Invalid login information. Please try again.";
        driver.findElement(By.xpath(LOGIN)).click();
        driver.findElement(By.id(LOGIN_USER_FIELD)).sendKeys("1111");
        driver.findElement(By.name(LOGIN_PASSWORD_FIELD)).sendKeys(PASSWORD);
        driver.findElement(By.id(LOGIN_BUTTON)).click();

    String actualErrorMessage = driver.findElement(By.xpath("//*[@id='main-panel-content']/div[1]")).getText().trim().split("\n")[0];
    Assert.assertEquals(expectedErrorMessage,actualErrorMessage,"Error message is not as expected!");
}
    @Test(dependsOnMethods = "open_login_page")
    public void login_with_incorrect_password() {
        final String expectedErrorMessage = "Invalid login information. Please try again.";
        driver.findElement(By.xpath(LOGIN)).click();
        driver.findElement(By.id(LOGIN_USER_FIELD)).sendKeys(USER);
        driver.findElement(By.name(LOGIN_PASSWORD_FIELD)).sendKeys("12345");
        driver.findElement(By.id(LOGIN_BUTTON)).click();

        String actualErrorMessage = driver.findElement(By.xpath("//*[@id='main-panel-content']/div[1]")).getText().trim().split("\n")[0];
        Assert.assertEquals(expectedErrorMessage,actualErrorMessage,"Error message is not as expected!");
    }

}
