package com.microFinance1.services;

import com.microFinance1.dtos.inputs.SlabInputDto;
import com.microFinance1.dtos.outputs.SlabOutputDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SlabService {
    Mono<SlabOutputDto> addSlab(SlabInputDto slabInputDto);

    Flux<SlabOutputDto> getAllSlabs();
}
