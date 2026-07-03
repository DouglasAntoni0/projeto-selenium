package br.com.douglasantoni0.selenium.pages;

import br.com.douglasantoni0.selenium.config.EnvironmentConfig;
import br.com.douglasantoni0.selenium.model.ProductSnapshot;
import br.com.douglasantoni0.selenium.pages.components.HeaderComponent;
import br.com.douglasantoni0.selenium.pages.locators.CatalogLocators;
import br.com.douglasantoni0.selenium.support.assertions.MoneyParser;
import org.openqa.selenium.WebDriver;

public class CatalogPage extends BasePage {
    public CatalogPage(WebDriver driver, EnvironmentConfig config) {
        super(driver, config);
    }

    public CatalogPage openBooks() {
        openPath("/books");
        visible(CatalogLocators.PAGE_TITLE);
        return this;
    }

    public ProductSnapshot readProduct(String productId) {
        visible(CatalogLocators.productCardById(productId));
        String name = textOf(CatalogLocators.productTitleById(productId));
        var unitPrice = MoneyParser.parse(textOf(CatalogLocators.productPriceById(productId)));
        return new ProductSnapshot(name, unitPrice, 1, unitPrice);
    }

    public CatalogPage addProductToCart(String productId) {
        scrollTo(CatalogLocators.addToCartButtonById(productId));
        click(CatalogLocators.addToCartButtonById(productId));
        waitForNotificationContaining("The product has been added to your shopping cart");
        return this;
    }

    public HeaderComponent header() {
        return new HeaderComponent(driver, config);
    }
}
