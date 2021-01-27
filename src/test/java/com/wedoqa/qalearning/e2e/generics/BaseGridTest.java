package com.wedoqa.qalearning.e2e.generics;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runners.Parameterized;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseGridTest {

    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public WebDriver getDriver() {
        return driver.get();
    }

    public MutableCapabilities capabilities;

    public BaseGridTest(MutableCapabilities capabilities) {
        this.capabilities = capabilities;
    }

    /**
     * HOME_URL - login page at SwagLabs website
     */
    protected static String HOME_URL = "https://www.saucedemo.com";

    @BeforeEach
    public void setUpTheTest() throws MalformedURLException {
        RemoteWebDriver webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.set(webDriver);
    }

    @AfterEach
    public void cleanUp() {
        getDriver().quit();
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null)
            driver.remove();
    }

    /**
     * Make web driver obtain provided URL path.
     * @param appendable = HOME_URL attachment
     */
    public void goToUrl(String appendable) {
        getDriver().get(HOME_URL + appendable);
    }

}
