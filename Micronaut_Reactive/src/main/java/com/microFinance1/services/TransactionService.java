package com.microFinance1.services;

import com.microFinance1.dtos.inputs.TransactionInputDto;
import com.microFinance1.dtos.outputs.TransactionOutputDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    Mono<TransactionOutputDto> deposit(TransactionInputDto transactionInputDto, Long accountNumber);

    Flux<TransactionOutputDto> getAllTransactions(LocalDate date1, LocalDate date2, String type, Long accNo);
}
