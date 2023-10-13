package com.smallfinance.controllers;

import com.smallfinance.dtos.inputs.SlabInput;
import com.smallfinance.dtos.outputs.SlabOutput;
import com.smallfinance.services.SlabService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;

import java.util.List;

/**
 * SlabController handles operations related to slab apis.
 */
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("slabs")
public class SlabController {
    @Inject
    private SlabService slabService;
    @Inject
    private final SecurityService securityService;

    public SlabController(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * Add a new slab based on the provided input.
     * @param slabInputDto SlabInput
     * @return SlabOutput
     */
    @Post
    public SlabOutput addSlab(@Body SlabInput slabInputDto) {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return slabService.addSlab(slabInputDto);
        throw new RuntimeException("you are not allowed to access this");
    }

    /**
     * Get a list of all slabs.
     * @return List of SlabOutput
     */
    @Get
    public List<SlabOutput> getAll() {
        if(securityService.getAuthentication().get().getAttributes().get("roles").equals("ROLE_CUSTOMER"))
            return slabService.getAll();
        throw new RuntimeException("you are not allowed to access this");
    }

}
