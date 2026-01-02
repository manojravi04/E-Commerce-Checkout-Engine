package ecommerce;

import ecommerce.model.Product;
import ecommerce.model.ShoppingCart;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        Product p1 = new Product("P100", "Mechanical Keyboard", new BigDecimal("129.99"));
        Product p2 = new Product("P200", "Mouse", new BigDecimal("39.99"));

        cart.addProduct(p1, 1);
        cart.addProduct(p2, 2);

        BigDecimal taxRate = new BigDecimal("0.13"); // 13%

        System.out.println("Subtotal: " + cart.subtotal());
        System.out.println("Tax: " + cart.tax(taxRate));
        System.out.println("Total: " + cart.total(taxRate));
    }
}