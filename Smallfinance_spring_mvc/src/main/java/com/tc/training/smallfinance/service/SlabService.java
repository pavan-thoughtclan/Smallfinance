package com.tc.training.smallfinance.service;

import com.tc.training.smallfinance.dtos.inputs.SlabInputDto;
import com.tc.training.smallfinance.dtos.outputs.SlabOutputDto;

import java.util.List;

public interface SlabService {
    SlabOutputDto addSlab(SlabInputDto slabInputDto);

    List<SlabOutputDto> getAllSlabs();
}
