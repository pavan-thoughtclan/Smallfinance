package com.microFinance1.controllers;

import com.microFinance1.dtos.outputs.UserOutputDto;
import com.microFinance1.services.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.utils.SecurityService;
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
    @Inject
    private SecurityService securityService;

    @Get()
    public Flux<UserOutputDto> getAllUsers(){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return userService.getAll();
        throw new AuthenticationException("you are not allowed to access this");
    }

    @Get("/{id}")
    public Mono<UserOutputDto> getById(@PathVariable(name = "id") UUID id){
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return userService.getById(id);
        throw new AuthenticationException("you are not allowed to access this");
    }
}
