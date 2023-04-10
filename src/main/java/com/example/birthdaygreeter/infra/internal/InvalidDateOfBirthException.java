package com.example.birthdaygreeter.infra.internal;

public class InvalidDateOfBirthException extends RuntimeException {
    public InvalidDateOfBirthException(String message) {
        super(message);
    }
}
