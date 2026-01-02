package ecommerce.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ShoppingCart {
    private final Map<String, CartItem> itemsByProductId = new LinkedHashMap<>();

    public void addProduct(Product product, int quantity) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be > 0");

        CartItem existing = itemsByProductId.get(product.getId());
        if (existing == null) {
            itemsByProductId.put(product.getId(), new CartItem(product, quantity));
        } else {
            existing.setQuantity(existing.getQuantity() + quantity);
        }
    }

    public boolean removeProduct(String productId) {
        if (productId == null || productId.isBlank()) throw new IllegalArgumentException("productId cannot be null/blank");
        return itemsByProductId.remove(productId) != null;
    }

    public void updateQuantity(String productId, int newQuantity) {
        if (productId == null || productId.isBlank()) throw new IllegalArgumentException("productId cannot be null/blank");

        CartItem item = itemsByProductId.get(productId);
        if (item == null) throw new NoSuchElementException("No cart item found for productId: " + productId);

        if (newQuantity <= 0) {
            itemsByProductId.remove(productId); // common ecommerce behavior
        } else {
            item.setQuantity(newQuantity);
        }
    }

    public List<CartItem> getItems() {
        return List.copyOf(itemsByProductId.values());
    }

    public boolean isEmpty() {
        return itemsByProductId.isEmpty();
    }

    public void clear() {
        itemsByProductId.clear();
    }

    public BigDecimal subtotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (CartItem item : itemsByProductId.values()) {
            sum = sum.add(item.lineTotal());
        }
        return sum.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal tax(BigDecimal taxRate) {
        if (taxRate == null) throw new IllegalArgumentException("taxRate cannot be null");
        if (taxRate.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("taxRate cannot be negative");

        BigDecimal tax = subtotal().multiply(taxRate);
        return tax.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal total(BigDecimal taxRate) {
        return subtotal().add(tax(taxRate)).setScale(2, RoundingMode.HALF_UP);
    }
}
