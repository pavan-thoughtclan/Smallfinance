package com.microFinance1.exceptions.handlers;

import com.microFinance1.exceptions.CustomException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.server.exceptions.ExceptionHandler;

public class CustomExceptionHandler implements ExceptionHandler<CustomException,String> {
    @Override
    public String handle(HttpRequest request, CustomException exception) {
        return exception.getMessage();
    }
}
