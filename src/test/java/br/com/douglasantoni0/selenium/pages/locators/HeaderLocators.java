package br.com.douglasantoni0.selenium.pages.locators;

import org.openqa.selenium.By;

public final class HeaderLocators {
    public static final By REGISTER_LINK = By.cssSelector("a.ico-register");
    public static final By LOGIN_LINK = By.cssSelector("a.ico-login");
    public static final By LOGOUT_LINK = By.cssSelector("a.ico-logout");
    public static final By ACCOUNT_LINK = By.cssSelector("a.account");
    public static final By CART_LINK = By.cssSelector("a.ico-cart");
    public static final By CART_QUANTITY = By.cssSelector(".header-links .cart-qty");
    public static final By BOOKS_MENU_LINK = By.cssSelector(".top-menu a[href='/books']");
    public static final By CONTACT_US_FOOTER_LINK = By.cssSelector(".footer a[href='/contactus']");

    private HeaderLocators() {
    }
}
