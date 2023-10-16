package com.tc.training.controller;

import com.tc.training.dtos.outputdto.UserOutputDto;
import com.tc.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("")
    @PreAuthorize("hasRole('MANAGER')")
    public Flux<UserOutputDto> getAllUsers(){
        return userService.getAll();

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public Mono<UserOutputDto> getById(@PathVariable UUID id){
        return userService.getById(id);
    }



}
