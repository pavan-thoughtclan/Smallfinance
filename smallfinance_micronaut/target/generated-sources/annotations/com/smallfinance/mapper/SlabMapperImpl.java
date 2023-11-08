package com.smallfinance.mapper;

import com.smallfinance.dtos.inputs.SlabInput;
import com.smallfinance.dtos.outputs.SlabOutput;
import com.smallfinance.entities.Slabs;
import com.smallfinance.enums.Tenures;
import com.smallfinance.enums.TypeOfSlab;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-06T16:53:44+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Singleton
@Named
public class SlabMapperImpl implements SlabMapper {

    @Override
    public Slabs mapToSlabs(SlabInput slabInputDto) {
        if ( slabInputDto == null ) {
            return null;
        }

        Slabs slabs = new Slabs();

        if ( slabInputDto.getTenures() != null ) {
            slabs.setTenures( Enum.valueOf( Tenures.class, slabInputDto.getTenures() ) );
        }
        slabs.setInterestRate( slabInputDto.getInterestRate() );
        if ( slabInputDto.getTypeOfTransaction() != null ) {
            slabs.setTypeOfTransaction( Enum.valueOf( TypeOfSlab.class, slabInputDto.getTypeOfTransaction() ) );
        }

        return slabs;
    }

    @Override
    public SlabOutput mapToSlabOutputDto(Slabs slabs) {
        if ( slabs == null ) {
            return null;
        }

        SlabOutput slabOutput = new SlabOutput();

        if ( slabs.getTenures() != null ) {
            slabOutput.setTenures( slabs.getTenures().name() );
        }
        slabOutput.setInterestRate( slabs.getInterestRate() );
        if ( slabs.getTypeOfTransaction() != null ) {
            slabOutput.setTypeOfTransaction( slabs.getTypeOfTransaction().name() );
        }

        return slabOutput;
    }
}
