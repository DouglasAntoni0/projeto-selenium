package br.com.douglasantoni0.selenium.config;

import br.com.douglasantoni0.selenium.driver.Browser;
import org.openqa.selenium.Dimension;

import java.net.URI;
import java.time.Duration;

public final class EnvironmentConfig {
    private static final String DEFAULT_BASE_URL = "https://demowebshop.tricentis.com";
    private static final String DEFAULT_VIEWPORT = "1440x1000";

    private final URI baseUri;
    private final Browser browser;
    private final boolean headless;
    private final Duration timeout;
    private final Dimension viewport;

    private EnvironmentConfig(URI baseUri, Browser browser, boolean headless, Duration timeout, Dimension viewport) {
        this.baseUri = baseUri;
        this.browser = browser;
        this.headless = headless;
        this.timeout = timeout;
        this.viewport = viewport;
    }

    public static EnvironmentConfig fromSystemProperties() {
        String baseUrl = System.getProperty("baseUrl", DEFAULT_BASE_URL);
        String browser = System.getProperty("browser", "chrome");
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        long timeoutSeconds = Long.parseLong(System.getProperty("timeoutSeconds", "15"));
        Dimension viewport = parseViewport(System.getProperty("viewport", DEFAULT_VIEWPORT));

        return new EnvironmentConfig(
                URI.create(baseUrl),
                Browser.from(browser),
                headless,
                Duration.ofSeconds(timeoutSeconds),
                viewport
        );
    }

    public URI baseUri() {
        return baseUri;
    }

    public Browser browser() {
        return browser;
    }

    public boolean headless() {
        return headless;
    }

    public Duration timeout() {
        return timeout;
    }

    public Dimension viewport() {
        return viewport;
    }

    public String absoluteUrl(String path) {
        if (path == null || path.isBlank() || "/".equals(path)) {
            return baseUri.toString();
        }

        String normalizedPath = path.startsWith("/") ? path : "/" + path;
        return baseUri.resolve(normalizedPath).toString();
    }

    private static Dimension parseViewport(String viewport) {
        String[] pieces = viewport.toLowerCase().split("x");
        if (pieces.length != 2) {
            throw new IllegalArgumentException("Viewport deve seguir o formato largura x altura. Exemplo: 1440x1000.");
        }

        return new Dimension(Integer.parseInt(pieces[0].trim()), Integer.parseInt(pieces[1].trim()));
    }
}
