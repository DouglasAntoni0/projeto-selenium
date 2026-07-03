package br.com.douglasantoni0.selenium.pages;

import br.com.douglasantoni0.selenium.config.EnvironmentConfig;
import br.com.douglasantoni0.selenium.pages.locators.NotificationLocators;
import br.com.douglasantoni0.selenium.support.exceptions.PageInteractionException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final EnvironmentConfig config;
    protected final WebDriverWait wait;
    protected final WebDriverWait tinyWait;

    protected BasePage(WebDriver driver, EnvironmentConfig config) {
        this.driver = driver;
        this.config = config;
        this.wait = new WebDriverWait(driver, config.timeout());
        this.tinyWait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    protected void openPath(String path) {
        driver.navigate().to(config.absoluteUrl(path));
        waitUntilDocumentIsReady();
    }

    protected WebElement visible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException exception) {
            throw new PageInteractionException("Elemento nao ficou visivel no tempo esperado: " + locator, exception);
        }
    }

    protected WebElement clickable(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException exception) {
            throw new PageInteractionException("Elemento nao ficou clicavel no tempo esperado: " + locator, exception);
        }
    }

    protected List<WebElement> visibleElements(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        } catch (TimeoutException exception) {
            throw new PageInteractionException("Elementos nao ficaram visiveis no tempo esperado: " + locator, exception);
        }
    }

    protected void click(By locator) {
        clickable(locator).click();
    }

    protected void type(By locator, String value) {
        WebElement field = visible(locator);
        field.clear();
        field.sendKeys(value);
    }

    protected String textOf(By locator) {
        return visible(locator).getText().trim();
    }

    protected String attributeOf(By locator, String attributeName) {
        return visible(locator).getAttribute(attributeName);
    }

    protected void selectByVisibleText(By locator, String text) {
        new Select(visible(locator)).selectByVisibleText(text);
    }

    protected void waitForUrlContaining(String fragment) {
        try {
            wait.until(ExpectedConditions.urlContains(fragment));
        } catch (TimeoutException exception) {
            throw new PageInteractionException("URL nao contem o fragmento esperado: " + fragment, exception);
        }
    }

    protected boolean isVisible(By locator) {
        try {
            return tinyWait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException exception) {
            return false;
        }
    }

    protected void waitForAnyVisible(By... locators) {
        try {
            wait.until(anyVisible(locators));
        } catch (TimeoutException exception) {
            throw new PageInteractionException("Nenhum dos elementos ficou visivel: " + Arrays.toString(locators), exception);
        }
    }

    protected void waitForNotificationContaining(String expectedText) {
        // O carrinho do nopCommerce e atualizado via AJAX; a barra de notificacao e o contrato visual mais confiavel.
        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(NotificationLocators.BAR, expectedText));
        } catch (TimeoutException exception) {
            throw new PageInteractionException("Notificacao nao exibiu o texto esperado: " + expectedText, exception);
        }
    }

    protected String notificationText() {
        return textOf(NotificationLocators.BAR);
    }

    protected void closeNotificationIfVisible() {
        if (isVisible(NotificationLocators.CLOSE_BUTTON)) {
            click(NotificationLocators.CLOSE_BUTTON);
        }
    }

    protected void waitUntilDocumentIsReady() {
        try {
            wait.until(driver -> "complete".equals(((JavascriptExecutor) driver).executeScript("return document.readyState")));
        } catch (TimeoutException exception) {
            throw new PageInteractionException("Documento nao terminou de carregar.", exception);
        }
    }

    protected void waitUntilAjaxLoaderDisappears() {
        // O site usa uma camada global de loading em algumas transicoes de checkout.
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ajax-loading-block-window")));
    }

    protected void scrollTo(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", visible(locator));
    }

    private static ExpectedCondition<Boolean> anyVisible(By... locators) {
        return driver -> Arrays.stream(locators).anyMatch(locator -> {
            try {
                return ExpectedConditions.visibilityOfElementLocated(locator).apply(driver) != null;
            } catch (RuntimeException ignored) {
                return false;
            }
        });
    }
}
