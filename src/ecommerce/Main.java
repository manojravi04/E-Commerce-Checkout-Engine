package ecommerce;

import ecommerce.controller.CartController;
import ecommerce.controller.CheckoutController;
import ecommerce.model.Product;
import ecommerce.model.ShoppingCart;
import ecommerce.view.ConsoleView;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // In-memory catalog (simple + fine for this project)
        List<Product> catalog = List.of(
                new Product("P100", "Mechanical Keyboard", new BigDecimal("129.99")),
                new Product("P200", "Wireless Mouse", new BigDecimal("39.99")),
                new Product("P300", "USB-C Hub", new BigDecimal("24.50")),
                new Product("P400", "Laptop Stand", new BigDecimal("44.00"))
        );

        ShoppingCart cart = new ShoppingCart();
        CartController cartController = new CartController(cart, catalog);

        BigDecimal taxRate = new BigDecimal("0.13"); // Ontario HST (example)
        CheckoutController checkoutController = new CheckoutController(cart, taxRate);

        new ConsoleView(cartController, checkoutController).start();
    }
}
