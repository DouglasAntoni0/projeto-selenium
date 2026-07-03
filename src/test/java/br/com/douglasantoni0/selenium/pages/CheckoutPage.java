package br.com.douglasantoni0.selenium.pages;

import br.com.douglasantoni0.selenium.config.EnvironmentConfig;
import br.com.douglasantoni0.selenium.model.Address;
import br.com.douglasantoni0.selenium.pages.locators.CheckoutLocators;
import br.com.douglasantoni0.selenium.support.assertions.MoneyParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.math.BigDecimal;

public class CheckoutPage extends BasePage {
    public CheckoutPage(WebDriver driver, EnvironmentConfig config) {
        super(driver, config);
    }

    public CheckoutPage fillBillingAddress(Address address) {
        visible(CheckoutLocators.BILLING_STEP);
        selectNewAddressIfAvailable();

        type(CheckoutLocators.BILLING_CITY, address.city());
        type(CheckoutLocators.BILLING_ADDRESS_LINE, address.addressLine());
        type(CheckoutLocators.BILLING_POSTAL_CODE, address.postalCode());
        type(CheckoutLocators.BILLING_PHONE, address.phoneNumber());
        selectByVisibleText(CheckoutLocators.BILLING_COUNTRY, address.country());

        // Cada botao do checkout dispara chamada AJAX e troca apenas um bloco da pagina.
        click(CheckoutLocators.BILLING_CONTINUE);
        waitUntilAjaxLoaderDisappears();
        waitForAnyVisible(CheckoutLocators.SHIPPING_ADDRESS_STEP, CheckoutLocators.SHIPPING_METHOD_STEP, CheckoutLocators.PAYMENT_METHOD_STEP);
        return this;
    }

    public CheckoutPage continueShippingAddressIfPresent() {
        if (isVisible(CheckoutLocators.SHIPPING_ADDRESS_STEP) && isVisible(CheckoutLocators.SHIPPING_CONTINUE)) {
            click(CheckoutLocators.SHIPPING_CONTINUE);
            waitUntilAjaxLoaderDisappears();
            waitForAnyVisible(CheckoutLocators.SHIPPING_METHOD_STEP, CheckoutLocators.PAYMENT_METHOD_STEP);
        }
        return this;
    }

    public CheckoutPage selectGroundShippingIfPresent() {
        if (isVisible(CheckoutLocators.SHIPPING_METHOD_STEP)) {
            WebElement ground = visible(CheckoutLocators.GROUND_SHIPPING_METHOD);
            if (!ground.isSelected()) {
                ground.click();
            }
            click(CheckoutLocators.SHIPPING_METHOD_CONTINUE);
            waitUntilAjaxLoaderDisappears();
            waitForAnyVisible(CheckoutLocators.PAYMENT_METHOD_STEP);
        }
        return this;
    }

    public CheckoutPage selectCashOnDelivery() {
        visible(CheckoutLocators.PAYMENT_METHOD_STEP);
        WebElement cashOnDelivery = visible(CheckoutLocators.CASH_ON_DELIVERY);
        if (!cashOnDelivery.isSelected()) {
            cashOnDelivery.click();
        }
        click(CheckoutLocators.PAYMENT_METHOD_CONTINUE);
        waitUntilAjaxLoaderDisappears();
        visible(CheckoutLocators.PAYMENT_INFO_STEP);
        return this;
    }

    public CheckoutPage continuePaymentInformation() {
        click(CheckoutLocators.PAYMENT_INFO_CONTINUE);
        waitUntilAjaxLoaderDisappears();
        visible(CheckoutLocators.CONFIRM_ORDER_STEP);
        return this;
    }

    public CheckoutPage confirmOrder() {
        click(CheckoutLocators.CONFIRM_ORDER_CONTINUE);
        waitUntilAjaxLoaderDisappears();
        visible(CheckoutLocators.ORDER_COMPLETED);
        return this;
    }

    public CheckoutPage completeOrder(Address address) {
        return fillBillingAddress(address)
                .continueShippingAddressIfPresent()
                .selectGroundShippingIfPresent()
                .selectCashOnDelivery()
                .continuePaymentInformation()
                .confirmOrder();
    }

    public String confirmedProductName() {
        return textOf(CheckoutLocators.CONFIRM_PRODUCT_NAME);
    }

    public BigDecimal confirmedTotal() {
        return MoneyParser.parse(textOf(CheckoutLocators.CONFIRM_ORDER_TOTAL));
    }

    public boolean orderWasCompleted() {
        return isVisible(CheckoutLocators.ORDER_COMPLETED)
                && textOf(CheckoutLocators.ORDER_COMPLETED).contains("Your order has been successfully processed");
    }

    public String orderNumber() {
        return textOf(CheckoutLocators.ORDER_NUMBER);
    }

    private void selectNewAddressIfAvailable() {
        if (!isVisible(CheckoutLocators.BILLING_ADDRESS_SELECT)) {
            return;
        }

        Select addressSelect = new Select(visible(CheckoutLocators.BILLING_ADDRESS_SELECT));
        boolean hasNewAddressOption = addressSelect.getOptions().stream()
                .map(WebElement::getText)
                .anyMatch(option -> option.contains("New Address"));

        if (hasNewAddressOption) {
            addressSelect.selectByVisibleText("New Address");
        }
    }
}
