package com.tc.training.smallfinance.service.Impl;

import com.tc.training.smallfinance.dtos.inputs.SlabInputDto;
import com.tc.training.smallfinance.dtos.outputs.SlabOutputDto;
import com.tc.training.smallfinance.mapper.SlabMapper;
import com.tc.training.smallfinance.model.Slabs;
import com.tc.training.smallfinance.repository.SlabRepository;
import com.tc.training.smallfinance.service.SlabService;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlabServiceImpl implements SlabService {
//    @Autowired
//    private ModelMapper modelMapper;
    @Autowired
    private SlabRepository slabRepository;

    @Autowired
    private SlabMapper slabMapper;

//    @Override
//    public SlabOutputDto addSlab(SlabInputDto slabInputDto) {
//        Slabs slab = modelMapper.map(slabInputDto, Slabs.class);
//        slab.setTenures(Tenures.valueOf(slabInputDto.getTenures()));
//        slab.setTypeOfTransaction(TypeOfSlab.valueOf(slabInputDto.getTypeOfTransaction()));
//        slabRepository.save(slab);
//        return  modelMapper.map(slab,SlabOutputDto.class);
//    }

    @Override
    public SlabOutputDto addSlab(SlabInputDto slabInputDto) {
//        Slabs slab = SlabMapper.MAPPER.mapToSlabs(slabInputDto);
        Slabs slab = slabMapper.mapToSlabs(slabInputDto);
        slabRepository.save(slab);
//        return SlabMapper.MAPPER.mapToSlabOutputDto(slab);
        return slabMapper.mapToSlabOutputDto(slab);
    }


    @Override
    public List<SlabOutputDto> getAllSlabs() {
//       return slabRepository.findAll().stream().map(slab->modelMapper.map(slab,SlabOutputDto.class)).collect(Collectors.toList());
        return slabRepository.findAll().stream().map(slab->slabMapper.mapToSlabOutputDto(slab)).collect(Collectors.toList());
    }
}
