package ecommerce.payment;

import ecommerce.processor.PaymentProcessor;
import ecommerce.processor.PaymentResult;

import java.math.BigDecimal;

public class PayPalPayment extends Payment {
    private final String email;

    public PayPalPayment(PaymentProcessor processor, String email) {
        super(processor);
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email cannot be null/blank");
        }
        this.email = email;
    }

    @Override
    public PaymentResult pay(BigDecimal amount) {
        PaymentResult result = processor.process(amount);
        String msg = result.message() + " (PayPal: " + email + ")";
        return new PaymentResult(result.success(), msg, result.transactionId());
    }
}
