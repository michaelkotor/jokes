package com.example.jokes.exception;

public class NoSuchCategoryException extends RuntimeException {
    public NoSuchCategoryException(String message) {
        super(message);
    }
}
