package com.microFinance1.exceptions.handlers;

import com.microFinance1.exceptions.AmountNotSufficientException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.server.exceptions.ExceptionHandler;

public class AmountNotSufficientExceptionHandler implements ExceptionHandler<AmountNotSufficientException,String > {
    @Override
    public String handle(HttpRequest request, AmountNotSufficientException exception) {
        return exception.getMessage();
    }
}
