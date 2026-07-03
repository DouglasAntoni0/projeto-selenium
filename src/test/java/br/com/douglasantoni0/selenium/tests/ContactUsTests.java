package br.com.douglasantoni0.selenium.tests;

import br.com.douglasantoni0.selenium.model.ContactMessage;
import br.com.douglasantoni0.selenium.pages.ContactUsPage;
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
@Feature("Contato")
@Tag("contact")
class ContactUsTests extends BaseTest {

    @Test
    @Tag("smoke")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Contato com sucesso")
    @DisplayName("Formulario de contato deve enviar mensagem valida")
    void should_send_valid_contact_message() {
        ContactMessage message = FakeDataFactory.newContactMessage();

        ContactUsPage contactUsPage = new ContactUsPage(driver, config)
                .open()
                .send(message);

        assertThat(contactUsPage.successMessage())
                .as("sucesso precisa ser textual, pois usuario final depende deste feedback")
                .contains("Your enquiry has been successfully sent");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Campos obrigatorios")
    @DisplayName("Formulario de contato vazio deve exibir validacoes obrigatorias")
    void should_validate_required_contact_fields() {
        ContactUsPage contactUsPage = new ContactUsPage(driver, config)
                .open()
                .submitEmptyForm();

        assertThat(contactUsPage.nameValidation()).contains("Enter your name");
        assertThat(contactUsPage.emailValidation()).contains("Enter email");
        assertThat(contactUsPage.enquiryValidation()).contains("Enter enquiry");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Email invalido")
    @DisplayName("Formulario de contato deve bloquear email invalido")
    void should_validate_contact_invalid_email() {
        ContactMessage message = FakeDataFactory.newContactMessage();

        ContactUsPage contactUsPage = new ContactUsPage(driver, config)
                .open()
                .submitWithInvalidEmail(message, "email-sem-arroba");

        assertThat(contactUsPage.emailValidation())
                .contains("Wrong email");
    }
}
