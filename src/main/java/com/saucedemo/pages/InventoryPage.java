package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class InventoryPage extends BasePage {
    private final By title      = By.cssSelector(".title");
    private final By items      = By.cssSelector(".inventory_item");
    private final By names      = By.cssSelector(".inventory_item_name");
    private final By prices     = By.cssSelector(".inventory_item_price");
    private final By addBtns    = By.cssSelector("button[id^='add-to-cart']");
    private final By badge      = By.cssSelector(".shopping_cart_badge");
    private final By cartIcon   = By.cssSelector(".shopping_cart_link");
    private final By sortDrop   = By.cssSelector("[data-test='product-sort-container']");
    private final By burgerMenu = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public InventoryPage addFirstProductToCart() {
        waitForClickable(addBtns);
        List<WebElement> btns = driver.findElements(addBtns);
        if (!btns.isEmpty()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btns.get(0));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btns.get(0));
            // Attendre que le bouton devienne "Remove" — confirme que l'ajout est effectif
            wait.until(d -> !d.findElements(By.cssSelector("button[id^='remove']")).isEmpty());
            logger.info("Produit 1 ajouté.");
        }
        return this;
    }

    public InventoryPage addProductsToCart(int n) {
        waitForClickable(addBtns);
        List<WebElement> btns = driver.findElements(addBtns);
        for (int i = 0; i < Math.min(n, btns.size()); i++) {
            btns = driver.findElements(addBtns); // re-fetch après chaque clic
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btns.get(i));
            int finalI = i;
            wait.until(d -> d.findElements(By.cssSelector("button[id^='remove']")).size() > finalI);
        }
        return this;
    }

    public CartPage goToCart() {
        click(cartIcon);
        wait.until(d -> d.getCurrentUrl().contains("/cart.html")); // ← attendre la navigation
        return new CartPage();
    }

    public LoginPage logout() {
        click(burgerMenu);
        waitForVisible(logoutLink);

        // ✅ JS click — ignore l'animation du menu
        ((JavascriptExecutor) driver).executeScript(
                "document.getElementById('logout_sidebar_link').click();"
        );

        // ✅ Attend le changement d'URL plutôt que l'élément
        wait.until(d -> d.getCurrentUrl().equals("https://www.saucedemo.com/"));

        return new LoginPage();
    }

    public InventoryPage sortBy(String v) {
        new Select(driver.findElement(sortDrop)).selectByValue(v);
        return this;
    }

    public boolean isOnInventoryPage() { return getCurrentUrl().contains("/inventory.html"); }
    public String  getPageTitle()      { return getText(title); }
    public int     getProductCount()   { return driver.findElements(items).size(); }
    public int     getCartBadgeCount() { return isDisplayed(badge) ? Integer.parseInt(getText(badge)) : 0; }
    public List<String> getProductNames()  { return driver.findElements(names).stream().map(WebElement::getText).toList(); }
    public List<String> getProductPrices() { return driver.findElements(prices).stream().map(WebElement::getText).toList(); }
}