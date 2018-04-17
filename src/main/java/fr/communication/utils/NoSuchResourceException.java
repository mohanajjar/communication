package fr.communication.utils;

public class NoSuchResourceException extends Exception {

    String message;

    public NoSuchResourceException(String message) {
        super(message);
        this.message = message;
    }
}
