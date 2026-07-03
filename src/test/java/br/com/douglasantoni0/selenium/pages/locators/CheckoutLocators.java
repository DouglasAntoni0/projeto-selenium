package br.com.douglasantoni0.selenium.pages.locators;

import org.openqa.selenium.By;

public final class CheckoutLocators {
    public static final By CHECKOUT_PROGRESS = By.cssSelector(".checkout-progress");
    public static final By BILLING_STEP = By.id("checkout-step-billing");
    public static final By BILLING_ADDRESS_SELECT = By.id("billing-address-select");
    public static final By BILLING_COUNTRY = By.id("BillingNewAddress_CountryId");
    public static final By BILLING_CITY = By.id("BillingNewAddress_City");
    public static final By BILLING_ADDRESS_LINE = By.id("BillingNewAddress_Address1");
    public static final By BILLING_POSTAL_CODE = By.id("BillingNewAddress_ZipPostalCode");
    public static final By BILLING_PHONE = By.id("BillingNewAddress_PhoneNumber");
    public static final By BILLING_CONTINUE = By.cssSelector("#billing-buttons-container input.button-1");

    public static final By SHIPPING_ADDRESS_STEP = By.id("checkout-step-shipping");
    public static final By SHIPPING_CONTINUE = By.cssSelector("#shipping-buttons-container input.button-1");

    public static final By SHIPPING_METHOD_STEP = By.id("checkout-step-shipping-method");
    public static final By GROUND_SHIPPING_METHOD = By.id("shippingoption_0");
    public static final By SHIPPING_METHOD_CONTINUE = By.cssSelector("#shipping-method-buttons-container input.button-1");

    public static final By PAYMENT_METHOD_STEP = By.id("checkout-step-payment-method");
    public static final By CASH_ON_DELIVERY = By.id("paymentmethod_0");
    public static final By PAYMENT_METHOD_CONTINUE = By.cssSelector("#payment-method-buttons-container input.button-1");

    public static final By PAYMENT_INFO_STEP = By.id("checkout-step-payment-info");
    public static final By PAYMENT_INFO_CONTINUE = By.cssSelector("#payment-info-buttons-container input.button-1");

    public static final By CONFIRM_ORDER_STEP = By.id("checkout-step-confirm-order");
    public static final By CONFIRM_PRODUCT_NAME = By.cssSelector("#checkout-step-confirm-order .cart .product-name");
    public static final By CONFIRM_ORDER_TOTAL = By.cssSelector("#checkout-step-confirm-order .cart-total .order-total .product-price");
    public static final By CONFIRM_ORDER_CONTINUE = By.cssSelector("#confirm-order-buttons-container input.button-1");

    public static final By ORDER_COMPLETED = By.cssSelector(".order-completed");
    public static final By ORDER_NUMBER = By.cssSelector(".order-number");
    public static final By CONTINUE_AFTER_ORDER = By.cssSelector(".order-completed-continue-button");

    private CheckoutLocators() {
    }
}
