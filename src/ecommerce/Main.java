package ecommerce;

import ecommerce.payment.CreditCardPayment;
import ecommerce.payment.PayPalPayment;
import ecommerce.processor.MockBankProcessor;
import ecommerce.processor.PayPalProcessor;
import ecommerce.processor.PaymentResult;
import ecommerce.processor.StripeProcessor;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BigDecimal amount = new BigDecimal("149.99");

        // Same payment type (CreditCard) can use different processors:
        var ccViaStripe = new CreditCardPayment(new StripeProcessor(), "1234");
        PaymentResult r1 = ccViaStripe.pay(amount);
        System.out.println("1) " + r1);

        var ccViaBank = new CreditCardPayment(new MockBankProcessor(), "1234");
        PaymentResult r2 = ccViaBank.pay(amount);
        System.out.println("2) " + r2);

        // PayPal payment using PayPal processor:
        var paypal = new PayPalPayment(new PayPalProcessor(), "manoj@example.com");
        PaymentResult r3 = paypal.pay(amount);
        System.out.println("3) " + r3);

        // Demonstrate failure:
        var bigCharge = new CreditCardPayment(new MockBankProcessor(), "9876");
        PaymentResult r4 = bigCharge.pay(new BigDecimal("5000.00"));
        System.out.println("4) " + r4);
    }
}
