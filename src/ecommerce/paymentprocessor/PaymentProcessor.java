package ecommerce.processor;

import java.math.BigDecimal;

public interface PaymentProcessor {
    PaymentResult process(BigDecimal amount);
}
