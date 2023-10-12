package com.smallfinance.controllers;

import com.smallfinance.dtos.inputs.SlabInput;
import com.smallfinance.dtos.outputs.SlabOutput;
import com.smallfinance.services.SlabService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

import java.util.List;

@Controller("slabs")
public class SlabController {
    @Inject
    private SlabService slabService;

    @Post("/add")
    public SlabOutput addSlab(@Body SlabInput slabInputDto) {
        return slabService.addSlab(slabInputDto);
    }

    @Get
    public List<SlabOutput> getAll() {
        return slabService.getAll();
    }

}
