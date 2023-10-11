package com.tc.training.service;

import com.tc.training.dtos.inputdto.RecurringDepositInputDto;
import com.tc.training.dtos.outputdto.RecurringDepositOutputDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface RecurringDepositService {
    Mono<RecurringDepositOutputDto> saveRd(RecurringDepositInputDto recurringDepositService);

    Mono<RecurringDepositOutputDto> getById(UUID id);

    Flux<RecurringDepositOutputDto> getAll();

    Flux<RecurringDepositOutputDto> getAllRecurringDeposit(Long accNo);

    Mono<Double> getTotalMoneyInvested(Long accNo);

    Flux<RecurringDepositOutputDto> getByStatus(Long accNo);
}
