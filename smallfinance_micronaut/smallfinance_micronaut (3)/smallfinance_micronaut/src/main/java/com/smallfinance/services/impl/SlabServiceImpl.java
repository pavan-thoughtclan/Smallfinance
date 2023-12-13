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

/**
 * Service implementation for managing Slab operations.
 */
@Singleton
public class SlabServiceImpl implements SlabService {
    @Inject
    private SlabMapper slabMapper;

    @Inject
    private SlabRepository slabRepository;

    /**
     * Add a new slab based on the provided input.
     *
     * @param slabInputDto The input data for creating a new slab.
     * @return SlabOutput containing information about the created slab.
     */
    @Override
    public SlabOutput addSlab(SlabInput slabInputDto) {
        Slabs slabs = slabMapper.mapToSlabs(slabInputDto);
        Slabs savedSlabs = slabRepository.save(slabs);
        return slabMapper.mapToSlabOutputDto(savedSlabs);
    }

    /**
     * Get a list of all slabs.
     *
     * @return A list of SlabOutput objects containing information about all slabs.
     */
    @Override
    public List<SlabOutput> getAll() {
        return slabRepository.findAll().stream()
                .map(slabs -> slabMapper.mapToSlabOutputDto(slabs))
                .toList();
    }

}
