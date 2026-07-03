package br.com.douglasantoni0.selenium.model;

public record GiftCardData(
        String recipientName,
        String recipientEmail,
        String senderName,
        String senderEmail,
        String message
) {
}
