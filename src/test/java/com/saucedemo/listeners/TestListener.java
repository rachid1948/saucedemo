package com.saucedemo.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final Logger log = LogManager.getLogger(TestListener.class);
    public void onTestStart(ITestResult r)   { log.info(">> START  {}", r.getName()); }
    public void onTestSuccess(ITestResult r)  { log.info(">> PASS   {}", r.getName()); }
    public void onTestFailure(ITestResult r)  { log.error(">> FAIL   {}", r.getName()); }
    public void onTestSkipped(ITestResult r)  { log.warn(">> SKIP   {}", r.getName()); }
}
