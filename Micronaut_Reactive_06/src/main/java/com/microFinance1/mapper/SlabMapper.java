package com.microFinance1.mapper;

import com.microFinance1.dtos.inputs.SlabInputDto;
import com.microFinance1.dtos.outputs.SlabOutputDto;
import com.microFinance1.entities.Slabs;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jsr330")
public interface SlabMapper {
    SlabMapper MAPPER= Mappers.getMapper(SlabMapper.class);

    Slabs mapToSlabs(SlabInputDto slabInputDto);

    SlabOutputDto mapToSlabOutputDto(Slabs slabs);
}
