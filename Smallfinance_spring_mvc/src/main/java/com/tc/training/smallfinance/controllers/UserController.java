package com.tc.training.smallfinance.controllers;

import com.tc.training.smallfinance.dtos.outputs.UserOutputDto;
import com.tc.training.smallfinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * UserController handles operations related to user APIs.
 */

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Get a list of all users.
     * @return List of UserOutput
     */

    @GetMapping("")
    @PreAuthorize("hasRole('MANAGER')")
    public List<UserOutputDto> getAllUsers(){
        return userService.getAll();
    }

    /**
     * Get user information by their ID.
     * @param id The ID of the user
     * @return UserOutput
     */

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public UserOutputDto getById(@PathVariable("id") UUID id){
        return userService.getById(id);
    }
}
