package ru.wallet.exception;

import org.springframework.http.HttpStatus;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFound(final NotFoundException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler
    public ExceptionResponse validationHandler(final MethodArgumentNotValidException exception) {
        String errorMsg = null;
        if (exception.getErrorCount() > 0) {
            List<String> errorDetails = new ArrayList<>();
            for (ObjectError error : exception.getBindingResult().getAllErrors()) {
                errorDetails.add(error.getDefaultMessage());
            }
            if (!errorDetails.isEmpty()) errorMsg = errorDetails.get(0);
        }
        return new ExceptionResponse(errorMsg);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse negativeBalance(final BalanceException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

}
