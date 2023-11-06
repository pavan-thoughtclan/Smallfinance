package com.microFinance1.services;

import com.microFinance1.dtos.inputs.RecurringDepositInputDto;
import com.microFinance1.dtos.outputs.RecurringDepositOutputDto;
import io.micronaut.http.HttpResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface RecurringDepositService {
    Mono<RecurringDepositOutputDto> saveRd(RecurringDepositInputDto recurringDepositInputDto);

    Mono<RecurringDepositOutputDto> getById(UUID id);

    Flux<RecurringDepositOutputDto> getAll();

    Flux<RecurringDepositOutputDto> getAllRecurringDeposit(Long accNo);

    Mono<Double> getTotalMoneyInvested(Long accNo);

    Flux<RecurringDepositOutputDto> getByStatus(Long accNo);
}
