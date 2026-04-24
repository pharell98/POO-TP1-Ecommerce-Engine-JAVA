package main.java.com.inovabraintech.catalogue;


import main.java.com.inovabraintech.catalogue.entity.Product;
import main.java.com.inovabraintech.catalogue.exception.CurrencyMismatchException;
import main.java.com.inovabraintech.catalogue.exception.InvalidCurrencyException;
import main.java.com.inovabraintech.catalogue.exception.InvalidSkuException;
import main.java.com.inovabraintech.catalogue.valueobject.Money;
import main.java.com.inovabraintech.catalogue.valueobject.SKU;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== DÉMONSTRATION DE ROBUSTESSE ===\n");

        // -----------------------------------------------
        // CAS 1 : Création valide
        // -----------------------------------------------
        System.out.println("--- CAS 1 : Création valide ---");
        try {
            SKU sku = new SKU("TEC-1234");
            Money price = new Money(new BigDecimal("15000"), "FCFA");
            Product product = new Product(sku, "Casque Bluetooth", price);
            System.out.println("✅ Produit créé : " + product);
        } catch (Exception e) {
            System.out.println("❌ FAIL : " + e.getMessage());
        }

        // -----------------------------------------------
        // CAS 2 : Montant négatif
        // -----------------------------------------------
        System.out.println("\n--- CAS 2 : Montant négatif ---");
        try {
            new Money(new BigDecimal("-500"), "FCFA");
            System.out.println("❌ FAIL : l'exception n'a pas été levée !");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Protection OK : " + e.getMessage());
        }

        // -----------------------------------------------
        // CAS 3 : Devise invalide
        // -----------------------------------------------
        System.out.println("\n--- CAS 3 : Devise invalide (USD) ---");
        try {
            new Money(new BigDecimal("100"), "USD");
            System.out.println("❌ FAIL : l'exception n'a pas été levée !");
        } catch (InvalidCurrencyException e) {
            System.out.println("✅ Protection OK : " + e.getMessage());
        }

        // -----------------------------------------------
        // CAS 4 : Devise null
        // -----------------------------------------------
        System.out.println("\n--- CAS 4 : Devise null ---");
        try {
            new Money(new BigDecimal("100"), null);
            System.out.println("❌ FAIL : l'exception n'a pas été levée !");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Protection OK : " + e.getMessage());
        }

        // -----------------------------------------------
        // CAS 5 : SKU format invalide
        // -----------------------------------------------
        System.out.println("\n--- CAS 5 : SKU format invalide (AB-12) ---");
        try {
            new SKU("AB-12");
            System.out.println("❌ FAIL : l'exception n'a pas été levée !");
        } catch (InvalidSkuException e) {
            System.out.println("✅ Protection OK : " + e.getMessage());
        }

        // -----------------------------------------------
        // CAS 6 : SKU null
        // -----------------------------------------------
        System.out.println("\n--- CAS 6 : SKU null ---");
        try {
            new SKU(null);
            System.out.println("❌ FAIL : l'exception n'a pas été levée !");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Protection OK : " + e.getMessage());
        }

        // -----------------------------------------------
        // CAS 7 : Nom de produit vide
        // -----------------------------------------------
        System.out.println("\n--- CAS 7 : Nom de produit vide ---");
        try {
            new Product(
                    new SKU("ABC-1234"),
                    "   ",
                    new Money(new BigDecimal("100"), "FCFA")
            );
            System.out.println("❌ FAIL : l'exception n'a pas été levée !");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Protection OK : " + e.getMessage());
        }

        // -----------------------------------------------
        // CAS 8 : SKU null dans Product
        // -----------------------------------------------
        System.out.println("\n--- CAS 8 : SKU null dans Product ---");
        try {
            new Product(
                    null,
                    "Test",
                    new Money(new BigDecimal("100"), "FCFA")
            );
            System.out.println("❌ FAIL : l'exception n'a pas été levée !");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Protection OK : " + e.getMessage());
        }

        // -----------------------------------------------
        // CAS 9 : Price null dans Product
        // -----------------------------------------------
        System.out.println("\n--- CAS 9 : Price null dans Product ---");
        try {
            new Product(
                    new SKU("ABC-1234"),
                    "Test",
                    null
            );
            System.out.println("❌ FAIL : l'exception n'a pas été levée !");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Protection OK : " + e.getMessage());
        }

        // -----------------------------------------------
        // CAS 10 : Remise à 0 (hors plage)
        // -----------------------------------------------
        System.out.println("\n--- CAS 10 : Remise à 0% (hors plage) ---");
        try {
            Product p = new Product(
                    new SKU("ABC-1234"),
                    "Test",
                    new Money(new BigDecimal("1000"), "FCFA")
            );
            p.applyDiscount(BigDecimal.ZERO);
            System.out.println("❌ FAIL : l'exception n'a pas été levée !");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Protection OK : " + e.getMessage());
        }

        // -----------------------------------------------
        // CAS 11 : Remise à 101 (hors plage)
        // -----------------------------------------------
        System.out.println("\n--- CAS 11 : Remise à 101% (hors plage) ---");
        try {
            Product p = new Product(
                    new SKU("ABC-1234"),
                    "Test",
                    new Money(new BigDecimal("1000"), "FCFA")
            );
            p.applyDiscount(new BigDecimal("101"));
            System.out.println("❌ FAIL : l'exception n'a pas été levée !");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Protection OK : " + e.getMessage());
        }

        // -----------------------------------------------
        // CAS 12 : Addition FCFA + EUR
        // -----------------------------------------------
        System.out.println("\n--- CAS 12 : Addition FCFA + EUR ---");
        try {
            Money fcfa = new Money(new BigDecimal("1000"), "FCFA");
            Money eur  = new Money(new BigDecimal("100"),  "EUR");
            fcfa.add(eur);
            System.out.println("❌ FAIL : l'exception n'a pas été levée !");
        } catch (CurrencyMismatchException e) {
            System.out.println("✅ Protection OK : " + e.getMessage());
        }

        // -----------------------------------------------
        // CAS 13 : Remise valide 20% — avant / après
        // -----------------------------------------------
        System.out.println("\n--- CAS 13 : Remise valide de 20% ---");
        try {
            Product p = new Product(
                    new SKU("ABC-1234"),
                    "MacBook",
                    new Money(new BigDecimal("1000"), "FCFA")
            );
            System.out.println("   Avant  : " + p.getPrice());
            p.applyDiscount(new BigDecimal("20"));
            System.out.println("   Après  : " + p.getPrice());
            System.out.println("✅ CAS 13 OK");
        } catch (Exception e) {
            System.out.println("❌ FAIL : " + e.getMessage());
        }

        System.out.println("\n=== TOUS LES INVARIANTS SONT RESPECTÉS ✅ ===");
    }
}