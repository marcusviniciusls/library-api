package com.marcus.silva.dev.libraryapi.exception.custom;

public class IsbnAlreadyExisting extends RuntimeException{

    public IsbnAlreadyExisting(String message) {
        super(message);
    }
}
