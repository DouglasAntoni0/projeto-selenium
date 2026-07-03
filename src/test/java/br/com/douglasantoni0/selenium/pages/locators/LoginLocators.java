package br.com.douglasantoni0.selenium.pages.locators;

import org.openqa.selenium.By;

public final class LoginLocators {
    public static final By PAGE_TITLE = By.cssSelector(".page-title h1");
    public static final By EMAIL = By.id("Email");
    public static final By PASSWORD = By.id("Password");
    public static final By REMEMBER_ME = By.id("RememberMe");
    public static final By LOGIN_BUTTON = By.cssSelector("input.login-button");
    public static final By REGISTER_BUTTON = By.cssSelector("input.register-button");
    public static final By VALIDATION_SUMMARY = By.cssSelector(".validation-summary-errors");
    public static final By EMAIL_VALIDATION = By.cssSelector("[data-valmsg-for='Email']");
    public static final By PASSWORD_VALIDATION = By.cssSelector("[data-valmsg-for='Password']");

    private LoginLocators() {
    }
}
