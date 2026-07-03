package br.com.douglasantoni0.selenium.tests;

import br.com.douglasantoni0.selenium.model.Customer;
import br.com.douglasantoni0.selenium.pages.RegisterPage;
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
@Feature("Cadastro")
@Tag("registration")
class RegistrationTests extends BaseTest {

    @Test
    @Tag("smoke")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Cadastro com sucesso")
    @DisplayName("Cadastro com dados dinamicos deve autenticar o cliente")
    void should_register_customer_with_dynamic_data() {
        Customer customer = registerFreshCustomer();

        assertThat(header().accountEmail())
                .as("o header deve expor o email cadastrado, pois ele representa a sessao autenticada")
                .isEqualToIgnoringCase(customer.email());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Campos obrigatorios")
    @DisplayName("Cadastro vazio deve exibir validacoes de campos obrigatorios")
    void should_validate_required_registration_fields() {
        RegisterPage registerPage = new RegisterPage(driver, config)
                .open()
                .submitEmptyForm();

        assertThat(registerPage.firstNameValidation()).contains("First name is required");
        assertThat(registerPage.emailValidation()).contains("Email is required");
        assertThat(registerPage.passwordValidation()).contains("Password is required");
        assertThat(registerPage.confirmPasswordValidation()).contains("Password is required");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Email duplicado")
    @DisplayName("Cadastro nao deve permitir email ja existente")
    void should_reject_duplicate_email() {
        Customer alreadyRegistered = registerFreshCustomer();
        header().logout();

        RegisterPage registerPage = new RegisterPage(driver, config)
                .open()
                .register(alreadyRegistered);

        assertThat(registerPage.errorMessage())
                .as("email duplicado precisa ser barrado para preservar identidade da conta")
                .contains("The specified email already exists");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Confirmacao de senha")
    @DisplayName("Cadastro deve bloquear confirmacao de senha divergente")
    void should_validate_password_confirmation() {
        Customer customer = FakeDataFactory.newCustomer();

        RegisterPage registerPage = new RegisterPage(driver, config)
                .open()
                .submitWithMismatchedPassword(customer, customer.password() + "x");

        assertThat(registerPage.confirmPasswordValidation())
                .contains("The password and confirmation password do not match");
    }
}
