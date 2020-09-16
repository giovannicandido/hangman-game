package com.dell.hangman.infrastructure.config;

public class HangmanDomainExceptionError {
    private final String message;

    public HangmanDomainExceptionError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
