package com.marcus.silva.dev.libraryapi.exception.custom;

public class BookAlreadyRented extends RuntimeException{

    public BookAlreadyRented(String message) {
        super(message);
    }
}
