package br.com.douglasantoni0.selenium.pages;

import br.com.douglasantoni0.selenium.config.EnvironmentConfig;
import br.com.douglasantoni0.selenium.model.Customer;
import br.com.douglasantoni0.selenium.pages.components.HeaderComponent;
import br.com.douglasantoni0.selenium.pages.locators.RegisterLocators;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {
    public RegisterPage(WebDriver driver, EnvironmentConfig config) {
        super(driver, config);
    }

    public RegisterPage open() {
        openPath("/register");
        visible(RegisterLocators.PAGE_TITLE);
        return this;
    }

    public RegisterPage register(Customer customer) {
        chooseGender(customer.gender());
        type(RegisterLocators.FIRST_NAME, customer.firstName());
        type(RegisterLocators.LAST_NAME, customer.lastName());
        type(RegisterLocators.EMAIL, customer.email());
        type(RegisterLocators.PASSWORD, customer.password());
        type(RegisterLocators.CONFIRM_PASSWORD, customer.password());
        click(RegisterLocators.REGISTER_BUTTON);
        return this;
    }

    public RegisterPage submitEmptyForm() {
        click(RegisterLocators.REGISTER_BUTTON);
        return this;
    }

    public RegisterPage submitWithMismatchedPassword(Customer customer, String wrongConfirmation) {
        chooseGender(customer.gender());
        type(RegisterLocators.FIRST_NAME, customer.firstName());
        type(RegisterLocators.LAST_NAME, customer.lastName());
        type(RegisterLocators.EMAIL, customer.email());
        type(RegisterLocators.PASSWORD, customer.password());
        type(RegisterLocators.CONFIRM_PASSWORD, wrongConfirmation);
        click(RegisterLocators.REGISTER_BUTTON);
        return this;
    }

    public HeaderComponent continueAfterSuccessfulRegistration() {
        click(RegisterLocators.CONTINUE_BUTTON);
        return new HeaderComponent(driver, config);
    }

    public String successMessage() {
        return textOf(RegisterLocators.SUCCESS_RESULT);
    }

    public String errorMessage() {
        return textOf(RegisterLocators.MESSAGE_ERROR);
    }

    public String firstNameValidation() {
        return textOf(RegisterLocators.FIRST_NAME_VALIDATION);
    }

    public String emailValidation() {
        return textOf(RegisterLocators.EMAIL_VALIDATION);
    }

    public String passwordValidation() {
        return textOf(RegisterLocators.PASSWORD_VALIDATION);
    }

    public String confirmPasswordValidation() {
        return textOf(RegisterLocators.CONFIRM_PASSWORD_VALIDATION);
    }

    private void chooseGender(String gender) {
        if ("M".equalsIgnoreCase(gender)) {
            click(RegisterLocators.GENDER_MALE);
            return;
        }
        click(RegisterLocators.GENDER_FEMALE);
    }
}
