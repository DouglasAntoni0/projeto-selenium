package br.com.douglasantoni0.selenium.support.data;

import br.com.douglasantoni0.selenium.model.Address;
import br.com.douglasantoni0.selenium.model.ContactMessage;
import br.com.douglasantoni0.selenium.model.Customer;
import br.com.douglasantoni0.selenium.model.GiftCardData;
import net.datafaker.Faker;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

public final class FakeDataFactory {
    private static final Faker FAKER = new Faker(Locale.US);

    private FakeDataFactory() {
    }

    public static Customer newCustomer() {
        String firstName = cleanName(FAKER.name().firstName());
        String lastName = cleanName(FAKER.name().lastName());

        return new Customer(
                "F",
                firstName,
                lastName,
                uniqueEmail("customer"),
                "Qa@" + FAKER.number().digits(8)
        );
    }

    public static Customer customerWithInvalidEmail() {
        Customer customer = newCustomer();
        return new Customer(
                customer.gender(),
                customer.firstName(),
                customer.lastName(),
                "email-invalido-sem-arroba",
                customer.password()
        );
    }

    public static Address newAddress() {
        return new Address(
                "United States",
                FAKER.address().cityName(),
                FAKER.address().streetAddress(),
                FAKER.address().zipCode(),
                FAKER.phoneNumber().subscriberNumber(10)
        );
    }

    public static ContactMessage newContactMessage() {
        return new ContactMessage(
                FAKER.name().fullName(),
                uniqueEmail("contact"),
                "Automated enquiry generated at %s. Please ignore this QA message.".formatted(Instant.now())
        );
    }

    public static GiftCardData newGiftCardData(Customer sender) {
        return new GiftCardData(
                FAKER.name().fullName(),
                uniqueEmail("gift-recipient"),
                sender.fullName(),
                sender.email(),
                "Happy testing! This gift card was created by an automated Selenium scenario."
        );
    }

    public static String uniqueEmail(String purpose) {
        String token = UUID.randomUUID().toString().substring(0, 8);
        return "qa.%s.%s.%d@example.com".formatted(purpose, token, System.currentTimeMillis()).toLowerCase(Locale.ROOT);
    }

    private static String cleanName(String name) {
        return name.replaceAll("[^A-Za-z]", "");
    }
}
