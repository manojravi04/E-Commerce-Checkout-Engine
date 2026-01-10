package ecommerce.controller;

import ecommerce.model.Product;
import ecommerce.model.ShoppingCart;

import java.util.List;
import java.util.NoSuchElementException;

public class CartController {
    private final ShoppingCart cart;
    private final List<Product> catalog;

    public CartController(ShoppingCart cart, List<Product> catalog) {
        if (cart == null) throw new IllegalArgumentException("cart cannot be null");
        if (catalog == null || catalog.isEmpty()) throw new IllegalArgumentException("catalog cannot be null/empty");
        this.cart = cart;
        this.catalog = catalog;
    }

    public List<Product> getCatalog() {
        return List.copyOf(catalog);
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public Product findProductById(String productId) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("productId cannot be null/blank");
        }

        return catalog.stream()
                .filter(p -> p.getId().equalsIgnoreCase(productId.trim()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No product found with id: " + productId));
    }

    public void addToCart(String productId, int quantity) {
        Product product = findProductById(productId);
        cart.addProduct(product, quantity);
    }

    public boolean removeFromCart(String productId) {
        return cart.removeProduct(productId);
    }

    public void updateQuantity(String productId, int newQuantity) {
        cart.updateQuantity(productId, newQuantity);
    }

    public void clearCart() {
        cart.clear();
    }
}
