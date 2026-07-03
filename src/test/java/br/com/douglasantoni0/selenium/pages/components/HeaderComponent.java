package br.com.douglasantoni0.selenium.pages.components;

import br.com.douglasantoni0.selenium.config.EnvironmentConfig;
import br.com.douglasantoni0.selenium.pages.BasePage;
import br.com.douglasantoni0.selenium.pages.CartPage;
import br.com.douglasantoni0.selenium.pages.CatalogPage;
import br.com.douglasantoni0.selenium.pages.ContactUsPage;
import br.com.douglasantoni0.selenium.pages.LoginPage;
import br.com.douglasantoni0.selenium.pages.RegisterPage;
import br.com.douglasantoni0.selenium.pages.locators.HeaderLocators;
import org.openqa.selenium.WebDriver;

public class HeaderComponent extends BasePage {
    public HeaderComponent(WebDriver driver, EnvironmentConfig config) {
        super(driver, config);
    }

    public RegisterPage goToRegister() {
        click(HeaderLocators.REGISTER_LINK);
        return new RegisterPage(driver, config);
    }

    public LoginPage goToLogin() {
        click(HeaderLocators.LOGIN_LINK);
        return new LoginPage(driver, config);
    }

    public CatalogPage goToBooks() {
        click(HeaderLocators.BOOKS_MENU_LINK);
        return new CatalogPage(driver, config);
    }

    public CartPage goToCart() {
        openPath("/cart");
        return new CartPage(driver, config);
    }

    public ContactUsPage goToContactUs() {
        click(HeaderLocators.CONTACT_US_FOOTER_LINK);
        return new ContactUsPage(driver, config);
    }

    public HeaderComponent logout() {
        if (isVisible(HeaderLocators.LOGOUT_LINK)) {
            click(HeaderLocators.LOGOUT_LINK);
            waitForUrlContaining("/");
        }
        return this;
    }

    public boolean isLoggedInAs(String email) {
        return isVisible(HeaderLocators.ACCOUNT_LINK) && accountEmail().equalsIgnoreCase(email);
    }

    public String accountEmail() {
        return textOf(HeaderLocators.ACCOUNT_LINK);
    }

    public int cartQuantity() {
        String rawQuantity = textOf(HeaderLocators.CART_QUANTITY);
        String digitsOnly = rawQuantity.replaceAll("[^0-9]", "");
        return digitsOnly.isBlank() ? 0 : Integer.parseInt(digitsOnly);
    }
}

