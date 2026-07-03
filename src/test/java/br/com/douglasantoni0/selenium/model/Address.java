package br.com.douglasantoni0.selenium.model;

public record Address(
        String country,
        String city,
        String addressLine,
        String postalCode,
        String phoneNumber
) {
}
