package com.wedoqa.qalearning.e2e.pages;

import com.wedoqa.qalearning.e2e.generics.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryPage extends PageObject {

    @FindBy(className = "product_sort_container")
    private WebElement sortSelect;

    @FindBy(id = "item_4_title_link")
    private WebElement firstShopItem;

    @FindBy(className = "inventory_item_price")
    private WebElement firstShopItemPrice;

    @FindBy(xpath = "//*[ text() = 'ADD TO CART' ]")
    private WebElement firstShopItemAddToCartButton;

    /**
     * The PageFactory processes all the annotated WebElements and locates the element on the
     * page using the annotated selector.
     *
     * @param driver
     */
    public InventoryPage(WebDriver driver) {
        super(driver);
        assertTrue(sortSelect.isDisplayed());
    }

    /**
     *
     * @return Text content representing first shop item
     */
    public String getFirstShopItem() {
        return this.firstShopItem.getText();
    }

    /**
     *
     * @return Text content representing first shop item price
     */
    public String getFirstShopItemPrice() {
        return this.firstShopItemPrice.getText();
    }

    /**
     * Check if item is added to the cart.
     * In case it is, cart counter badge appears, while ADD_TO_CART button becomes REMOVE button.
     * @return Boolean areBadgeAndButtonDisplayed
     */
    public Boolean areCartCounterBadgeAndRemoveButtonDisplayed() {
        try {
            WebElement shoppingCartCounterBadge = driver.findElement(By.className("shopping_cart_badge"));
            WebElement removeButton = driver.findElement(By.xpath("//*[ text() = 'REMOVE' ]"));

            return shoppingCartCounterBadge.isDisplayed() && removeButton.isDisplayed();
        }
        catch (Exception e) {
            try {
                screenshot(driver, e.getClass().getSimpleName(), e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Add first shop item to the buyer's cart.
     */
    public void addToCart() {
        this.firstShopItemAddToCartButton.click();
    }

}
