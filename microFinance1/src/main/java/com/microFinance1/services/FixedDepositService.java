package com.microFinance1.services;

import com.microFinance1.dtos.inputs.FixedDepositInputDto;
import com.microFinance1.dtos.outputs.FDDetails;
import com.microFinance1.dtos.outputs.FixedDepositOutputDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FixedDepositService {
    Mono<FixedDepositOutputDto> createFixedDeposit(FixedDepositInputDto fixedDepositInputDto);
    Flux<FixedDepositOutputDto> allFixedDeposit(Long accNo);

    Mono<FDDetails> getFDDetails(Long accNo);

    Mono<FixedDepositOutputDto> breakFixedDeposit(String  uid);

    Flux<FixedDepositOutputDto> getAll();

    Mono<FixedDepositOutputDto> getById(UUID id);
    Flux<FixedDepositOutputDto> getAllActive(Long accNo);


}
