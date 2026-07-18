# Selenium POM Project - SauceDemo

Tests automatises avec Selenium 4 + TestNG + POM.
Site : https://www.saucedemo.com

## Structure
```
saucedemo/
|-- pom.xml
|-- src/main/java/com/saucedemo/
|   |-- base/BasePage.java         <- classe mere
|   |-- utils/DriverManager.java   <- gestion WebDriver (ThreadLocal)
|   |-- utils/ConfigReader.java    <- lecture config.properties
|   |-- pages/
|       |-- LoginPage.java
|       |-- InventoryPage.java
|       |-- CartPage.java
|       +-- CheckoutPage.java
|-- src/test/java/com/saucedemo/
|   |-- tests/
|   |   |-- BaseTest.java          <- setup + ExtentReports
|   |   |-- LoginTest.java         <- 5 tests
|   |   |-- InventoryTest.java     <- 5 tests
|   |   |-- CartTest.java          <- 4 tests
|   |   +-- CheckoutTest.java      <- 4 tests
|   +-- listeners/TestListener.java
|-- src/test/resources/
|   |-- testng.xml
|   |-- config.properties
|   +-- log4j2.xml
|-- reports/                        <- rapport HTML genere ici
+-- logs/
```

## Comptes SauceDemo
| User | Password | Note |
|---|---|---|
| standard_user | secret_sauce | Normal |
| locked_out_user | secret_sauce | Verrouille |
| problem_user | secret_sauce | Bugs UI |
| performance_glitch_user | secret_sauce | Lent |

## Lancer les tests
```bash
mvn clean test                    # tous les tests
mvn clean test -Dheadless=true   # sans navigateur graphique
mvn clean test -Dbrowser=firefox # avec Firefox
```
Rapport : `reports/ExtentReport.html`
