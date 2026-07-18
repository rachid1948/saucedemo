package com.saucedemo.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/** DriverManager - ThreadLocal WebDriver (compatible parallelisme). */
public class DriverManager {
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> tl = new ThreadLocal<>();
    private DriverManager() {}

    public static void initDriver() {
        String browser   = ConfigReader.get("browser", "chrome");
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless", "false"));
        WebDriver d;
        switch (browser.toLowerCase()) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions o = new FirefoxOptions();
                if (headless) o.addArguments("-headless");
                d = new FirefoxDriver(o);
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions o = new ChromeOptions();
                if (headless) o.addArguments("--headless","--no-sandbox","--disable-dev-shm-usage");
                o.addArguments("--start-maximized");
                d = new ChromeDriver(o);
            }
        }
        tl.set(d);
        logger.info("Driver {} pret (headless={}).", browser, headless);
    }

    public static WebDriver getDriver() {
        if (tl.get() == null) throw new IllegalStateException("Appelez initDriver() d'abord.");
        return tl.get();
    }
    public static void quitDriver() {
        if (tl.get() != null) { tl.get().quit(); tl.remove(); logger.info("Driver ferme."); }
    }
}
