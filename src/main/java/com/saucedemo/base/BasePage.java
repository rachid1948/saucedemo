package com.saucedemo.base;

import com.saucedemo.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitForVisible(By l)   { return wait.until(ExpectedConditions.visibilityOfElementLocated(l)); }
    protected WebElement waitForClickable(By l) { return wait.until(ExpectedConditions.elementToBeClickable(l)); }

    protected void click(By l) {

        logger.debug("click => {}", l);
        waitForClickable(l).click();
    }

    // IMPORTANT : el.click() avant clear() pour déclencher les events React
    protected void type(By l, String t) {
        logger.debug("type ['{}'] => {}", t, l);
        WebElement e = waitForClickable(l);
        e.click();
        e.clear();
        e.sendKeys(t);
    }

    protected String  getText(By l)     { return waitForVisible(l).getText(); }
    protected boolean isDisplayed(By l) {
        try { return driver.findElement(l).isDisplayed(); }
        catch (NoSuchElementException e) { return false; }
    }
    protected void navigateTo(String url) { logger.info("goto {}", url); driver.get(url); }
    public String getCurrentUrl() { return driver.getCurrentUrl(); }
    public String getTitle()      { return driver.getTitle(); }
    public byte[] takeScreenshot() { return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES); }
}