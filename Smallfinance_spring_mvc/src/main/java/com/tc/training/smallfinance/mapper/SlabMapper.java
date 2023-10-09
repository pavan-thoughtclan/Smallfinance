package com.tc.training.smallfinance.mapper;

import com.tc.training.smallfinance.dtos.inputs.SlabInputDto;
import com.tc.training.smallfinance.dtos.outputs.SlabOutputDto;
import com.tc.training.smallfinance.model.Slabs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SlabMapper {
    SlabMapper MAPPER = Mappers.getMapper(SlabMapper.class);


    Slabs mapToSlabs(SlabInputDto slabInputDto);

    @Mapping(target = "slabId", source = "id")
    SlabOutputDto mapToSlabOutputDto(Slabs slabs);
}
