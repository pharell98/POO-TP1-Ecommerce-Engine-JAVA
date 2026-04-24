package main.java.com.inovabraintech.catalogue.valueobject;

import main.java.com.inovabraintech.catalogue.exception.InvalidSkuException;

import java.util.regex.Pattern;

public record SKU(String value) {

    private static final Pattern SKU_PATTERN = Pattern.compile("^[A-Z]{3}-[0-9]{4,6}$");

    public SKU {
        if (value==null){
            throw new IllegalArgumentException("Le SKU ne peut pas être null");
        }
        value = value.trim().toUpperCase();
       if(!SKU_PATTERN.matcher(value).matches()){
           throw new InvalidSkuException(value);
       }
    }
    @Override
    public String toString(){
        return value;
    }
}