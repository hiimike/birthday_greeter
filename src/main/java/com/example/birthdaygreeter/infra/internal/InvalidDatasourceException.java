package com.example.birthdaygreeter.infra.internal;

public class InvalidDatasourceException extends RuntimeException {
    public InvalidDatasourceException(String message) {
        super(message);
    }
}
