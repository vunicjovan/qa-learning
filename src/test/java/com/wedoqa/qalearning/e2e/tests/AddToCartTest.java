package com.wedoqa.qalearning.e2e.tests;

import com.wedoqa.qalearning.e2e.generics.BaseTest;
import com.wedoqa.qalearning.e2e.pages.InventoryPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddToCartTest extends BaseTest {

    @Test
    public void testAddToCart() {
        goToUrl("/inventory.html");
        InventoryPage inventoryPage = new InventoryPage(driver);

        assertEquals("$29.99", inventoryPage.getFirstShopItemPrice());

        inventoryPage.addToCart();

        assertTrue(inventoryPage.areCartCounterBadgeAndRemoveButtonDisplayed());
    }

}
