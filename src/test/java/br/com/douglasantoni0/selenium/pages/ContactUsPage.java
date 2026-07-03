package br.com.douglasantoni0.selenium.pages;

import br.com.douglasantoni0.selenium.config.EnvironmentConfig;
import br.com.douglasantoni0.selenium.model.ContactMessage;
import br.com.douglasantoni0.selenium.pages.locators.ContactUsLocators;
import org.openqa.selenium.WebDriver;

public class ContactUsPage extends BasePage {
    public ContactUsPage(WebDriver driver, EnvironmentConfig config) {
        super(driver, config);
    }

    public ContactUsPage open() {
        openPath("/contactus");
        visible(ContactUsLocators.PAGE_TITLE);
        return this;
    }

    public ContactUsPage send(ContactMessage message) {
        type(ContactUsLocators.FULL_NAME, message.name());
        type(ContactUsLocators.EMAIL, message.email());
        type(ContactUsLocators.ENQUIRY, message.enquiry());
        click(ContactUsLocators.SUBMIT_BUTTON);
        return this;
    }

    public ContactUsPage submitEmptyForm() {
        click(ContactUsLocators.SUBMIT_BUTTON);
        return this;
    }

    public ContactUsPage submitWithInvalidEmail(ContactMessage message, String invalidEmail) {
        type(ContactUsLocators.FULL_NAME, message.name());
        type(ContactUsLocators.EMAIL, invalidEmail);
        type(ContactUsLocators.ENQUIRY, message.enquiry());
        click(ContactUsLocators.SUBMIT_BUTTON);
        return this;
    }

    public String successMessage() {
        return textOf(ContactUsLocators.SUCCESS_RESULT);
    }

    public String nameValidation() {
        return textOf(ContactUsLocators.NAME_VALIDATION);
    }

    public String emailValidation() {
        return textOf(ContactUsLocators.EMAIL_VALIDATION);
    }

    public String enquiryValidation() {
        return textOf(ContactUsLocators.ENQUIRY_VALIDATION);
    }
}
