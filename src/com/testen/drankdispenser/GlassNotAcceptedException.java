package com.testen.drankdispenser;

public class GlassNotAcceptedException extends Exception {

    private DrinkTypes type;

    public GlassNotAcceptedException(DrinkTypes type, String message) {
        super(message);
        this.type = type;
    }

    public GlassNotAcceptedException(DrinkTypes type) {
        super(type.name());
    }
}
