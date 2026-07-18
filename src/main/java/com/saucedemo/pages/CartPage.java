package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CartPage extends BasePage {
    private final By cartItems  = By.cssSelector(".cart_item");
    private final By cartNames  = By.cssSelector(".inventory_item_name");
    private final By cartPrices = By.cssSelector(".inventory_item_price");
    private final By continueBtn= By.id("continue-shopping");
    private final By checkoutBtn= By.id("checkout");
    private final By removeBtns = By.cssSelector("button[id^='remove']");

    public CheckoutPage proceedToCheckout() {
        wait.until(d -> d.getCurrentUrl().contains("/cart.html")); // sécurité
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector('#checkout').click();"
        );
        waitForVisible(By.id("first-name"));
        return new CheckoutPage();
    }

    public InventoryPage continueShopping() {
        ((JavascriptExecutor) driver).executeScript(
                "document.getElementById('continue-shopping').click();"
        );
        waitForVisible(By.cssSelector(".inventory_item"));
        return new InventoryPage();
    }

    public CartPage removeFirstItem() {
        List<WebElement> btns = driver.findElements(removeBtns);
        if (!btns.isEmpty()) btns.get(0).click();
        return this;
    }

    public boolean isOnCartPage() {
        try { waitForVisible(checkoutBtn); return getCurrentUrl().contains("/cart.html"); }
        catch (Exception e) { return false; }
    }
    public boolean      isCartEmpty()       { return driver.findElements(cartItems).isEmpty(); }
    public int          getCartItemCount()  { return driver.findElements(cartItems).size(); }
    public List<String> getCartItemNames()  { return driver.findElements(cartNames).stream().map(WebElement::getText).toList(); }
    public List<String> getCartItemPrices() { return driver.findElements(cartPrices).stream().map(WebElement::getText).toList(); }
}