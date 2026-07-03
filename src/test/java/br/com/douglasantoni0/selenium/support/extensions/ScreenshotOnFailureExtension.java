package br.com.douglasantoni0.selenium.support.extensions;

import br.com.douglasantoni0.selenium.driver.DriverFactory;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriverException;

public class ScreenshotOnFailureExtension implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isEmpty()) {
            return;
        }

        try {
            var driver = DriverFactory.getDriver();
            String displayName = context.getDisplayName().replaceAll("[^a-zA-Z0-9._-]", "_");
            AllureEvidence.attachScreenshot(driver, "failure-screenshot-" + displayName);
            AllureEvidence.attachPageSource(driver, "failure-page-source-" + displayName);
        } catch (IllegalStateException | WebDriverException ignored) {
            // Se o browser falhou antes de iniciar, a excecao original do teste e mais valiosa.
        }
    }
}
