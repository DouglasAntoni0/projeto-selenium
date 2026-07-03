package br.com.douglasantoni0.selenium.pages;

import br.com.douglasantoni0.selenium.config.EnvironmentConfig;
import br.com.douglasantoni0.selenium.pages.components.HeaderComponent;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver, EnvironmentConfig config) {
        super(driver, config);
    }

    public HomePage open() {
        openPath("/");
        return this;
    }

    public HeaderComponent header() {
        return new HeaderComponent(driver, config);
    }
}
