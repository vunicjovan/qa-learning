package com.wedoqa.qalearning.e2e.tests;

import com.wedoqa.qalearning.e2e.generics.BaseTest;
import com.wedoqa.qalearning.e2e.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HealthCheckTest extends BaseTest {

    @Test
    public void testCheckHealth() {
        goToUrl("/");
        LoginPage loginPage = new LoginPage(driver);

        assertTrue(loginPage.checkHealth());
    }

}
