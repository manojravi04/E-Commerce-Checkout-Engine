package ecommerce.payment;

import ecommerce.processor.PaymentProcessor;
import ecommerce.processor.PaymentResult;

import java.math.BigDecimal;

public class DebitPayment extends Payment {
    private final String accountLast4;

    public DebitPayment(PaymentProcessor processor, String accountLast4) {
        super(processor);
        if (accountLast4 == null || accountLast4.isBlank()) {
            throw new IllegalArgumentException("accountLast4 cannot be null/blank");
        }
        this.accountLast4 = accountLast4;
    }

    @Override
    public PaymentResult pay(BigDecimal amount) {
        PaymentResult result = processor.process(amount);
        String msg = result.message() + " (Debit •••• " + accountLast4 + ")";
        return new PaymentResult(result.success(), msg, result.transactionId());
    }
}
