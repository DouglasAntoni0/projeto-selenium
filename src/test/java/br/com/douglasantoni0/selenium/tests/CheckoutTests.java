package br.com.douglasantoni0.selenium.tests;

import br.com.douglasantoni0.selenium.model.Address;
import br.com.douglasantoni0.selenium.model.ProductSnapshot;
import br.com.douglasantoni0.selenium.pages.CartPage;
import br.com.douglasantoni0.selenium.pages.CheckoutPage;
import br.com.douglasantoni0.selenium.pages.CatalogPage;
import br.com.douglasantoni0.selenium.support.data.DemoProducts;
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
@Feature("Checkout")
@Tag("checkout")
class CheckoutTests extends BaseTest {

    @Test
    @Tag("smoke")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Fluxo completo")
    @DisplayName("Cliente cadastrado deve concluir checkout completo com produto fisico")
    void should_complete_full_checkout_for_registered_customer() {
        registerFreshCustomer();
        Address address = FakeDataFactory.newAddress();

        CartPage cartPage = new CatalogPage(driver, config)
                .openBooks()
                .addProductToCart(DemoProducts.COMPUTING_AND_INTERNET_ID)
                .header()
                .goToCart();

        ProductSnapshot cartItem = cartPage.firstItem();

        CheckoutPage checkoutPage = cartPage
                .acceptTermsOfService()
                .checkout()
                .fillBillingAddress(address)
                .continueShippingAddressIfPresent()
                .selectGroundShippingIfPresent()
                .selectCashOnDelivery()
                .continuePaymentInformation();

        assertThat(checkoutPage.confirmedProductName())
                .as("confirmacao de pedido deve mostrar o mesmo produto do carrinho")
                .isEqualTo(cartItem.name());
        assertThat(checkoutPage.confirmedTotal())
                .as("total confirmado deve ser igual ou maior que subtotal, pois frete pode ser aplicado")
                .isGreaterThanOrEqualTo(cartItem.subtotal());

        checkoutPage.confirmOrder();

        assertThat(checkoutPage.orderWasCompleted()).isTrue();
        assertThat(checkoutPage.orderNumber()).contains("Order number");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Pre-condicao de autenticacao")
    @DisplayName("Checkout anonimo deve direcionar para identificacao do cliente")
    void should_redirect_anonymous_checkout_to_login() {
        CartPage cartPage = new CatalogPage(driver, config)
                .openBooks()
                .addProductToCart(DemoProducts.FICTION_ID)
                .header()
                .goToCart();

        cartPage.acceptTermsOfService().checkout();

        assertThat(driver.getCurrentUrl())
                .as("usuario anonimo nao deve ir direto para dados de pagamento")
                .contains("/login/checkoutasguest");
    }
}
