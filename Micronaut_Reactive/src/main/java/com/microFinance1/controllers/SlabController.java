package com.microFinance1.controllers;

import com.microFinance1.dtos.inputs.SlabInputDto;
import com.microFinance1.dtos.outputs.SlabOutputDto;
import com.microFinance1.exceptions.CustomException;
import com.microFinance1.services.SlabService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Controller("/slabs")
@Secured(IS_AUTHENTICATED)
public class SlabController {
    @Inject
    private SlabService slabService;
    @Inject
    private SecurityService securityService;
    @Post()
    public Mono<SlabOutputDto> addSlab(@Body SlabInputDto slabInputDto){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
        return slabService.addSlab(slabInputDto);
        throw new AuthenticationException("you are not allowed to access this");
    }
    @Get()
    public Flux<SlabOutputDto> allSlabs(){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
        return slabService.getAllSlabs();
        throw new AuthenticationException("you are not allowed to access this");
//        else return Flux.error(new AuthenticationException("you are not allowed to access this"));
    }
}
