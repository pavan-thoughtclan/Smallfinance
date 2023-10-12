package com.smallfinance.mapper;

import com.smallfinance.dtos.inputs.SlabInput;
import com.smallfinance.dtos.outputs.SlabOutput;
import com.smallfinance.entities.Slabs;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface SlabMapper {
    Slabs mapToSlabs(SlabInput slabInputDto);

    SlabOutput mapToSlabOutputDto(Slabs slabs);
}
