package com.smallfinance.controllers;

import com.smallfinance.dtos.outputs.UserOutput;
import com.smallfinance.services.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

/**
 * UserController handles operations related to user APIs.
 */
@Controller("/users")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class UserController {
    @Inject
    private final UserService userService;
    @Inject
    private final SecurityService securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    /**
     * Get user information by their ID.
     * @param id The ID of the user
     * @return UserOutput
     */
    @Get("/{id}")
    public UserOutput getById(@PathVariable UUID id) {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
            return userService.getById(id);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get a list of all users.
     * @return List of UserOutput
     */
    @Get
    public List<UserOutput> getAll() {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_MANAGER"))
            return userService.getAll();
        throw new RuntimeException("you are not allowed to access this");
    }
}
