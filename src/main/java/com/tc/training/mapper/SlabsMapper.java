package com.tc.training.mapper;

import com.tc.training.dtos.inputdto.AccountDetailsInputDto;
import com.tc.training.dtos.inputdto.SlabInputDto;
import com.tc.training.dtos.outputdto.AccountDetailsOutputDto;
import com.tc.training.dtos.outputdto.SlabOutputDto;
import com.tc.training.model.AccountDetails;
import com.tc.training.model.Slabs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SlabsMapper {

    SlabsMapper mapper = Mappers.getMapper(SlabsMapper.class);

    @Mapping(target = "slabId" , source = "slab.id")
    @Mapping(target = "tenures" , source = "slab.tenures")
    @Mapping(target = "typeOfTransaction" , source = "slab.typeOfTransaction")
    SlabOutputDto SlabToSlabOutputDto(Slabs slab);

    @Mapping(target = "tenures", source = "dto.tenures")
    @Mapping(target = "typeOfTransaction", source = "dto.typeOfTransaction")
    Slabs SlabInputDtoToSlabs(SlabInputDto dto);
}
