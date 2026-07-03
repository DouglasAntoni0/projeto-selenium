package br.com.douglasantoni0.selenium.pages.locators;

import org.openqa.selenium.By;

public final class ContactUsLocators {
    public static final By PAGE_TITLE = By.cssSelector(".page-title h1");
    public static final By FULL_NAME = By.id("FullName");
    public static final By EMAIL = By.id("Email");
    public static final By ENQUIRY = By.id("Enquiry");
    public static final By SUBMIT_BUTTON = By.cssSelector("input.contact-us-button");
    public static final By SUCCESS_RESULT = By.cssSelector(".result");
    public static final By NAME_VALIDATION = By.cssSelector("[data-valmsg-for='FullName']");
    public static final By EMAIL_VALIDATION = By.cssSelector("[data-valmsg-for='Email']");
    public static final By ENQUIRY_VALIDATION = By.cssSelector("[data-valmsg-for='Enquiry']");

    private ContactUsLocators() {
    }
}
