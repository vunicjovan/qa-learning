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
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

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
    public static void screenshot(WebDriver driver, String exceptionName, Exception e) throws Exception {
        //Convert web driver object to TakeScreenshot
        TakesScreenshot screenshotTaker = (TakesScreenshot) driver;
        //Call getScreenshotAs method to create image file
        File sourceFile = screenshotTaker.getScreenshotAs(OutputType.FILE);
        //Move image file to new destination
        String path = generateScreenshotName(exceptionName);
        File destinationFile = new File(path + ".png");
        //Copy file at destination
        FileUtils.copyFile(sourceFile, destinationFile);
        // Compile error report
        compileErrorReport(path + ".txt", e);
    }

    public static String generateScreenshotName(String exceptionName) {
        StringBuilder builder = new StringBuilder(SCREENSHOT_PATH);
        builder.append(dateTimeFormatter.format(LocalDateTime.now()) + "/")
                .append(exceptionName)
                .append("-")
                .append(dateTimeFormatter.format(LocalDateTime.now()));

        return builder.toString();
    }

    /**
     * Compile an error report in the same folder as error screenshot and with the same name.
     * @param title
     * @param e
     */
    public static void compileErrorReport(String title, Exception e) {
        File file = new File(title);
        PrintStream ps = null;
        try {
            ps = new PrintStream(file);
            e.printStackTrace(ps);
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
