package ecommerce.processor;

import java.math.BigDecimal;
import java.util.UUID;

public class MockBankProcessor implements PaymentProcessor {

    @Override
    public PaymentResult process(BigDecimal amount) {
        if (amount == null) {
            return new PaymentResult(false, "Amount cannot be null", null);
        }
        if (amount.signum() <= 0) {
            return new PaymentResult(false, "Amount must be > 0", null);
        }

        // Simulate a possible failure condition for realism:
        // Fail if amount > 1000 (arbitrary business rule)
        if (amount.compareTo(new BigDecimal("1000.00")) > 0) {
            return new PaymentResult(false, "Bank declined: amount exceeds limit", null);
        }

        String txn = "bank_" + UUID.randomUUID();
        return new PaymentResult(true, "Bank transfer approved", txn);
    }
}
