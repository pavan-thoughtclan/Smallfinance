package com.tc.training.controller;

import com.tc.training.dtos.inputdto.SlabInputDto;
import com.tc.training.dtos.outputdto.SlabOutputDto;
import com.tc.training.service.SlabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/slabs")
public class SlabController {
    @Autowired
    private SlabService slabService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('MANAGER')")
    public Mono<SlabOutputDto> addSlab(@RequestBody SlabInputDto slabInputDto){
        return slabService.addSlab(slabInputDto);
    }

    @GetMapping()
    @PreAuthorize("hasRole('MANAGER')")
    public Flux<SlabOutputDto> getAllSlabs(){
        return slabService.getAllSlabs();
    }
}
