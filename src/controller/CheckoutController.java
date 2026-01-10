package ecommerce.controller;

import ecommerce.model.Order;
import ecommerce.model.ShoppingCart;
import ecommerce.payment.CreditCardPayment;
import ecommerce.payment.DebitPayment;
import ecommerce.payment.PayPalPayment;
import ecommerce.payment.Payment;
import ecommerce.processor.*;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;

public class CheckoutController {

    public enum PaymentType { CREDIT_CARD, DEBIT, PAYPAL }
    public enum ProcessorType { STRIPE, PAYPAL, BANK_MOCK }

    private final ShoppingCart cart;
    private final BigDecimal taxRate; // e.g. 0.13

    public CheckoutController(ShoppingCart cart, BigDecimal taxRate) {
        this.cart = Objects.requireNonNull(cart, "cart cannot be null");
        if (taxRate == null) throw new IllegalArgumentException("taxRate cannot be null");
        if (taxRate.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("taxRate cannot be negative");
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public CheckoutResult checkout(PaymentType paymentType,
                                   ProcessorType processorType,
                                   String identifierLast4OrEmail) {

        if (cart.isEmpty()) {
            return new CheckoutResult(false, null, null, "Cart is empty. Add items before checkout.");
        }

        PaymentProcessor processor = createProcessor(processorType);
        Payment payment = createPayment(paymentType, processor, identifierLast4OrEmail);

        BigDecimal subtotal = cart.subtotal();
        BigDecimal tax = cart.tax(taxRate);
        BigDecimal total = cart.total(taxRate);

        PaymentResult paymentResult = payment.pay(total);
        if (!paymentResult.success()) {
            return new CheckoutResult(false, null, paymentResult, "Payment failed: " + paymentResult.message());
        }

        Order order = new Order(cart.getItems(), subtotal, tax, total);
        cart.clear();

        return new CheckoutResult(true, order, paymentResult, "Checkout successful!");
    }

    private PaymentProcessor createProcessor(ProcessorType type) {
        return switch (type) {
            case STRIPE -> new StripeProcessor();
            case PAYPAL -> new PayPalProcessor();
            case BANK_MOCK -> new MockBankProcessor();
        };
    }

    private Payment createPayment(PaymentType type, PaymentProcessor processor, String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Identifier cannot be null/blank (last4 or email)");
        }

        return switch (type) {
            case CREDIT_CARD -> new CreditCardPayment(processor, sanitize(id));
            case DEBIT -> new DebitPayment(processor, sanitize(id));
            case PAYPAL -> new PayPalPayment(processor, id.trim().toLowerCase(Locale.ROOT));
        };
    }

    private String sanitize(String s) {
        return s.trim();
    }

    public record CheckoutResult(
            boolean success,
            Order order,
            PaymentResult paymentResult,
            String message
    ) {}
}
