package main.java.com.inovabraintech.catalogue.exception;

public class CurrencyMismatchException extends RuntimeException {
    public CurrencyMismatchException(String currency1, String currency2){
        super("Les devises ne correspondent pas: " + currency1 + " et " + currency2);
    }
}