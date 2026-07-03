package br.com.douglasantoni0.selenium.support.assertions;

import java.math.BigDecimal;

public final class MoneyParser {
    private MoneyParser() {
    }

    public static BigDecimal parse(String rawMoney) {
        String normalized = rawMoney
                .replace("$", "")
                .replace(",", "")
                .trim();

        if (normalized.isBlank()) {
            throw new IllegalArgumentException("Nao foi possivel converter valor monetario vazio.");
        }

        return new BigDecimal(normalized);
    }
}
