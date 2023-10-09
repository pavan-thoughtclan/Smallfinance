package com.tc.training.mapper;

import com.tc.training.dtos.inputdto.FixedDepositInputDto;
import com.tc.training.dtos.inputdto.TransactionInputDto;
import com.tc.training.dtos.outputdto.FixedDepositOutputDto;
import com.tc.training.model.FixedDeposit;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-05T11:31:47+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class FixedDepositMapperImpl implements FixedDepositMapper {

    @Override
    public FixedDepositOutputDto FixedDepositToFixedDepositOutputDto(FixedDeposit fd) {
        if ( fd == null ) {
            return null;
        }

        FixedDepositOutputDto fixedDepositOutputDto = new FixedDepositOutputDto();

        fixedDepositOutputDto.setFdId( fd.getId() );
        fixedDepositOutputDto.setInterestRate( fd.getInterestRate() );
        fixedDepositOutputDto.setInterestAmountAdded( fd.getInterestAmount() );
        fixedDepositOutputDto.setAmount( fd.getAmount() );
        fixedDepositOutputDto.setActive( fd.getActive() );
        fixedDepositOutputDto.setAccountNumber( fd.getAccountNumber() );
        fixedDepositOutputDto.setMaturityDate( fd.getMaturityDate() );
        fixedDepositOutputDto.setTotalAmount( fd.getTotalAmount() );

        return fixedDepositOutputDto;
    }

    @Override
    public FixedDeposit FixedDepositInputDtoToFixedDeposit(FixedDepositInputDto dto) {
        if ( dto == null ) {
            return null;
        }

        FixedDeposit fixedDeposit = new FixedDeposit();

        fixedDeposit.setAccountNumber( dto.getAccountNumber() );
        fixedDeposit.setAmount( dto.getAmount() );

        return fixedDeposit;
    }

    @Override
    public TransactionInputDto FixedDepositToTransactionInputDto(FixedDeposit fd) {
        if ( fd == null ) {
            return null;
        }

        TransactionInputDto transactionInputDto = new TransactionInputDto();

        transactionInputDto.setTo( fd.getAccountNumber() );
        transactionInputDto.setAccountNumber( fd.getAccountNumber() );
        transactionInputDto.setAmount( fd.getAmount() );

        return transactionInputDto;
    }
}
