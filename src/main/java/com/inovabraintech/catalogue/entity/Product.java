package main.java.com.inovabraintech.catalogue.entity;

import main.java.com.inovabraintech.catalogue.valueobject.Money;
import main.java.com.inovabraintech.catalogue.valueobject.SKU;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private final UUID id;
    private final SKU sku;
    private String name;
    private Money price;

    public Product(SKU sku, String name, Money price) {

        if (sku == null) {
            throw new IllegalArgumentException("Le SKU ne peut pas être null");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide");
        }

        if (price == null) {
            throw new IllegalArgumentException("Le prix ne peut pas être null");
        }

        this.id = UUID.randomUUID();
        this.sku = sku;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public SKU getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public void applyDiscount(BigDecimal percentage) {
        if (percentage == null) {
            throw new IllegalArgumentException("Le pourcentage ne peut pas etre null");
        }

        if(percentage.compareTo(new BigDecimal("0.01")) < 0 || percentage.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Pourcentage invalide (0.1 - 100)");
        }

        BigDecimal discount = price.amount().multiply(percentage).divide(new BigDecimal("100"));
        BigDecimal newPrice = price.amount().subtract(discount);
        this.price = new Money(newPrice, price.currency());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", sku=" + sku +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}