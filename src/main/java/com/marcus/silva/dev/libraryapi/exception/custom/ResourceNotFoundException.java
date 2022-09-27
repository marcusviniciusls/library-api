package com.marcus.silva.dev.libraryapi.exception.custom;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}