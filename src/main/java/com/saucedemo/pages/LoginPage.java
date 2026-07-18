package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    private static final String URL = "https://www.saucedemo.com";
    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginBtn = By.id("login-button");
    private final By errorMsg = By.cssSelector("[data-test='error']");
    private final By logo     = By.cssSelector(".login_logo");

    public LoginPage open() { navigateTo(URL); return this; }

    public InventoryPage loginAs(String u, String p) {
        logger.info("Login: {}", u);
        type(username, u); type(password, p); click(loginBtn);
        return new InventoryPage();
    }

    public LoginPage loginWithBadCredentials(String u, String p) {
        type(username, u); type(password, p); click(loginBtn);
        return this;
    }

    public boolean isOnLoginPage() {
        try { waitForVisible(logo); return true; }
        catch (Exception e) { return false; }
    }
    public boolean isErrorDisplayed() {
        try { waitForVisible(errorMsg); return true; }
        catch (Exception e) { return false; }
    }
    public String getErrorMessage() { return getText(errorMsg); }
}