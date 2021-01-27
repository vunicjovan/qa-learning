package com.wedoqa.qalearning.e2e.tests;

import com.wedoqa.qalearning.e2e.generics.BaseGridTest;
import com.wedoqa.qalearning.e2e.pages.InventoryPage;
import com.wedoqa.qalearning.e2e.pages.LoginPage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class Login extends BaseGridTest {

    public Login() {
        super(new ChromeOptions());
    }

    @ParameterizedTest(name = "username = {0}")
    @ValueSource(strings = { "standard_user", "problem_user", "performance_glitch_user" })
    public void testLoginSuccess(String username) {
        goToUrl("/");
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.fillLoginForm(username, "secret_sauce");

        InventoryPage inventoryPage = loginPage.login();

        assertEquals("Sauce Labs Backpack", inventoryPage.getFirstShopItem());
    }

    @ParameterizedTest(name = "username = {0}")
    @ValueSource(strings = { "locked_out_user" })
    public void testLoginFailure(String username) {
        goToUrl("/");
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.fillLoginForm(username, "secret_sauce");

        Exception exception = assertThrows(NoSuchElementException.class, loginPage::login);
        assertTrue(exception.getMessage().contains("Unable to locate element"));
    }

}
