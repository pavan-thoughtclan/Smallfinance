package com.microFinance1.mapper;

import com.microFinance1.dtos.inputs.SlabInputDto;
import com.microFinance1.dtos.outputs.SlabOutputDto;
import com.microFinance1.entities.Slabs;
import com.microFinance1.utils.Tenures;
import com.microFinance1.utils.TypeOfSlab;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-11T15:15:05+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230814-2020, environment: Java 17.0.8.1 (Eclipse Adoptium)"
)
@Singleton
@Named
public class SlabMapperImpl implements SlabMapper {

    @Override
    public Slabs mapToSlabs(SlabInputDto slabInputDto) {
        if ( slabInputDto == null ) {
            return null;
        }

        Slabs slabs = new Slabs();

        slabs.setInterestRate( slabInputDto.getInterestRate() );
        if ( slabInputDto.getTenures() != null ) {
            slabs.setTenures( Enum.valueOf( Tenures.class, slabInputDto.getTenures() ) );
        }
        if ( slabInputDto.getTypeOfTransaction() != null ) {
            slabs.setTypeOfTransaction( Enum.valueOf( TypeOfSlab.class, slabInputDto.getTypeOfTransaction() ) );
        }

        return slabs;
    }

    @Override
    public SlabOutputDto mapToSlabOutputDto(Slabs slabs) {
        if ( slabs == null ) {
            return null;
        }

        SlabOutputDto slabOutputDto = new SlabOutputDto();

        slabOutputDto.setInterestRate( slabs.getInterestRate() );
        if ( slabs.getTenures() != null ) {
            slabOutputDto.setTenures( slabs.getTenures().name() );
        }
        if ( slabs.getTypeOfTransaction() != null ) {
            slabOutputDto.setTypeOfTransaction( slabs.getTypeOfTransaction().name() );
        }

        return slabOutputDto;
    }
}
