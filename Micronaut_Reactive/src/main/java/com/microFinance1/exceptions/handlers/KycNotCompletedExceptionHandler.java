package com.microFinance1.exceptions.handlers;

import com.microFinance1.exceptions.KycNotCompletedException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.server.exceptions.ExceptionHandler;

public class KycNotCompletedExceptionHandler implements ExceptionHandler<KycNotCompletedException,String > {
    @Override
    public String handle(HttpRequest request, KycNotCompletedException exception) {
        return exception.getMessage();
    }
}
