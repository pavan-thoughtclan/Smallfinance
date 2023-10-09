package com.tc.training.service;


import com.tc.training.dtos.inputdto.FixedDepositInputDto;
import com.tc.training.dtos.outputdto.FDDetails;
import com.tc.training.dtos.outputdto.FixedDepositOutputDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface FixedDepositService {
    Mono<FixedDepositOutputDto> createFixedDeposit(FixedDepositInputDto fixedDepositInputDto);

    Flux<FixedDepositOutputDto> getAllFixedDeposit(Long accNo);

    Mono<FDDetails> getFDDetails(Long accNo);

    Mono<FixedDepositOutputDto> breakFixedDeposit(String id);



    Flux<FixedDepositOutputDto> getAll();

    Mono<FixedDepositOutputDto> getById(UUID id);

    Flux<FixedDepositOutputDto> getAllActive(Long accNo);
//    public FdOutputDto createFd(FdInputDto fdInputDto);
}
