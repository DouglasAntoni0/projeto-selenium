package br.com.douglasantoni0.selenium.pages.locators;

import org.openqa.selenium.By;

public final class ProductDetailsLocators {
    public static final By PRODUCT_NAME = By.cssSelector(".product-name h1");
    public static final By PRODUCT_PRICE = By.cssSelector(".product-price span");
    public static final By GIFT_RECIPIENT_NAME = By.id("giftcard_2_RecipientName");
    public static final By GIFT_RECIPIENT_EMAIL = By.id("giftcard_2_RecipientEmail");
    public static final By GIFT_SENDER_NAME = By.id("giftcard_2_SenderName");
    public static final By GIFT_SENDER_EMAIL = By.id("giftcard_2_SenderEmail");
    public static final By GIFT_MESSAGE = By.id("giftcard_2_Message");
    public static final By QUANTITY = By.id("addtocart_2_EnteredQuantity");
    public static final By ADD_TO_CART_BUTTON = By.id("add-to-cart-button-2");

    private ProductDetailsLocators() {
    }
}
