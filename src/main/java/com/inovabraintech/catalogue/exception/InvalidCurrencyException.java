package main.java.com.inovabraintech.catalogue.exception;

public class InvalidCurrencyException extends RuntimeException{
    public InvalidCurrencyException(String currency) {
        super("Devise invalide: '" + currency + "' Acceptes : FCFA, EUR.");
    }
}