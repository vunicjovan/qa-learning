package com.wedoqa.qalearning.e2e.generics;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class PageObject {

    private static final String SCREENSHOT_PATH = "./src/test/screenshots/";
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH-mm-ss-SSS");
    protected WebDriver driver;

    /**
     * The PageFactory processes all the annotated WebElements and locates the element on the
     * page using the annotated selector.
     */
    public PageObject(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Helper method for element presence check.
     * driver = driver used for testing
     * by = selector used for element retrieval
     * @return boolean doesElementExist
     */
    public boolean doesElementExist(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch(NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Helper method for input field clearing, considered as a workaround, especially for Chrome.
     * Reference: https://github.com/SeleniumHQ/selenium/issues/6741
     */
    public void clearInput(WebElement element) {
        element.clear();
        element.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
    }

    /**
     * Take a screenshot of a page every time when test fails.
     * @param driver
     * @param exceptionName
     * @throws Exception
     */
    public static void screenshot(WebDriver driver, String exceptionName) throws Exception{
        //Convert web driver object to TakeScreenshot
        TakesScreenshot screenshotTaker = (TakesScreenshot) driver;
        //Call getScreenshotAs method to create image file
        File sourceFile = screenshotTaker.getScreenshotAs(OutputType.FILE);
        //Move image file to new destination
        File destinationFile = new File(generateScreenshotName(exceptionName));
        //Copy file at destination
        FileUtils.copyFile(sourceFile, destinationFile);
    }

    public static String generateScreenshotName(String exceptionName) {
        StringBuilder builder = new StringBuilder(SCREENSHOT_PATH);
        builder.append(exceptionName)
                .append("-")
                .append(dateTimeFormatter.format(LocalDateTime.now()));

        return builder.toString();
    }

}
