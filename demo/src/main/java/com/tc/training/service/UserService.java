package com.tc.training.service;

import com.tc.training.dtos.inputdto.AccountDetailsInputDto;
import com.tc.training.dtos.inputdto.LoginInputDto;
import com.tc.training.dtos.outputdto.LoginOutputDto;
import com.tc.training.dtos.outputdto.UserOutputDto;
import com.tc.training.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;


public interface UserService {

    public Mono<User> addUser(AccountDetailsInputDto accountDetailsInputDto);

    Flux<UserOutputDto> getAll();
                                                            //write it afterwards
    Mono<UserOutputDto> getById(UUID id);



}
