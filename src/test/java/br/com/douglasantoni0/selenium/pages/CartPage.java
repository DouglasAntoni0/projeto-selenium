package br.com.douglasantoni0.selenium.pages;

import br.com.douglasantoni0.selenium.config.EnvironmentConfig;
import br.com.douglasantoni0.selenium.model.ProductSnapshot;
import br.com.douglasantoni0.selenium.pages.locators.CartLocators;
import br.com.douglasantoni0.selenium.support.assertions.MoneyParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.List;

public class CartPage extends BasePage {
    public CartPage(WebDriver driver, EnvironmentConfig config) {
        super(driver, config);
    }

    public CartPage open() {
        openPath("/cart");
        visible(CartLocators.PAGE_TITLE);
        return this;
    }

    public List<ProductSnapshot> items() {
        return visibleElements(CartLocators.CART_ROWS).stream()
                .map(this::snapshotFrom)
                .toList();
    }

    public ProductSnapshot firstItem() {
        return snapshotFrom(firstRow());
    }

    public BigDecimal orderTotal() {
        return MoneyParser.parse(textOf(CartLocators.ORDER_TOTAL));
    }

    public CartPage updateFirstItemQuantity(int quantity) {
        WebElement quantityInput = firstRow().findElement(CartLocators.QUANTITY_INPUT);
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(quantity));
        click(CartLocators.UPDATE_CART_BUTTON);
        waitUntilDocumentIsReady();
        wait.until(driver -> String.valueOf(quantity).equals(firstRow().findElement(CartLocators.QUANTITY_INPUT).getAttribute("value")));
        return this;
    }

    public CartPage removeFirstItem() {
        firstRow().findElement(CartLocators.REMOVE_CHECKBOX).click();
        click(CartLocators.UPDATE_CART_BUTTON);
        waitUntilDocumentIsReady();
        return this;
    }

    public CartPage acceptTermsOfService() {
        WebElement terms = visible(CartLocators.TERMS_OF_SERVICE);
        if (!terms.isSelected()) {
            terms.click();
        }
        return this;
    }

    public CheckoutPage checkout() {
        click(CartLocators.CHECKOUT_BUTTON);
        return new CheckoutPage(driver, config);
    }

    public CartPage attemptCheckoutWithoutTerms() {
        click(CartLocators.CHECKOUT_BUTTON);
        visible(CartLocators.TERMS_WARNING_DIALOG);
        return this;
    }

    public String termsWarningText() {
        return textOf(CartLocators.TERMS_WARNING_DIALOG);
    }

    public boolean isEmpty() {
        return isVisible(CartLocators.EMPTY_CART_MESSAGE)
                && textOf(CartLocators.EMPTY_CART_MESSAGE).contains("Your Shopping Cart is empty");
    }

    public boolean checkoutButtonIsEnabled() {
        return visible(CartLocators.CHECKOUT_BUTTON).isEnabled();
    }

    private WebElement firstRow() {
        return visibleElements(CartLocators.CART_ROWS).get(0);
    }

    private ProductSnapshot snapshotFrom(WebElement row) {
        String name = row.findElement(CartLocators.PRODUCT_NAME).getText().trim();
        BigDecimal unitPrice = MoneyParser.parse(row.findElement(CartLocators.UNIT_PRICE).getText());
        int quantity = Integer.parseInt(row.findElement(CartLocators.QUANTITY_INPUT).getAttribute("value"));
        BigDecimal subtotal = MoneyParser.parse(row.findElement(CartLocators.SUBTOTAL).getText());
        return new ProductSnapshot(name, unitPrice, quantity, subtotal);
    }
}
