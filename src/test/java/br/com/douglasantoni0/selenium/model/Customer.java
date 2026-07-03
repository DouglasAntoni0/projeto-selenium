package br.com.douglasantoni0.selenium.model;

public record Customer(
        String gender,
        String firstName,
        String lastName,
        String email,
        String password
) {
    public String fullName() {
        return firstName + " " + lastName;
    }
}
