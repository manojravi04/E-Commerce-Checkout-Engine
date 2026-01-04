package ecommerce.processor;

public record PaymentResult(boolean success, String message, String transactionId) {}
