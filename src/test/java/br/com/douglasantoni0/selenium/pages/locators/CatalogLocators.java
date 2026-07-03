package br.com.douglasantoni0.selenium.pages.locators;

import org.openqa.selenium.By;

public final class CatalogLocators {
    public static final By PAGE_TITLE = By.cssSelector(".page-title h1");
    public static final By PRODUCT_CARDS = By.cssSelector(".product-item");

    private CatalogLocators() {
    }

    public static By productCardById(String productId) {
        return By.cssSelector(".product-item[data-productid='%s']".formatted(productId));
    }

    public static By productTitleById(String productId) {
        return By.cssSelector(".product-item[data-productid='%s'] .product-title a".formatted(productId));
    }

    public static By productPriceById(String productId) {
        return By.cssSelector(".product-item[data-productid='%s'] .actual-price".formatted(productId));
    }

    public static By addToCartButtonById(String productId) {
        return By.cssSelector(".product-item[data-productid='%s'] input.product-box-add-to-cart-button".formatted(productId));
    }
}
