package com.tc.training.smallfinance.mapper;

import com.tc.training.smallfinance.dtos.outputs.FixedDepositOutputDto;
import com.tc.training.smallfinance.model.AccountDetails;
import com.tc.training.smallfinance.model.FixedDeposit;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-28T12:52:11+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class FixedDepositMapperImpl implements FixedDepositMapper {

    @Override
    public FixedDepositOutputDto mapToFixedDepositOutputDto(FixedDeposit fixedDeposit) {
        if ( fixedDeposit == null ) {
            return null;
        }

        FixedDepositOutputDto fixedDepositOutputDto = new FixedDepositOutputDto();

        fixedDepositOutputDto.setAccountNumber( fixedDepositAccountNumberAccountNumber( fixedDeposit ) );
        fixedDepositOutputDto.setFdId( fixedDeposit.getId() );
        fixedDepositOutputDto.setAmount( fixedDeposit.getAmount() );
        fixedDepositOutputDto.setInterestRate( fixedDeposit.getInterestRate() );
        fixedDepositOutputDto.setIsActive( fixedDeposit.getIsActive() );
        fixedDepositOutputDto.setMaturityDate( fixedDeposit.getMaturityDate() );
        fixedDepositOutputDto.setTotalAmount( fixedDeposit.getTotalAmount() );

        return fixedDepositOutputDto;
    }

    private Long fixedDepositAccountNumberAccountNumber(FixedDeposit fixedDeposit) {
        if ( fixedDeposit == null ) {
            return null;
        }
        AccountDetails accountNumber = fixedDeposit.getAccountNumber();
        if ( accountNumber == null ) {
            return null;
        }
        Long accountNumber1 = accountNumber.getAccountNumber();
        if ( accountNumber1 == null ) {
            return null;
        }
        return accountNumber1;
    }
}
