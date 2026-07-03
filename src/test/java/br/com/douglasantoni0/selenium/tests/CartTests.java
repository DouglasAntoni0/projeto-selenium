package br.com.douglasantoni0.selenium.tests;

import br.com.douglasantoni0.selenium.model.Customer;
import br.com.douglasantoni0.selenium.model.GiftCardData;
import br.com.douglasantoni0.selenium.model.ProductSnapshot;
import br.com.douglasantoni0.selenium.pages.CartPage;
import br.com.douglasantoni0.selenium.pages.CatalogPage;
import br.com.douglasantoni0.selenium.pages.ProductDetailsPage;
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

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Demo Web Shop")
@Feature("Carrinho")
@Tag("cart")
class CartTests extends BaseTest {

    @Test
    @Tag("smoke")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Adicionar item")
    @DisplayName("Produto do catalogo deve ser adicionado ao carrinho com nome e preco corretos")
    void should_add_catalog_product_to_cart_with_correct_values() {
        CatalogPage catalogPage = new CatalogPage(driver, config).openBooks();
        ProductSnapshot catalogProduct = catalogPage.readProduct(DemoProducts.COMPUTING_AND_INTERNET_ID);

        catalogPage.addProductToCart(DemoProducts.COMPUTING_AND_INTERNET_ID);

        assertThat(catalogPage.header().cartQuantity())
                .as("contador do header deve refletir o item incluido via AJAX")
                .isEqualTo(1);

        ProductSnapshot cartItem = catalogPage.header().goToCart().firstItem();

        assertThat(cartItem.name()).isEqualTo(catalogProduct.name());
        assertThat(cartItem.unitPrice()).isEqualByComparingTo(catalogProduct.unitPrice());
        assertThat(cartItem.subtotal()).isEqualByComparingTo(catalogProduct.unitPrice());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Atualizar quantidade")
    @DisplayName("Carrinho deve recalcular subtotal quando quantidade muda")
    void should_recalculate_subtotal_when_quantity_changes() {
        CartPage cartPage = addBookAndOpenCart()
                .updateFirstItemQuantity(3);

        ProductSnapshot cartItem = cartPage.firstItem();
        BigDecimal expectedSubtotal = cartItem.unitPrice().multiply(BigDecimal.valueOf(cartItem.quantity()));

        assertThat(cartItem.quantity()).isEqualTo(3);
        assertThat(cartItem.subtotal())
                .as("subtotal deve ser preco unitario multiplicado pela quantidade")
                .isEqualByComparingTo(expectedSubtotal);
        assertThat(cartPage.orderTotal())
                .as("sem frete escolhido ainda, total do carrinho deve acompanhar o subtotal")
                .isEqualByComparingTo(expectedSubtotal);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Remover item")
    @DisplayName("Carrinho deve ficar vazio ao remover unico item")
    void should_remove_item_from_cart() {
        CartPage cartPage = addBookAndOpenCart()
                .removeFirstItem();

        assertThat(cartPage.isEmpty())
                .as("remocao precisa zerar o carrinho e expor mensagem clara ao usuario")
                .isTrue();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Termos de servico")
    @DisplayName("Checkout nao deve avancar sem aceitar termos de servico")
    void should_block_checkout_without_terms_of_service() {
        CartPage cartPage = addBookAndOpenCart()
                .attemptCheckoutWithoutTerms();

        assertThat(cartPage.termsWarningText())
                .contains("Please accept the terms of service before the next step");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Produto com campos obrigatorios")
    @DisplayName("Gift card deve validar campos obrigatorios antes de entrar no carrinho")
    void should_validate_required_gift_card_fields() {
        ProductDetailsPage productPage = new ProductDetailsPage(driver, config)
                .openVirtualGiftCard()
                .tryAddGiftCardWithoutRequiredFields();

        assertThat(productPage.notification())
                .contains("Enter valid")
                .contains("recipient");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Adicionar gift card")
    @DisplayName("Gift card com dados dinamicos deve entrar no carrinho com quantidade correta")
    void should_add_gift_card_with_dynamic_data() {
        Customer sender = FakeDataFactory.newCustomer();
        GiftCardData giftCardData = FakeDataFactory.newGiftCardData(sender);

        ProductDetailsPage productPage = new ProductDetailsPage(driver, config)
                .openVirtualGiftCard()
                .addGiftCardToCart(giftCardData, 2);

        assertThat(productPage.header().cartQuantity()).isEqualTo(2);

        ProductSnapshot cartItem = productPage.header().goToCart().firstItem();
        assertThat(cartItem.name()).contains(DemoProducts.VIRTUAL_GIFT_CARD_NAME);
        assertThat(cartItem.quantity()).isEqualTo(2);
        assertThat(cartItem.subtotal())
                .as("gift card de 25.00 com quantidade 2 deve totalizar 50.00")
                .isEqualByComparingTo("50.00");
    }

    private CartPage addBookAndOpenCart() {
        return new CatalogPage(driver, config)
                .openBooks()
                .addProductToCart(DemoProducts.COMPUTING_AND_INTERNET_ID)
                .header()
                .goToCart();
    }
}
