package br.com.douglasantoni0.selenium.pages.locators;

import org.openqa.selenium.By;

public final class NotificationLocators {
    public static final By BAR = By.id("bar-notification");
    public static final By CLOSE_BUTTON = By.cssSelector("#bar-notification .close");

    private NotificationLocators() {
    }
}
