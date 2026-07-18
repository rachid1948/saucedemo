package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

/** 4 tests du panier */
public class CartTest extends BaseTest {

    @Test(description = "Panier vide au premier acces")
    public void testEmptyCart() {
        CartPage cart = loginPage.loginAs(getValidUsername(), getValidPassword()).goToCart();
        Assert.assertTrue(cart.isOnCartPage());
        Assert.assertTrue(cart.isCartEmpty());
    }

    @Test(description = "Le produit ajoute apparait dans le panier")
    public void testProductInCart() {
        InventoryPage inv = loginPage.loginAs(getValidUsername(), getValidPassword());
        List<String> names = inv.getProductNames();
        inv.addFirstProductToCart();
        CartPage cart = inv.goToCart();
        Assert.assertEquals(cart.getCartItemCount(), 1);
        Assert.assertTrue(cart.getCartItemNames().contains(names.get(0)));
    }

    @Test(description = "Suppression d un article vide le panier")
    public void testRemoveItem() {
        InventoryPage inv = loginPage.loginAs(getValidUsername(), getValidPassword());
        inv.addFirstProductToCart();
        CartPage cart = inv.goToCart();
        cart.removeFirstItem();
        Assert.assertTrue(cart.isCartEmpty());
    }

    @Test(description = "Continuer les achats retourne sur /inventory")
    public void testContinueShopping() {
        InventoryPage inv = loginPage.loginAs(getValidUsername(), getValidPassword())
                                     .goToCart().continueShopping();
        Assert.assertTrue(inv.isOnInventoryPage());
    }
}
