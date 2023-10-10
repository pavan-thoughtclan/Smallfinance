package com.microFinance1.controllers;

import com.microFinance1.dtos.outputs.UserOutputDto;
import com.microFinance1.services.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Secured(IS_AUTHENTICATED)
@Controller("/user")
public class UserController {
    @Inject
    private UserService userService;

    @Get()
    public Flux<UserOutputDto> getAllUsers(){
        return userService.getAll();
    }

    @Get("/{id}")
    public Mono<UserOutputDto> getById(@PathVariable(name = "id") UUID id){
        return userService.getById(id);
    }
}
