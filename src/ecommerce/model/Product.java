package ecommerce.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Product {
    private final String id;
    private final String name;
    private final BigDecimal price; // price per unit

    public Product(String id, String name, BigDecimal price) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Product id cannot be null/blank");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Product name cannot be null/blank");
        if (price == null) throw new IllegalArgumentException("Product price cannot be null");
        if (price.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Product price cannot be negative");

        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Product{id='%s', name='%s', price=%s}".formatted(id, name, price);
    }
}
