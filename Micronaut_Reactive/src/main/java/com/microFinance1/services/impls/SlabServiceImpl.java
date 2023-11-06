package com.microFinance1.services.impls;

import com.microFinance1.dtos.inputs.SlabInputDto;
import com.microFinance1.dtos.outputs.SlabOutputDto;
import com.microFinance1.entities.Slabs;
import com.microFinance1.mapper.SlabMapper;
import com.microFinance1.repository.SlabRepository;
import com.microFinance1.services.SlabService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Singleton
public class SlabServiceImpl implements SlabService {
    @Inject
    private SlabMapper slabMapper;
    @Inject
    private SlabRepository slabRepository;
    @Override
    public Mono<SlabOutputDto> addSlab(SlabInputDto slabInputDto) {
        Slabs slabs=slabMapper.mapToSlabs(slabInputDto);
        return slabRepository.save(slabs).map(slabs1 -> {
            return slabMapper.mapToSlabOutputDto(slabs1);
        });
    }

    @Override
    public Flux<SlabOutputDto> getAllSlabs() {
        return slabRepository.findAll().map(slabs -> {
            return slabMapper.mapToSlabOutputDto(slabs);
        });
    }
}
