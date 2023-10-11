package com.tc.training.service;

import com.tc.training.dtos.inputdto.TransactionInputDto;
import com.tc.training.dtos.outputdto.TransactionOutputDto;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    Mono<TransactionOutputDto> deposit(TransactionInputDto transactionInputDto, Long accountNumber);

    Flux<TransactionOutputDto> getAllTransactions(LocalDate date1, LocalDate date2, String type, Long accNo);
}
