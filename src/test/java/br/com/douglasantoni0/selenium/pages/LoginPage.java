package br.com.douglasantoni0.selenium.pages;

import br.com.douglasantoni0.selenium.config.EnvironmentConfig;
import br.com.douglasantoni0.selenium.pages.components.HeaderComponent;
import br.com.douglasantoni0.selenium.pages.locators.LoginLocators;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver, EnvironmentConfig config) {
        super(driver, config);
    }

    public LoginPage open() {
        openPath("/login");
        visible(LoginLocators.PAGE_TITLE);
        return this;
    }

    public HeaderComponent loginSuccessfully(String email, String password) {
        submitCredentials(email, password, false);
        return new HeaderComponent(driver, config);
    }

    public LoginPage loginExpectingFailure(String email, String password) {
        submitCredentials(email, password, false);
        return this;
    }

    public LoginPage submitEmptyForm() {
        click(LoginLocators.LOGIN_BUTTON);
        return this;
    }

    public LoginPage loginRememberingUser(String email, String password) {
        submitCredentials(email, password, true);
        return this;
    }

    public String validationSummary() {
        return textOf(LoginLocators.VALIDATION_SUMMARY);
    }

    public String emailValidation() {
        return textOf(LoginLocators.EMAIL_VALIDATION);
    }

    public boolean rememberMeIsSelected() {
        return visible(LoginLocators.REMEMBER_ME).isSelected();
    }

    private void submitCredentials(String email, String password, boolean rememberMe) {
        type(LoginLocators.EMAIL, email);
        type(LoginLocators.PASSWORD, password);
        if (rememberMe && !visible(LoginLocators.REMEMBER_ME).isSelected()) {
            click(LoginLocators.REMEMBER_ME);
        }
        click(LoginLocators.LOGIN_BUTTON);
    }
}
