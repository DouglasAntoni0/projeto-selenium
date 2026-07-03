package br.com.douglasantoni0.selenium.support.extensions;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public final class AllureEvidence {
    private AllureEvidence() {
    }

    public static void attachScreenshot(WebDriver driver, String attachmentName) {
        if (!(driver instanceof TakesScreenshot screenshotDriver)) {
            return;
        }

        byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(attachmentName, "image/png", new ByteArrayInputStream(screenshot), ".png");
    }

    public static void attachPageSource(WebDriver driver, String attachmentName) {
        byte[] pageSource = driver.getPageSource().getBytes(StandardCharsets.UTF_8);
        Allure.addAttachment(attachmentName, "text/html", new ByteArrayInputStream(pageSource), ".html");
    }
}
