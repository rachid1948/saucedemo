package com.saucedemo.tests;

import com.saucedemo.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

/** 5 tests de la page catalogue */
public class InventoryTest extends BaseTest {

    @Test(description = "6 produits affiches")
    public void testProductCount() {
        InventoryPage inv = loginPage.loginAs(getValidUsername(), getValidPassword());
        Assert.assertEquals(inv.getProductCount(), 6);
    }

    @Test(description = "Ajout d un produit incremente le badge a 1")
    public void testAddOneProduct() {
        InventoryPage inv = loginPage.loginAs(getValidUsername(), getValidPassword());
        Assert.assertEquals(inv.getCartBadgeCount(), 0);
        inv.addFirstProductToCart();
        Assert.assertEquals(inv.getCartBadgeCount(), 1);
    }

    @Test(description = "Ajout de 3 produits => badge = 3")
    public void testAddThreeProducts() {
        InventoryPage inv = loginPage.loginAs(getValidUsername(), getValidPassword());
        inv.addProductsToCart(3);
        Assert.assertEquals(inv.getCartBadgeCount(), 3);
    }

    @Test(description = "Tri prix croissant")
    public void testSortPriceAsc() {
        InventoryPage inv = loginPage.loginAs(getValidUsername(), getValidPassword());
        inv.sortBy("lohi");
        List<String> p = inv.getProductPrices();
        double prev = 0;
        for (String s : p) {
            double v = Double.parseDouble(s.replace("$",""));
            Assert.assertTrue(v >= prev);
            prev = v;
        }
    }

    @Test(description = "Tri alphabetique Z-A")
    public void testSortNameDesc() {
        InventoryPage inv = loginPage.loginAs(getValidUsername(), getValidPassword());
        inv.sortBy("za");
        List<String> n = inv.getProductNames();
        for (int i=0; i<n.size()-1; i++)
            Assert.assertTrue(n.get(i).compareToIgnoreCase(n.get(i+1)) >= 0);
    }
}
