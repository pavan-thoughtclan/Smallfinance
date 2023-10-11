package com.tc.training.service;


import com.tc.training.dtos.outputdto.FDDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DepositService {
    Mono<FDDetails> getDetails(Long accNo);

    Flux<Object> getAccounts(Long accNo);
}
