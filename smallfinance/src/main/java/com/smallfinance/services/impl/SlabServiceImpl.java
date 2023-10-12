package com.smallfinance.services.impl;

import com.smallfinance.dtos.inputs.SlabInput;
import com.smallfinance.dtos.outputs.SlabOutput;
import com.smallfinance.entities.Slabs;
import com.smallfinance.mapper.SlabMapper;
import com.smallfinance.repositories.SlabRepository;
import com.smallfinance.services.SlabService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class SlabServiceImpl implements SlabService {
    @Inject
    private SlabMapper slabMapper;

    @Inject
    private SlabRepository slabRepository;

    @Override
    public SlabOutput addSlab(SlabInput slabInputDto) {
        Slabs slabs = slabMapper.mapToSlabs(slabInputDto);
        Slabs savedSlabs = slabRepository.save(slabs);
        return slabMapper.mapToSlabOutputDto(savedSlabs);
    }

    @Override
    public List<SlabOutput> getAll() {
        return slabRepository.findAll().stream()
                .map(slabs -> slabMapper.mapToSlabOutputDto(slabs))
                .toList();
    }

}
