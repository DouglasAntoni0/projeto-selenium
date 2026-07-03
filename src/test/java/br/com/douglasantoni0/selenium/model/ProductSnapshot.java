package br.com.douglasantoni0.selenium.model;

import java.math.BigDecimal;

public record ProductSnapshot(
        String name,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal subtotal
) {
}
