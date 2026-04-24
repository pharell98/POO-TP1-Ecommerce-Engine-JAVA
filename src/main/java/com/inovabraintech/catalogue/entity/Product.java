package main.java.com.inovabraintech.catalogue.entity;

import main.java.com.inovabraintech.catalogue.valueobject.Money;
import main.java.com.inovabraintech.catalogue.valueobject.SKU;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
public class Product {

    private final UUID id;
    private final SKU sku;
    private final String name;
    private Money price;

    public Product(SKU sku, String name, Money price) {
        if (sku == null) {
            throw new IllegalArgumentException("Le SKU ne peut pas être null.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        if (price == null) {
            throw new IllegalArgumentException("Le prix ne peut pas être null.");
        }

        this.id = UUID.randomUUID();
        this.sku = sku;
        this.name = name.strip(); // strip() > trim() : gère aussi les espaces Unicode
        this.price = price;
    }

    public UUID getId()     { return id; }
    public SKU getSku()     { return sku; }
    public String getName() { return name; }
    public Money getPrice() { return price; }

    public void applyDiscount(BigDecimal percentage) {
        if (percentage == null) {
            throw new IllegalArgumentException("Le pourcentage ne peut pas être null.");
        }

        // ✅ 0.1 et non 0.01
        BigDecimal min = new BigDecimal("0.1");
        BigDecimal max = new BigDecimal("100");

        if (percentage.compareTo(min) < 0 || percentage.compareTo(max) > 0) {
            throw new IllegalArgumentException(
                    "Pourcentage invalide : " + percentage + ". Attendu entre 0.1 et 100."
            );
        }

        // ✅ divide() avec échelle et RoundingMode → jamais d'exception
        BigDecimal discount = price.amount()
                .multiply(percentage)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        BigDecimal newAmount = price.amount().subtract(discount);

        // ✅ nouveau Money → immuabilité respectée
        this.price = new Money(newAmount, price.currency());
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