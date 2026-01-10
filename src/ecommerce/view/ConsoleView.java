package ecommerce.view;

import ecommerce.controller.CartController;
import ecommerce.controller.CheckoutController;
import ecommerce.model.CartItem;
import ecommerce.model.Product;
import ecommerce.model.ShoppingCart;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private final CartController cartController;
    private final CheckoutController checkoutController;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleView(CartController cartController, CheckoutController checkoutController) {
        if (cartController == null) throw new IllegalArgumentException("cartController cannot be null");
        if (checkoutController == null) throw new IllegalArgumentException("checkoutController cannot be null");
        this.cartController = cartController;
        this.checkoutController = checkoutController;
    }

    public void start() {
        System.out.println("=== E-Commerce Checkout Engine ===");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1 -> listProducts();
                    case 2 -> addToCartFlow();
                    case 3 -> viewCart();
                    case 4 -> updateQuantityFlow();
                    case 5 -> removeFromCartFlow();
                    case 6 -> checkoutFlow();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }

            System.out.println();
        }

        System.out.println("Goodbye!");
    }

    private void printMenu() {
        System.out.println("""
                1) List products
                2) Add to cart
                3) View cart
                4) Update quantity
                5) Remove from cart
                6) Checkout
                0) Exit
                """);
    }

    private void listProducts() {
        List<Product> catalog = cartController.getCatalog();
        System.out.println("--- Product Catalog ---");
        for (Product p : catalog) {
            System.out.println(p.getId() + " | " + p.getName() + " | $" + p.getPrice());
        }
    }

    private void addToCartFlow() {
        String id = readString("Enter product ID: ");
        int qty = readInt("Enter quantity: ");
        cartController.addToCart(id, qty);
        System.out.println("Added to cart.");
    }

    private void viewCart() {
        ShoppingCart cart = cartController.getCart();
        System.out.println("--- Your Cart ---");

        if (cart.isEmpty()) {
            System.out.println("(empty)");
            return;
        }

        for (CartItem item : cart.getItems()) {
            System.out.println(item.getProduct().getId() + " | " + item);
        }

        BigDecimal taxRate = checkoutController.getTaxRate();
        System.out.println("Subtotal: $" + cart.subtotal());
        System.out.println("Tax:      $" + cart.tax(taxRate));
        System.out.println("Total:    $" + cart.total(taxRate));
    }

    private void updateQuantityFlow() {
        String id = readString("Enter product ID: ");
        int qty = readInt("Enter new quantity (0 removes): ");
        cartController.updateQuantity(id, qty);
        System.out.println("Quantity updated.");
    }

    private void removeFromCartFlow() {
        String id = readString("Enter product ID: ");
        boolean removed = cartController.removeFromCart(id);
        System.out.println(removed ? "Removed." : "Not found in cart.");
    }

    private void checkoutFlow() {
        ShoppingCart cart = cartController.getCart();
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Add items first.");
            return;
        }

        // Choose Payment Type
        System.out.println("""
                Payment Type:
                1) Credit Card
                2) Debit
                3) PayPal
                """);
        int pt = readInt("Choose payment type: ");
        CheckoutController.PaymentType paymentType = switch (pt) {
            case 1 -> CheckoutController.PaymentType.CREDIT_CARD;
            case 2 -> CheckoutController.PaymentType.DEBIT;
            case 3 -> CheckoutController.PaymentType.PAYPAL;
            default -> throw new IllegalArgumentException("Invalid payment type.");
        };

        // Choose Processor
        System.out.println("""
                Processor:
                1) Stripe
                2) PayPal
                3) Mock Bank
                """);
        int pr = readInt("Choose processor: ");
        CheckoutController.ProcessorType processorType = switch (pr) {
            case 1 -> CheckoutController.ProcessorType.STRIPE;
            case 2 -> CheckoutController.ProcessorType.PAYPAL;
            case 3 -> CheckoutController.ProcessorType.BANK_MOCK;
            default -> throw new IllegalArgumentException("Invalid processor type.");
        };

        // Identifier
        String identifier = switch (paymentType) {
            case PAYPAL -> readString("Enter PayPal email: ");
            case CREDIT_CARD -> readString("Enter card last 4 digits (e.g., 1234): ");
            case DEBIT -> readString("Enter account last 4 digits (e.g., 9876): ");
        };

        var result = checkoutController.checkout(paymentType, processorType, identifier);

        System.out.println(result.message());
        if (result.success()) {
            ReceiptPrinter.print(result.order(), result.paymentResult());
        } else if (result.paymentResult() != null) {
            System.out.println("Transaction: " + result.paymentResult().transactionId());
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
