package com.tc.training.mapper;

import com.tc.training.dtos.inputdto.SlabInputDto;
import com.tc.training.dtos.outputdto.SlabOutputDto;
import com.tc.training.model.Slabs;
import com.tc.training.utils.Tenures;
import com.tc.training.utils.TypeOfTransaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-06T16:55:51+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class SlabsMapperImpl implements SlabsMapper {

    @Override
    public SlabOutputDto SlabToSlabOutputDto(Slabs slab) {
        if ( slab == null ) {
            return null;
        }

        SlabOutputDto slabOutputDto = new SlabOutputDto();

        slabOutputDto.setSlabId( slab.getId() );
        if ( slab.getTenures() != null ) {
            slabOutputDto.setTenures( slab.getTenures().name() );
        }
        if ( slab.getTypeOfTransaction() != null ) {
            slabOutputDto.setTypeOfTransaction( slab.getTypeOfTransaction().name() );
        }
        slabOutputDto.setInterestRate( slab.getInterestRate() );

        return slabOutputDto;
    }

    @Override
    public Slabs SlabInputDtoToSlabs(SlabInputDto dto) {
        if ( dto == null ) {
            return null;
        }

        Slabs slabs = new Slabs();

        if ( dto.getTenures() != null ) {
            slabs.setTenures( Enum.valueOf( Tenures.class, dto.getTenures() ) );
        }
        if ( dto.getTypeOfTransaction() != null ) {
            slabs.setTypeOfTransaction( Enum.valueOf( TypeOfTransaction.class, dto.getTypeOfTransaction() ) );
        }
        slabs.setInterestRate( dto.getInterestRate() );

        return slabs;
    }
}
