package main.java.com.inovabraintech.catalogue.exception;

public class InvalidSkuException extends RuntimeException {

    public InvalidSkuException(String value) {
        super("SKU invalide : '" + value + "' Format attendu : AAA-123 ou AAA-1234");
    }
}