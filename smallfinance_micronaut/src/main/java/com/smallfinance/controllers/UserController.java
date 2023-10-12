package com.smallfinance.controllers;

import com.smallfinance.dtos.outputs.UserOutput;
import com.smallfinance.services.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@Controller("/users")

public class UserController {
    @Inject
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Get("/{id}")
    public UserOutput getById(@PathVariable UUID id) {
        return userService.getById(id);
    }

    @Get
    public List<UserOutput> getAll() {
        return userService.getAll();
    }
}
