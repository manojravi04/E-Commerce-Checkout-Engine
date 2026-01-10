package ecommerce.view;

import ecommerce.model.CartItem;
import ecommerce.model.Order;
import ecommerce.processor.PaymentResult;

public final class ReceiptPrinter {
    private ReceiptPrinter() {}

    public static void print(Order order, PaymentResult paymentResult) {
        System.out.println("\n=== RECEIPT ===");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Created:  " + order.getCreatedAt());
        System.out.println("\nItems:");
        for (CartItem item : order.getItems()) {
            System.out.println("- " + item.getProduct().getName() + " x" + item.getQuantity() + " = $" + item.lineTotal());
        }
        System.out.println("\nSubtotal: $" + order.getSubtotal());
        System.out.println("Tax:      $" + order.getTax());
        System.out.println("Total:    $" + order.getTotal());
        System.out.println("\nPayment:  " + paymentResult.message());
        System.out.println("Txn ID:   " + paymentResult.transactionId());
        System.out.println("==============\n");
    }
}
