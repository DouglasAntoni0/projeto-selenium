package br.com.douglasantoni0.selenium.driver;

import java.util.Arrays;

public enum Browser {
    CHROME,
    FIREFOX,
    EDGE;

    public static Browser from(String rawBrowser) {
        return Arrays.stream(values())
                .filter(browser -> browser.name().equalsIgnoreCase(rawBrowser))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Browser invalido: '%s'. Valores aceitos: chrome, firefox, edge.".formatted(rawBrowser)));
    }
}
