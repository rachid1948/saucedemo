package com.saucedemo.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.ConfigReader;
import com.saucedemo.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.util.Base64;

/** BaseTest - Setup/Teardown + ExtentReports + Screenshot sur echec. */
public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected LoginPage loginPage;
    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @BeforeSuite
    public void setUpReport() {
        ExtentSparkReporter r = new ExtentSparkReporter("reports/ExtentReport.html");
        r.config().setDocumentTitle("SauceDemo Tests");
        r.config().setReportName("Selenium POM");
        extent = new ExtentReports();
        extent.attachReporter(r);
    }

    @AfterSuite
    public void tearDownReport() { if (extent != null) extent.flush(); }

    @BeforeMethod
    public void setUp(java.lang.reflect.Method m) {
        logger.info("===== START: {} =====", m.getName());
        test.set(extent.createTest(m.getName()));
        DriverManager.initDriver();
        loginPage = new LoginPage().open();
    }

    @AfterMethod
    public void tearDown(ITestResult r) {
        if (r.getStatus() == ITestResult.FAILURE) {
            String b64 = Base64.getEncoder().encodeToString(loginPage.takeScreenshot());
            test.get().fail(r.getThrowable()).addScreenCaptureFromBase64String(b64, "Echec");
        } else if (r.getStatus() == ITestResult.SUCCESS) test.get().pass("OK");
        else test.get().skip("Skip");
        DriverManager.quitDriver();
    }

    protected String getValidUsername()  { return ConfigReader.get("valid.username",  "standard_user"); }
    protected String getValidPassword()  { return ConfigReader.get("valid.password",  "secret_sauce"); }
    protected String getLockedUsername() { return ConfigReader.get("locked.username", "locked_out_user"); }
}
