package com.tc.training.smallfinance.controllers;

import com.tc.training.smallfinance.dtos.outputs.UserOutputDto;
import com.tc.training.smallfinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    @PreAuthorize("hasRole('MANAGER')")
    public List<UserOutputDto> getAllUsers(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public UserOutputDto getById(@PathVariable("id") UUID id){
        return userService.getById(id);
    }
}
