package main.java.com.inovabraintech.catalogue.valueobject;

import main.java.com.inovabraintech.catalogue.exception.CurrencyMismatchException;
import main.java.com.inovabraintech.catalogue.exception.InvalidCurrencyException;

import java.math.BigDecimal;
import java.util.Set;

public record Money(BigDecimal amount, String currency) {
    private static final Set<String> allowedCurrencies =
            Set.of("FCFA", "EUR");

    public Money {
        if (amount == null) {
            throw new IllegalArgumentException("Le montant ne peut pas être null.");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("le montant ne peut pas etre negatif");
        }

        if (currency == null || currency.isBlank()) {
            throw new InvalidCurrencyException("null");
        }

        currency = currency.trim().toUpperCase();

        if (!allowedCurrencies.contains(currency)) {
            throw new InvalidCurrencyException(currency);
        }
        // ce code peut marcher mais a des limite si on ajoute plus de devise
       // if(!currency.equals("FCFA") && !currency.equals("EUR")){
       //     throw new InvalidCurrencyException(currency);
       // }
    }
    public Money add(Money other) {
        if(other==null){
            throw new IllegalArgumentException("Le dividende ne peut pas etre null");
        }
        if(!this.currency.equals(other.currency())){
            throw new IllegalArgumentException("L'autre montant ne peut pas être null");
        }
        return new Money(this.amount.add(other.amount()), this.currency);
    }
}