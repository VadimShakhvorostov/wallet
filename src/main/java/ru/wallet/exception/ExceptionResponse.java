package ru.wallet.exception;

import lombok.Getter;

@Getter
public class ExceptionResponse {

    private final String description;

    public ExceptionResponse(String description) {
        this.description = description;
    }
}
