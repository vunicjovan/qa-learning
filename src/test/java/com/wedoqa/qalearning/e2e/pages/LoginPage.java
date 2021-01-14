package com.wedoqa.qalearning.e2e.pages;

import com.wedoqa.qalearning.e2e.generics.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPage extends PageObject {

    /**
     * Page elements relevant for the testing process.
     */
    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    /**
     * The PageFactory processes all the annotated WebElements and locates the element on the
     * page using the annotated selector.
     *
     * @param driver
     */
    public LoginPage(WebDriver driver) {
        super(driver);
        assertTrue(usernameInput.isDisplayed());
        assertTrue(passwordInput.isDisplayed());
        assertTrue(loginButton.isDisplayed());
    }

    /**
     * Simple health check method.
     * @return Boolean checkHealth
     */
    public Boolean checkHealth() {
        return usernameInput.isDisplayed() && passwordInput.isDisplayed();
    }

    /**
     * Fill up the sign-in data required for login action.
     * @param username
     * @param password
     */
    public void fillLoginForm(String username, String password) {
        clearInput(this.usernameInput);
        this.usernameInput.sendKeys(username);

        clearInput(this.passwordInput);
        this.passwordInput.sendKeys(password);
    }

    /**
     * Sign in to the SwagLabs web shop.
     * @return new InventoryPage(driver)
     */
    public InventoryPage login() {
        this.loginButton.click();

        return new InventoryPage(driver);
    }

}
