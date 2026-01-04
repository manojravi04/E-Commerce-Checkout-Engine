package ecommerce.payment;

import ecommerce.processor.PaymentProcessor;
import ecommerce.processor.PaymentResult;

import java.math.BigDecimal;

public class CreditCardPayment extends Payment {
    private final String cardLast4;

    public CreditCardPayment(PaymentProcessor processor, String cardLast4) {
        super(processor);
        if (cardLast4 == null || cardLast4.isBlank()) {
            throw new IllegalArgumentException("cardLast4 cannot be null/blank");
        }
        this.cardLast4 = cardLast4;
    }

    @Override
    public PaymentResult pay(BigDecimal amount) {
        // You could add card validation rules here
        PaymentResult result = processor.process(amount);
        String msg = result.message() + " (Credit Card •••• " + cardLast4 + ")";
        return new PaymentResult(result.success(), msg, result.transactionId());
    }
}
