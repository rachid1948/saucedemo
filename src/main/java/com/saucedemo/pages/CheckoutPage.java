package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;

public class CheckoutPage extends BasePage {
    private final By firstName  = By.id("first-name");
    private final By lastName   = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueBtn= By.id("continue");
    private final By cancelBtn  = By.id("cancel");
    private final By errorMsg   = By.cssSelector("[data-test='error']");
    private final By itemTotal  = By.cssSelector(".summary_subtotal_label");
    private final By total      = By.cssSelector(".summary_total_label");
    private final By finishBtn  = By.id("finish");
    private final By confirmH   = By.cssSelector(".complete-header");
    private final By confirmT   = By.cssSelector(".complete-text");
    private final By backBtn    = By.id("back-to-products");

    public CheckoutPage fillCustomerInfo(String fn, String ln, String zip) {
        logger.info("Fill info: {} {}", fn, ln);
        jsSetValue(firstName, fn);
        jsSetValue(lastName, ln);
        jsSetValue(postalCode, zip);
        return this;
    }

    public CheckoutPage clickContinue() {
        jsClick(continueBtn);
        return this;  // ← supprime le wait.until(...)
    }

    public CartPage cancelCheckout() {
        jsClick(cancelBtn);
        wait.until(d -> d.getCurrentUrl().contains("/cart.html"));
        return new CartPage();
    }

    public CheckoutPage proceedWithInfo(String fn, String ln, String zip) {
        return fillCustomerInfo(fn, ln, zip).clickContinue();
    }

    public CheckoutPage clickFinish() { jsClick(finishBtn); return this;}
    public InventoryPage backToHome()    { click(backBtn);    return new InventoryPage(); }

    public boolean isOnCheckoutStep1()    { return getCurrentUrl().contains("checkout-step-one"); }
    public boolean isOnCheckoutStep2() {
        try { waitForVisible(itemTotal); return getCurrentUrl().contains("checkout-step-two"); }
        catch (Exception e) { return false; }
    }
    public boolean isOnConfirmationPage() {
        try { waitForVisible(confirmH); return getCurrentUrl().contains("checkout-complete"); }
        catch (Exception e) { return false; }
    }
    public boolean isErrorDisplayed() {
        try { waitForVisible(errorMsg); return true; }
        catch (Exception e) { return false; }
    }
    public String getErrorMessage()  { return getText(errorMsg); }
    public String getConfirmHeader() { return getText(confirmH); }
    public String getConfirmText()   { return getText(confirmT); }
    public String getItemTotal()     { return getText(itemTotal); }
    public String getTotalLabel()    { return getText(total); }
}