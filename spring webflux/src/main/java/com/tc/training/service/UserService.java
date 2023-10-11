package com.tc.training.service;

import com.tc.training.dtos.inputdto.AccountDetailsInputDto;
import com.tc.training.dtos.outputdto.UserOutputDto;
import com.tc.training.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface UserService {

    public Mono<User> addUser(AccountDetailsInputDto accountDetailsInputDto);

    Flux<UserOutputDto> getAll();
                                                            //write it afterwards
    Mono<UserOutputDto> getById(UUID id);



}
