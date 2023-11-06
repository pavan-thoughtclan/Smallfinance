package com.microFinance1.services;

import com.microFinance1.dtos.inputs.LoanInputDto;
import com.microFinance1.dtos.outputs.LoanOutputDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface LoanService {
    Mono<LoanOutputDto> addLoan(LoanInputDto loanInputDto);

    Mono<LoanOutputDto> getById(UUID id);

    Flux<LoanOutputDto> getAllByUser(Long accNo);

    Flux<LoanOutputDto> getAll();

    Mono<LoanOutputDto> setLoan(UUID id,String status);


    Flux<LoanOutputDto> getByType(Long accNo, String type);

    Mono<Double> getTotalLoanAmount(Long accNo);

    Flux<LoanOutputDto> getAllPending();

    Flux<LoanOutputDto> getAllByNotPending();

    Flux<LoanOutputDto> getAllByStatus(String s);
}
