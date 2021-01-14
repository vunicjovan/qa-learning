package com.wedoqa.qalearning.e2e.generics;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    /**
     * HOME_URL - login page at SwagLabs website
     * driver - web driver used for e2e testing
     */
    protected  static String HOME_URL = "https://www.saucedemo.com";
    protected static WebDriver driver;

    /**
     * Initialization for every test class:
     * setting up web driver property on chosen relative path
     * initializing Selenium web driver
     * setting up different options of Selenium web driver
     */
    @BeforeAll
    public static void setUp() {
        setUpTheDriver(DriverManagerType.CHROME);
    }

    /**
     * Cleanup method for Selenium web driver, disposing all cookies
     */
    @AfterEach
    public void cleanUp() {
        driver.manage().deleteAllCookies();
    }

    /**
     * One of the most important parts of Selenium web driver lifecycle: dispose after usage.
     * If called driver.close() instead of driver.quit(), it would eventually lead to possible memory leak.
     */
    @AfterAll
    public static void tearDown() {
        if (driver != null)
            driver.quit();
    }

    /**
     * Helper method for Selenium web driver initialization.
     */
    private static void setUpTheDriver(DriverManagerType driverManagerType) {
        WebDriverManager.getInstance(driverManagerType).setup();
        try {
            Class<?> driverClass = Class.forName(driverManagerType.browserClass());
            driver = (WebDriver) driverClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.manage().window().maximize();    // maximize browser window
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);    // wait 10 seconds max for element to appear
    }

    /**
     * Make web driver obtain provided URL path.
     * @param appendable
     */
    public void goToUrl(String appendable) {
        driver.get(HOME_URL + appendable);
    }

}
