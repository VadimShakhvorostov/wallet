package ru.wallet.exception;

public class BalanceException extends RuntimeException {
    public BalanceException(String message) {
        super(message);
    }
}
