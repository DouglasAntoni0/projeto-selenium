package br.com.douglasantoni0.selenium.pages.locators;

import org.openqa.selenium.By;

public final class CartLocators {
    public static final By PAGE_TITLE = By.cssSelector(".page-title h1");
    public static final By EMPTY_CART_MESSAGE = By.cssSelector(".order-summary-content");
    public static final By CART_ROWS = By.cssSelector(".cart tbody tr.cart-item-row");
    public static final By PRODUCT_NAME = By.cssSelector(".product-name");
    public static final By UNIT_PRICE = By.cssSelector(".product-unit-price");
    public static final By QUANTITY_INPUT = By.cssSelector("input.qty-input");
    public static final By SUBTOTAL = By.cssSelector(".product-subtotal");
    public static final By REMOVE_CHECKBOX = By.cssSelector("input[name='removefromcart']");
    public static final By UPDATE_CART_BUTTON = By.name("updatecart");
    public static final By TERMS_OF_SERVICE = By.id("termsofservice");
    public static final By CHECKOUT_BUTTON = By.id("checkout");
    public static final By TERMS_WARNING_DIALOG = By.cssSelector(".ui-dialog, #terms-of-service-warning-box");
    public static final By ORDER_TOTAL = By.cssSelector(".cart-total .product-price.order-total");

    private CartLocators() {
    }
}

