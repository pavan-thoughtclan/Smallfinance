package com.tc.training.smallfinance.controllers;

import com.tc.training.smallfinance.dtos.inputs.SlabInputDto;
import com.tc.training.smallfinance.dtos.outputs.SlabOutputDto;
import com.tc.training.smallfinance.service.SlabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SlabController handles operations related to slab apis.
 */

@RestController
@RequestMapping("/slabs")
public class SlabController {
    @Autowired
    private SlabService slabService;

    /**
     * Add a new slab based on the provided input.
     * @param slabInputDto SlabInput
     * @return SlabOutput
     */

//    @PostMapping("/add")
    @PostMapping("")
    @PreAuthorize("hasRole('MANAGER')")
    public SlabOutputDto addSlab(@RequestBody SlabInputDto slabInputDto){
        return slabService.addSlab(slabInputDto);
    }

    /**
     * Get a list of all slabs.
     * @return List of SlabOutput
     */

    @GetMapping("")
    @PreAuthorize("hasRole('MANAGER')")
    public List<SlabOutputDto> getAllSlabs(){
        return slabService.getAllSlabs();
    }
}
