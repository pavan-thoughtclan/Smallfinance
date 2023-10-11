package com.tc.training.service.impl;

import com.tc.training.dtos.inputdto.SlabInputDto;
import com.tc.training.dtos.outputdto.SlabOutputDto;
import com.tc.training.mapper.SlabsMapper;
import com.tc.training.model.Slabs;
import com.tc.training.repo.SlabRepository;
import com.tc.training.service.SlabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlabServiceImpl implements SlabService {

    @Autowired
    private SlabsMapper slabsMapper;
    @Autowired
    private SlabRepository slabRepository;

    @Override
    public Mono<SlabOutputDto> addSlab(SlabInputDto slabInputDto) {
        Slabs slabObj = slabsMapper.SlabInputDtoToSlabs(slabInputDto);
        return slabRepository.save(slabObj)
                .map(slab -> {
                    return slabsMapper.SlabToSlabOutputDto(slab);
                });
    }

    @Override
    public Flux<SlabOutputDto> getAllSlabs() {
        return slabRepository.findAll()
                .map(slab  -> slabsMapper.SlabToSlabOutputDto(slab));
    }
}