package com.wedoqa.qalearning.e2e.tests;

import com.wedoqa.qalearning.e2e.generics.BaseGridTest;
import com.wedoqa.qalearning.e2e.pages.LoginPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HealthCheck extends BaseGridTest {

    public HealthCheck() {
        super(new ChromeOptions().addArguments("headless"));
    }

    @Test
    public void testCheckHealth() {
        goToUrl("/");
        LoginPage loginPage = new LoginPage(getDriver());

        assertTrue(loginPage.checkHealth());
    }

}
