package com.microFinance1.services;

import com.microFinance1.dtos.outputs.FDDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DepositService {
    public Mono<FDDetails> getDetails(Long accNo);

    public Flux<Object> getAccounts(Long accNo);
}
