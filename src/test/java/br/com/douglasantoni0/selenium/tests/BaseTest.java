package br.com.douglasantoni0.selenium.tests;

import br.com.douglasantoni0.selenium.config.EnvironmentConfig;
import br.com.douglasantoni0.selenium.driver.DriverFactory;
import br.com.douglasantoni0.selenium.model.Customer;
import br.com.douglasantoni0.selenium.pages.HomePage;
import br.com.douglasantoni0.selenium.pages.RegisterPage;
import br.com.douglasantoni0.selenium.pages.components.HeaderComponent;
import br.com.douglasantoni0.selenium.support.data.FakeDataFactory;
import br.com.douglasantoni0.selenium.support.extensions.ScreenshotOnFailureExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ScreenshotOnFailureExtension.class)
public abstract class BaseTest {
    protected EnvironmentConfig config;
    protected WebDriver driver;

    @BeforeEach
    void setUp() {
        config = EnvironmentConfig.fromSystemProperties();
        driver = DriverFactory.start(config);
        driver.manage().deleteAllCookies();
    }

    @AfterEach
    void tearDown() {
        DriverFactory.quitDriver();
    }

    protected HomePage home() {
        return new HomePage(driver, config);
    }

    protected HeaderComponent header() {
        return new HeaderComponent(driver, config);
    }

    protected Customer registerFreshCustomer() {
        Customer customer = FakeDataFactory.newCustomer();

        RegisterPage registerPage = new RegisterPage(driver, config)
                .open()
                .register(customer);

        // A mensagem de sucesso e o e-mail no header provam que a conta foi criada e a sessao autenticada nasceu.
        assertThat(registerPage.successMessage())
                .as("confirmacao textual de cadastro")
                .contains("Your registration completed");

        HeaderComponent header = registerPage.continueAfterSuccessfulRegistration();
        assertThat(header.isLoggedInAs(customer.email()))
                .as("usuario recem-cadastrado deve aparecer autenticado no header")
                .isTrue();

        return customer;
    }

    protected Customer registerFreshCustomerAndLogout() {
        Customer customer = registerFreshCustomer();
        header().logout();
        return customer;
    }
}
