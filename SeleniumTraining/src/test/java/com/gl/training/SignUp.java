package com.gl.training;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SignUp {

    private static final String JENKINS_URL = "http://clm-aus-002258.bmc.com:8080";
    private static final String SIGN_UP = "//*[@href='/signup']";

    private static final String FULL_USER_NAME = "//*[@class='model-link inside inverse']/b";

    /**
     * Sign up page
     */
    private static final String USERNAME_FIELD = "//*[@id='main-panel-content']/div/form/table/tbody/tr[1]/td/input";
    private static final String PASSWORD_FIELD = "//*[@id='main-panel-content']/div/form/table/tbody/tr[2]/td/input";
    private static final String CONFIRM_PASSWORD_FIELD = "//*[@id='main-panel-content']/div/form/table/tbody/tr[3]/td/input";
    private static final String FULL_NAME_FIELD = "//*[@id='main-panel-content']/div/form/table/tbody/tr[4]/td/input";
    private static final String EMAIL_FIELD = "//*[@id='main-panel-content']/div/form/table/tbody/tr[5]/td/input";
    private static final String SIGN_UP_BUTTON = "yui-gen1-button";

    /**
     * Test data
     */
    private static final String TEST_USERNAME = "Test_Iryna";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_FULL_NAME = "Test";
    private static final String TEST_USERNAME_REQUIRED = "Test_User";
    private static final String TEST_EMAIL_ADDRESS = "mail@kh.com";


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
    public void open_signup_page() {
        driver.get(JENKINS_URL);
        driver.findElement(By.xpath(SIGN_UP)).click();
        driver.findElement(By.xpath(USERNAME_FIELD));
        driver.findElement(By.xpath(PASSWORD_FIELD));
        driver.findElement(By.xpath(CONFIRM_PASSWORD_FIELD));
        driver.findElement(By.xpath(EMAIL_FIELD));
        driver.findElement(By.xpath(FULL_NAME_FIELD));
        driver.findElement(By.id(SIGN_UP_BUTTON));
    }


    @Test(dependsOnMethods = "open_signup_page")
    public void sign_up_with_all_correct_values() {
        driver.findElement(By.xpath(USERNAME_FIELD)).sendKeys(TEST_USERNAME);
        driver.findElement(By.xpath(PASSWORD_FIELD)).sendKeys(TEST_PASSWORD);
        driver.findElement(By.xpath(CONFIRM_PASSWORD_FIELD)).sendKeys(TEST_PASSWORD);
        driver.findElement(By.xpath(EMAIL_FIELD)).sendKeys(TEST_EMAIL_ADDRESS);
        driver.findElement(By.xpath(FULL_NAME_FIELD)).sendKeys(TEST_FULL_NAME);
        driver.findElement(By.id(SIGN_UP_BUTTON)).click();
        String actualUserFullName = driver.findElement(By.xpath(FULL_USER_NAME)).getText();
        Assert.assertEquals(TEST_FULL_NAME, actualUserFullName, "User name is not as expected!");
        driver.findElement(By.xpath("/*//*[@href='/logout']")).click();
    }

    @Test(dependsOnMethods = "sign_up_with_all_correct_values")
    public void sigh_up_with_already_existing_user() {
        final String expectedErrorMessage = "User name is already taken";
        open_signup_page();
        driver.findElement(By.xpath(USERNAME_FIELD)).sendKeys(TEST_USERNAME);
        driver.findElement(By.xpath(PASSWORD_FIELD)).sendKeys(TEST_PASSWORD);
        driver.findElement(By.xpath(CONFIRM_PASSWORD_FIELD)).sendKeys(TEST_PASSWORD);
        driver.findElement(By.xpath(EMAIL_FIELD)).sendKeys(TEST_EMAIL_ADDRESS);
        driver.findElement(By.xpath(FULL_NAME_FIELD)).sendKeys(TEST_FULL_NAME);
        driver.findElement(By.id(SIGN_UP_BUTTON)).click();

        String actualErrorMessage = driver.findElement(By.xpath("//*[@class='error']")).getText().trim();
        Assert.assertEquals(expectedErrorMessage, actualErrorMessage, "Error message is not as expected!");

        }

    @Test(dependsOnMethods = "sigh_up_with_already_existing_user")
    public void check_required_username_field() {
        final String expectedErrorMessage = "User name is required";
        open_signup_page();
        driver.findElement(By.xpath(PASSWORD_FIELD)).sendKeys(TEST_PASSWORD);
        driver.findElement(By.xpath(CONFIRM_PASSWORD_FIELD)).sendKeys(TEST_PASSWORD);
        driver.findElement(By.xpath(EMAIL_FIELD)).sendKeys(TEST_EMAIL_ADDRESS);
        driver.findElement(By.xpath(FULL_NAME_FIELD)).sendKeys(TEST_FULL_NAME);
        driver.findElement(By.id(SIGN_UP_BUTTON)).click();

        String actualErrorMessage = driver.findElement(By.xpath("//*[@class='error']")).getText().trim();
        Assert.assertEquals(expectedErrorMessage, actualErrorMessage, "Error message is not as expected!");

        driver.get(driver.getCurrentUrl());
    }

    @Test(dependsOnMethods = "sigh_up_with_already_existing_user")
    public void check_required_password_field() {
        open_signup_page();
        final String expectedErrorMessage = "Password is required";
        driver.findElement(By.xpath(USERNAME_FIELD)).sendKeys(TEST_USERNAME_REQUIRED);
        driver.findElement(By.xpath(CONFIRM_PASSWORD_FIELD)).sendKeys(TEST_PASSWORD);
        driver.findElement(By.xpath(EMAIL_FIELD)).sendKeys(TEST_EMAIL_ADDRESS);
        driver.findElement(By.xpath(FULL_NAME_FIELD)).sendKeys(TEST_FULL_NAME);
        driver.findElement(By.id(SIGN_UP_BUTTON)).click();

        String actualErrorMessage = driver.findElement(By.xpath("//*[@class='error']")).getText().trim();
        Assert.assertEquals(expectedErrorMessage, actualErrorMessage, "Error message is not as expected!");

        driver.get(driver.getCurrentUrl());

    }
    @Test(dependsOnMethods = "sigh_up_with_already_existing_user")
    public void check_required_confirm_password_field() {
        open_signup_page();
        final String expectedErrorMessage = "Password didnt match";
        driver.findElement(By.xpath(USERNAME_FIELD)).sendKeys(TEST_USERNAME_REQUIRED);
        driver.findElement(By.xpath(PASSWORD_FIELD)).sendKeys(TEST_PASSWORD);
        driver.findElement(By.xpath(EMAIL_FIELD)).sendKeys(TEST_EMAIL_ADDRESS);
        driver.findElement(By.xpath(FULL_NAME_FIELD)).sendKeys(TEST_FULL_NAME);
        driver.findElement(By.id(SIGN_UP_BUTTON)).click();

        String actualErrorMessage = driver.findElement(By.xpath("//*[@class='error']")).getText().trim();
        Assert.assertEquals(expectedErrorMessage, actualErrorMessage, "Error message is not as expected!");

        driver.get(driver.getCurrentUrl());
    }
    @Test(dependsOnMethods = "sigh_up_with_already_existing_user")
    public void check_required_email_address_field() {
        open_signup_page();
        final String expectedErrorMessage = "Invalid e-mail address";
        driver.findElement(By.xpath(USERNAME_FIELD)).sendKeys(TEST_USERNAME_REQUIRED);
        driver.findElement(By.xpath(PASSWORD_FIELD)).sendKeys(TEST_PASSWORD);
        driver.findElement(By.xpath(CONFIRM_PASSWORD_FIELD)).sendKeys(TEST_FULL_NAME);
        driver.findElement(By.xpath(FULL_NAME_FIELD)).sendKeys(TEST_FULL_NAME);
        driver.findElement(By.id(SIGN_UP_BUTTON)).click();

        String actualErrorMessage = driver.findElement(By.xpath("//*[@class='error']")).getText().trim();
        Assert.assertEquals(expectedErrorMessage, actualErrorMessage, "Error message is not as expected!");

        driver.get(driver.getCurrentUrl());}
}


