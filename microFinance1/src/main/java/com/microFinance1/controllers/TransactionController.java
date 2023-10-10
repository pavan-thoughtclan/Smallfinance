package com.microFinance1.controllers;

import com.microFinance1.dtos.inputs.TransactionInputDto;
import com.microFinance1.dtos.outputs.TransactionOutputDto;
import com.microFinance1.entities.Transaction;
import com.microFinance1.services.TransactionService;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Secured(IS_AUTHENTICATED)
@Controller("/transaction")
public class TransactionController {
    @Inject
    private TransactionService transactionService;
    @Post("/transfer")
    public Mono<TransactionOutputDto> transfer(@Body TransactionInputDto transactionInputDto, @QueryValue Long accNo ){
        return transactionService.deposit(transactionInputDto,accNo);
    }
   // @Get("/allTransactions{?input*}")
    @Get()
    public Flux<TransactionOutputDto> allTransaction( @QueryValue LocalDate date1, @QueryValue LocalDate date2,@QueryValue String type, @QueryValue Long accNo){
        return transactionService.getAllTransactions(date1,date2,type,accNo);
    }
}
