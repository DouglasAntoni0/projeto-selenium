package br.com.douglasantoni0.selenium.driver;

import br.com.douglasantoni0.selenium.config.EnvironmentConfig;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> CURRENT_DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver start(EnvironmentConfig config) {
        WebDriver driver = switch (config.browser()) {
            case CHROME -> new ChromeDriver(chromeOptions(config));
            case FIREFOX -> new FirefoxDriver(firefoxOptions(config));
            case EDGE -> new EdgeDriver(edgeOptions(config));
        };

        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        driver.manage().window().setSize(config.viewport());
        CURRENT_DRIVER.set(driver);
        return driver;
    }

    public static WebDriver getDriver() {
        WebDriver driver = CURRENT_DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver ainda nao foi iniciado para este teste.");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = CURRENT_DRIVER.get();
        if (driver != null) {
            driver.quit();
            CURRENT_DRIVER.remove();
        }
    }

    private static ChromeOptions chromeOptions(EnvironmentConfig config) {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("--window-size=%d,%d".formatted(config.viewport().getWidth(), config.viewport().getHeight()));
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        if (config.headless()) {
            options.addArguments("--headless=new");
        }
        return options;
    }

    private static FirefoxOptions firefoxOptions(EnvironmentConfig config) {
        FirefoxOptions options = new FirefoxOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        if (config.headless()) {
            options.addArguments("-headless");
        }
        return options;
    }

    private static EdgeOptions edgeOptions(EnvironmentConfig config) {
        EdgeOptions options = new EdgeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("--window-size=%d,%d".formatted(config.viewport().getWidth(), config.viewport().getHeight()));
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        if (config.headless()) {
            options.addArguments("--headless=new");
        }
        return options;
    }
}
