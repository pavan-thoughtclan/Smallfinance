package com.tc.training.smallfinance.mapper;

import com.tc.training.smallfinance.dtos.inputs.SlabInputDto;
import com.tc.training.smallfinance.dtos.outputs.SlabOutputDto;
import com.tc.training.smallfinance.model.Slabs;
import com.tc.training.smallfinance.model.Slabs.SlabsBuilder;
import com.tc.training.smallfinance.utils.Tenures;
import com.tc.training.smallfinance.utils.TypeOfSlab;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-06T11:52:14+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class SlabMapperImpl implements SlabMapper {

    @Override
    public Slabs mapToSlabs(SlabInputDto slabInputDto) {
        if ( slabInputDto == null ) {
            return null;
        }

        SlabsBuilder slabs = Slabs.builder();

        if ( slabInputDto.getTenures() != null ) {
            slabs.tenures( Enum.valueOf( Tenures.class, slabInputDto.getTenures() ) );
        }
        slabs.interestRate( slabInputDto.getInterestRate() );
        if ( slabInputDto.getTypeOfTransaction() != null ) {
            slabs.typeOfTransaction( Enum.valueOf( TypeOfSlab.class, slabInputDto.getTypeOfTransaction() ) );
        }

        return slabs.build();
    }

    @Override
    public SlabOutputDto mapToSlabOutputDto(Slabs slabs) {
        if ( slabs == null ) {
            return null;
        }

        SlabOutputDto slabOutputDto = new SlabOutputDto();

        slabOutputDto.setSlabId( slabs.getId() );
        if ( slabs.getTenures() != null ) {
            slabOutputDto.setTenures( slabs.getTenures().name() );
        }
        slabOutputDto.setInterestRate( slabs.getInterestRate() );
        if ( slabs.getTypeOfTransaction() != null ) {
            slabOutputDto.setTypeOfTransaction( slabs.getTypeOfTransaction().name() );
        }

        return slabOutputDto;
    }
}
