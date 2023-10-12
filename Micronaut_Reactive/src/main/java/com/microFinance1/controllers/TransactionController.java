package com.microFinance1.controllers;

import com.microFinance1.dtos.inputs.TransactionInputDto;
import com.microFinance1.dtos.outputs.TransactionOutputDto;
import com.microFinance1.services.TransactionService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;
import io.micronaut.security.authentication.AuthenticationException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Secured(IS_AUTHENTICATED)
@Controller("/transaction")
public class TransactionController {
    @Inject
    private TransactionService transactionService;
    @Inject
    private SecurityService securityService;
    @Post("/transfer")
    public Mono<TransactionOutputDto> transfer(@Body TransactionInputDto transactionInputDto, @QueryValue Long accNo )
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return transactionService.deposit(transactionInputDto,accNo);
        throw new AuthenticationException("you are not allowed to access this");
    }
   // @Get("/allTransactions{?input*}")
    @Get()
    public Flux<TransactionOutputDto> allTransaction( @QueryValue LocalDate date1, @QueryValue LocalDate date2,@QueryValue String type, @QueryValue Long accNo)
    {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return transactionService.getAllTransactions(date1,date2,type,accNo);
        throw new AuthenticationException("you are not allowed to access this");
    }
}
