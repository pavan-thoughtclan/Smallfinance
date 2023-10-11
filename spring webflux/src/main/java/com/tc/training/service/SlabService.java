package com.tc.training.service;


import com.tc.training.dtos.inputdto.SlabInputDto;
import com.tc.training.dtos.outputdto.SlabOutputDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface SlabService {
    Mono<SlabOutputDto> addSlab(SlabInputDto slabInputDto);
    Flux<SlabOutputDto> getAllSlabs();
}
