package br.com.douglasantoni0.selenium.tests;

import br.com.douglasantoni0.selenium.model.Customer;
import br.com.douglasantoni0.selenium.pages.LoginPage;
import br.com.douglasantoni0.selenium.pages.locators.LoginLocators;
import br.com.douglasantoni0.selenium.support.data.FakeDataFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Demo Web Shop")
@Feature("Login")
@Tag("login")
class LoginTests extends BaseTest {

    @Test
    @Tag("smoke")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login com sucesso")
    @DisplayName("Cliente cadastrado deve conseguir logar")
    void should_login_with_registered_customer() {
        Customer customer = registerFreshCustomerAndLogout();

        new LoginPage(driver, config)
                .open()
                .loginSuccessfully(customer.email(), customer.password());

        assertThat(header().isLoggedInAs(customer.email()))
                .as("apos login, o header deve mostrar o email da conta ativa")
                .isTrue();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Credenciais invalidas")
    @DisplayName("Login deve falhar com senha incorreta")
    void should_reject_wrong_password() {
        Customer customer = registerFreshCustomerAndLogout();

        LoginPage loginPage = new LoginPage(driver, config)
                .open()
                .loginExpectingFailure(customer.email(), customer.password() + "wrong");

        assertThat(loginPage.validationSummary())
                .contains("Login was unsuccessful")
                .contains("The credentials provided are incorrect");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Credenciais obrigatorias")
    @DisplayName("Login vazio deve exibir erro de autenticacao")
    void should_show_error_when_login_form_is_empty() {
        LoginPage loginPage = new LoginPage(driver, config)
                .open()
                .submitEmptyForm();

        assertThat(loginPage.validationSummary())
                .as("o servidor deve rejeitar tentativa sem credenciais")
                .contains("Login was unsuccessful");
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Story("Remember me")
    @DisplayName("Checkbox Remember me deve aceitar selecao do usuario")
    void should_allow_remember_me_selection() {
        LoginPage loginPage = new LoginPage(driver, config).open();

        if (!driver.findElement(LoginLocators.REMEMBER_ME).isSelected()) {
            driver.findElement(LoginLocators.REMEMBER_ME).click();
        }

        assertThat(loginPage.rememberMeIsSelected())
                .as("estado visual do checkbox precisa refletir a escolha do usuario")
                .isTrue();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Conta inexistente")
    @DisplayName("Login deve falhar com email inexistente")
    void should_reject_unknown_customer() {
        LoginPage loginPage = new LoginPage(driver, config)
                .open()
                .loginExpectingFailure(FakeDataFactory.uniqueEmail("unknown"), "Senha@123");

        assertThat(loginPage.validationSummary())
                .contains("Login was unsuccessful")
                .contains("No customer account found");
    }
}
