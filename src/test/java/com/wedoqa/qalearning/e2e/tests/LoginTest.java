package com.wedoqa.qalearning.e2e.tests;

import com.wedoqa.qalearning.e2e.generics.BaseTest;
import com.wedoqa.qalearning.e2e.pages.InventoryPage;
import com.wedoqa.qalearning.e2e.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest extends BaseTest {

    @Test
    public void testLogin() {
        goToUrl("/");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.fillLoginForm("standard_user", "secret_sauce");

        InventoryPage inventoryPage = loginPage.login();

        assertEquals("Sauce Labs Backpack", inventoryPage.getFirstShopItem());
    }

}
