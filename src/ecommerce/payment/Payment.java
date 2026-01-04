package ecommerce.payment;

import ecommerce.processor.PaymentProcessor;
import ecommerce.processor.PaymentResult;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Payment {
    protected final PaymentProcessor processor;

    protected Payment(PaymentProcessor processor) {
        this.processor = Objects.requireNonNull(processor, "processor cannot be null");
    }

    public abstract PaymentResult pay(BigDecimal amount);
}
