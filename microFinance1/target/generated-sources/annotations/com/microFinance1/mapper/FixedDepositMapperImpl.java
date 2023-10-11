package com.microFinance1.mapper;

import com.microFinance1.dtos.inputs.FixedDepositInputDto;
import com.microFinance1.dtos.inputs.TransactionInputDto;
import com.microFinance1.dtos.outputs.FixedDepositOutputDto;
import com.microFinance1.entities.FixedDeposit;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-11T15:15:06+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230814-2020, environment: Java 17.0.8.1 (Eclipse Adoptium)"
)
@Singleton
@Named
public class FixedDepositMapperImpl implements FixedDepositMapper {

    @Override
    public FixedDepositOutputDto mapToFixedDepositOutputDto(FixedDeposit fixedDeposit) {
        if ( fixedDeposit == null ) {
            return null;
        }

        FixedDepositOutputDto fixedDepositOutputDto = new FixedDepositOutputDto();

        fixedDepositOutputDto.setAccountNumber( fixedDeposit.getAccountNumber() );
        fixedDepositOutputDto.setAmount( fixedDeposit.getAmount() );
        fixedDepositOutputDto.setInterestRate( fixedDeposit.getInterestRate() );
        fixedDepositOutputDto.setIsActive( fixedDeposit.getIsActive() );
        fixedDepositOutputDto.setMaturityDate( fixedDeposit.getMaturityDate() );
        fixedDepositOutputDto.setTotalAmount( fixedDeposit.getTotalAmount() );

        return fixedDepositOutputDto;
    }

    @Override
    public FixedDeposit mapToFixedDeposit(FixedDepositInputDto fixedDepositInputDto) {
        if ( fixedDepositInputDto == null ) {
            return null;
        }

        FixedDeposit fixedDeposit = new FixedDeposit();

        fixedDeposit.setAccountNumber( fixedDepositInputDto.getAccountNumber() );
        fixedDeposit.setAmount( fixedDepositInputDto.getAmount() );

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
