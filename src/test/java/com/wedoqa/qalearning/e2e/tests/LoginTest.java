package com.wedoqa.qalearning.e2e.tests;

import com.wedoqa.qalearning.e2e.generics.BaseTest;
import com.wedoqa.qalearning.e2e.pages.InventoryPage;
import com.wedoqa.qalearning.e2e.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {

    @ParameterizedTest(name = "username = {0}")
    @ValueSource(strings = { "standard_user", "problem_user", "performance_glitch_user" })
    public void testLoginSuccess(String username) {
        goToUrl("/");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.fillLoginForm(username, "secret_sauce");

        InventoryPage inventoryPage = loginPage.login();

        assertEquals("Sauce Labs Backpack", inventoryPage.getFirstShopItem());
    }

    @ParameterizedTest(name = "username = {0}")
    @ValueSource(strings = { "locked_out_user" })
    public void testLoginFailure(String username) {
        goToUrl("/");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.fillLoginForm(username, "secret_sauce");

        Exception exception = assertThrows(NoSuchElementException.class, loginPage::login);
        assertTrue(exception.getMessage().contains("Unable to locate element"));
    }

}
