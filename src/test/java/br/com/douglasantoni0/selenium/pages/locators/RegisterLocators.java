package br.com.douglasantoni0.selenium.pages.locators;

import org.openqa.selenium.By;

public final class RegisterLocators {
    public static final By PAGE_TITLE = By.cssSelector(".page-title h1");
    public static final By GENDER_MALE = By.id("gender-male");
    public static final By GENDER_FEMALE = By.id("gender-female");
    public static final By FIRST_NAME = By.id("FirstName");
    public static final By LAST_NAME = By.id("LastName");
    public static final By EMAIL = By.id("Email");
    public static final By PASSWORD = By.id("Password");
    public static final By CONFIRM_PASSWORD = By.id("ConfirmPassword");
    public static final By REGISTER_BUTTON = By.id("register-button");
    public static final By SUCCESS_RESULT = By.cssSelector(".registration-result-page .result, .result");
    public static final By CONTINUE_BUTTON = By.cssSelector(".register-continue-button");
    public static final By MESSAGE_ERROR = By.cssSelector(".message-error");
    public static final By FIRST_NAME_VALIDATION = By.cssSelector("[data-valmsg-for='FirstName']");
    public static final By EMAIL_VALIDATION = By.cssSelector("[data-valmsg-for='Email']");
    public static final By PASSWORD_VALIDATION = By.cssSelector("[data-valmsg-for='Password']");
    public static final By CONFIRM_PASSWORD_VALIDATION = By.cssSelector("[data-valmsg-for='ConfirmPassword']");

    private RegisterLocators() {
    }
}
