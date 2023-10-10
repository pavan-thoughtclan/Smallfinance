package com.microFinance1.controllers;

import com.microFinance1.dtos.inputs.SlabInputDto;
import com.microFinance1.dtos.outputs.SlabOutputDto;
import com.microFinance1.services.SlabService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Controller("/slabs")
@Secured(IS_AUTHENTICATED)
public class SlabController {
    @Inject
    private SlabService slabService;
    @Post("/add")
    public Mono<SlabOutputDto> addSlab(@Body SlabInputDto slabInputDto){
        return slabService.addSlab(slabInputDto);
    }
    @Get()
    public Flux<SlabOutputDto> allSlabs(){
        return slabService.getAllSlabs();
    }
}
