package com.microFinance1.exceptions.handlers;

import com.microFinance1.exceptions.KycNotCompletedException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.Order;
import io.micronaut.core.order.Ordered;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.server.exceptions.ExceptionHandler;
//@Requires(classes = { KycNotCompletedException.class, ExceptionHandler.class })
@Order(Ordered.HIGHEST_PRECEDENCE)
public class KycNotCompletedExceptionHandler implements ExceptionHandler<KycNotCompletedException,String > {
    @Override
    public String handle(HttpRequest request, KycNotCompletedException exception) {
        return exception.getMessage();
    }
}
