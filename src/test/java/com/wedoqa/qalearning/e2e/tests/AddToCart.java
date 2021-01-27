package com.wedoqa.qalearning.e2e.tests;

import com.wedoqa.qalearning.e2e.generics.BaseGridTest;
import com.wedoqa.qalearning.e2e.pages.InventoryPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddToCart extends BaseGridTest {

    public AddToCart() {
        super(new ChromeOptions());
    }

    @Test
    public void testAddToCart() {
        goToUrl("/inventory.html");
        InventoryPage inventoryPage = new InventoryPage(getDriver());

        assertEquals("$29.99", inventoryPage.getFirstShopItemPrice());

        inventoryPage.addToCart();

        assertTrue(inventoryPage.areCartCounterBadgeAndRemoveButtonDisplayed());
    }

}
