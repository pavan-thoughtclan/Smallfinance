package com.tc.training.smallfinance.controllers;

import com.tc.training.smallfinance.dtos.inputs.SlabInputDto;
import com.tc.training.smallfinance.dtos.outputs.SlabOutputDto;
import com.tc.training.smallfinance.service.SlabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slabs")
public class SlabController {
    @Autowired
    private SlabService slabService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('MANAGER')")
    public SlabOutputDto addSlab(@RequestBody SlabInputDto slabInputDto){
        return slabService.addSlab(slabInputDto);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('MANAGER')")
    public List<SlabOutputDto> getAllSlabs(){
        return slabService.getAllSlabs();
    }
}
