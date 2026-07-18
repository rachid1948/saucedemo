package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private CheckoutPage reachCheckoutStep1() {
        return loginPage.loginAs(getValidUsername(), getValidPassword())
                .addFirstProductToCart()
                .goToCart()
                .proceedToCheckout(); // attend first-name visible
    }

    private CheckoutPage reachStep2() {
        return reachCheckoutStep1().proceedWithInfo("Rachid", "Chaouky", "20250");
    }

    @Test(description = "Commande complete de bout en bout")
    public void testCompleteCheckout() {
        CheckoutPage c = reachStep2();
        Assert.assertTrue(c.isOnCheckoutStep2(), "Doit etre sur step 2");
        c.clickFinish();
        Assert.assertTrue(c.isOnConfirmationPage(), "Doit etre sur la confirmation");
        Assert.assertEquals(c.getConfirmHeader(), "Thank you for your order!");
    }

    @Test(description = "Prenom manquant => erreur")
    public void testMissingFirstName() {
        CheckoutPage c = reachCheckoutStep1();
        Assert.assertTrue(c.isOnCheckoutStep1(), "Doit etre sur step 1");
        c.fillCustomerInfo("", "Chaouky", "20250").clickContinue();
        Assert.assertTrue(c.isErrorDisplayed(), "Erreur attendue si prenom vide");
        Assert.assertTrue(c.getErrorMessage().contains("First Name is required"));
    }

    @Test(description = "Resume affiche le montant total")
    public void testOrderSummary() {
        CheckoutPage c = reachStep2();
        Assert.assertTrue(c.isOnCheckoutStep2(), "Doit etre sur step 2");
        Assert.assertFalse(c.getItemTotal().isEmpty(), "Sous-total doit s'afficher");
        Assert.assertFalse(c.getTotalLabel().isEmpty(), "Total doit s'afficher");
    }

    @Test(description = "Annulation retourne au panier")
    public void testCancelCheckout() {
        CartPage cart = reachCheckoutStep1().cancelCheckout();
        Assert.assertTrue(cart.isOnCartPage(), "Doit revenir sur le panier apres annulation");
    }
}