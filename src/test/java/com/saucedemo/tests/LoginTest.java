package com.saucedemo.tests;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Connexion valide => /inventory.html")
    public void testValidLogin() {
        InventoryPage inv = loginPage.loginAs(getValidUsername(), getValidPassword());
        Assert.assertTrue(inv.isOnInventoryPage());
        Assert.assertEquals(inv.getPageTitle(), "Products");
    }

    @Test(description = "Mauvais mot de passe => erreur")
    public void testInvalidPassword() {
        loginPage.loginWithBadCredentials(getValidUsername(), "wrong");
        Assert.assertTrue(loginPage.isErrorDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"));
    }

    @Test(description = "Champs vides => erreur")
    public void testEmptyCredentials() {
        loginPage.loginWithBadCredentials("", "");
        Assert.assertTrue(loginPage.isErrorDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"));
    }

    @Test(description = "Compte verrouille => erreur")
    public void testLockedOutUser() {
        loginPage.loginWithBadCredentials(getLockedUsername(), getValidPassword());
        Assert.assertTrue(loginPage.isErrorDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"));
    }

    @Test(description = "Deconnexion => retour page login")
    public void testLogout() {
        // UTILISER le LoginPage RETOURNE par logout() -- PAS l'ancien loginPage !
        LoginPage result = loginPage.loginAs(getValidUsername(), getValidPassword()).logout();
        Assert.assertTrue(result.isOnLoginPage(), "Doit etre sur la page login apres deconnexion");
    }
}