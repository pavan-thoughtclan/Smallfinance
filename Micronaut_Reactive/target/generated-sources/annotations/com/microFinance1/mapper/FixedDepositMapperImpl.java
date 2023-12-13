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
    date = "2023-12-13T16:16:10+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 17.0.9 (Eclipse Adoptium)"
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

        fixedDepositOutputDto.setFdId( fixedDeposit.getId() );
        fixedDepositOutputDto.setInterestRate( fixedDeposit.getInterestRate() );
        fixedDepositOutputDto.setInterestAmountAdded( fixedDeposit.getInterestAmount() );
        fixedDepositOutputDto.setAccountNumber( fixedDeposit.getAccountNumber() );
        fixedDepositOutputDto.setAmount( fixedDeposit.getAmount() );
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
