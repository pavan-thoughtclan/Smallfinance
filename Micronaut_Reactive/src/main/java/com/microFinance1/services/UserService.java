package com.microFinance1.services;

import com.microFinance1.dtos.inputs.AccountDetailsInputDto;
import com.microFinance1.dtos.outputs.UserOutputDto;
import com.microFinance1.entities.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {
    public Mono<User> addUser(AccountDetailsInputDto accountDetailsInputDto);

    Flux<UserOutputDto> getAll();

    Mono<UserOutputDto> getById(UUID id);

}
