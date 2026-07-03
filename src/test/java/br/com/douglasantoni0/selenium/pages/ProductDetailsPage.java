package br.com.douglasantoni0.selenium.pages;

import br.com.douglasantoni0.selenium.config.EnvironmentConfig;
import br.com.douglasantoni0.selenium.model.GiftCardData;
import br.com.douglasantoni0.selenium.pages.components.HeaderComponent;
import br.com.douglasantoni0.selenium.pages.locators.ProductDetailsLocators;
import br.com.douglasantoni0.selenium.support.data.DemoProducts;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage extends BasePage {
    public ProductDetailsPage(WebDriver driver, EnvironmentConfig config) {
        super(driver, config);
    }

    public ProductDetailsPage openVirtualGiftCard() {
        openPath(DemoProducts.VIRTUAL_GIFT_CARD_PATH);
        visible(ProductDetailsLocators.PRODUCT_NAME);
        return this;
    }

    public ProductDetailsPage addGiftCardToCart(GiftCardData giftCardData, int quantity) {
        fillGiftCardData(giftCardData);
        type(ProductDetailsLocators.QUANTITY, String.valueOf(quantity));
        click(ProductDetailsLocators.ADD_TO_CART_BUTTON);
        waitForNotificationContaining("The product has been added to your shopping cart");
        return this;
    }

    public ProductDetailsPage tryAddGiftCardWithoutRequiredFields() {
        click(ProductDetailsLocators.ADD_TO_CART_BUTTON);
        waitForNotificationContaining("Enter valid");
        return this;
    }

    public String productName() {
        return textOf(ProductDetailsLocators.PRODUCT_NAME);
    }

    public String productPrice() {
        return textOf(ProductDetailsLocators.PRODUCT_PRICE);
    }

    public String notification() {
        return notificationText();
    }

    public HeaderComponent header() {
        return new HeaderComponent(driver, config);
    }

    private void fillGiftCardData(GiftCardData giftCardData) {
        type(ProductDetailsLocators.GIFT_RECIPIENT_NAME, giftCardData.recipientName());
        type(ProductDetailsLocators.GIFT_RECIPIENT_EMAIL, giftCardData.recipientEmail());
        type(ProductDetailsLocators.GIFT_SENDER_NAME, giftCardData.senderName());
        type(ProductDetailsLocators.GIFT_SENDER_EMAIL, giftCardData.senderEmail());
        type(ProductDetailsLocators.GIFT_MESSAGE, giftCardData.message());
    }
}
