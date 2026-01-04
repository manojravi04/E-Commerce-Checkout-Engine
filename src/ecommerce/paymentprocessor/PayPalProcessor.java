package ecommerce.processor;

import java.math.BigDecimal;
import java.util.UUID;

public class PayPalProcessor implements PaymentProcessor {

    @Override
    public PaymentResult process(BigDecimal amount) {
        if (amount == null) {
            return new PaymentResult(false, "Amount cannot be null", null);
        }
        if (amount.signum() <= 0) {
            return new PaymentResult(false, "Amount must be > 0", null);
        }

        String txn = "paypal_" + UUID.randomUUID();
        return new PaymentResult(true, "PayPal payment completed", txn);
    }
}
