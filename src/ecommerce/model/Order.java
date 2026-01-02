package ecommerce.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class Order {
    private final String orderId;
    private final Instant createdAt;
    private final List<CartItem> items; // snapshot
    private final BigDecimal subtotal;
    private final BigDecimal tax;
    private final BigDecimal total;

    public Order(List<CartItem> cartItemsSnapshot, BigDecimal subtotal, BigDecimal tax, BigDecimal total) {
        if (cartItemsSnapshot == null || cartItemsSnapshot.isEmpty())
            throw new IllegalArgumentException("Order must contain at least one item");
        if (subtotal == null || tax == null || total == null)
            throw new IllegalArgumentException("Amounts cannot be null");

        this.orderId = UUID.randomUUID().toString();
        this.createdAt = Instant.now();
        this.items = List.copyOf(cartItemsSnapshot);
        this.subtotal = subtotal;
        this.tax = tax;
        this.total = total;
    }

    public String getOrderId() { return orderId; }
    public Instant getCreatedAt() { return createdAt; }
    public List<CartItem> getItems() { return items; }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getTax() { return tax; }
    public BigDecimal getTotal() { return total; }
}
