package ru.vladikshk.library.util;

public class EntityNotModifiedException extends RuntimeException {
    public EntityNotModifiedException(String message) {
        super(message);
    }
}
