package com.microFinance1.exceptions.handlers;

import com.microFinance1.exceptions.AccountNotFoundException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.server.exceptions.ExceptionHandler;

public class AccountNotFoundExceptionHandler implements ExceptionHandler<AccountNotFoundException,String > {
    @Override
    public String handle(HttpRequest request, AccountNotFoundException exception) {
        return exception.getMessage();
    }
}
