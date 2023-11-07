package com.smallfinance.mapper;

import com.smallfinance.dtos.outputs.FixedDepositOutput;
import com.smallfinance.entities.AccountDetails;
import com.smallfinance.entities.FixedDeposit;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-06T16:53:45+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Singleton
@Named
public class FixedDepositMapperImpl implements FixedDepositMapper {

    @Override
    public FixedDepositOutput mapToFixedDepositOutputDto(FixedDeposit fixedDeposit) {
        if ( fixedDeposit == null ) {
            return null;
        }

        FixedDepositOutput fixedDepositOutput = new FixedDepositOutput();

        fixedDepositOutput.setAccountNumber( fixedDepositAccountNumberAccountNumber( fixedDeposit ) );
        fixedDepositOutput.setFdId( fixedDeposit.getId() );
        fixedDepositOutput.setAmount( fixedDeposit.getAmount() );
        fixedDepositOutput.setInterestRate( fixedDeposit.getInterestRate() );
        fixedDepositOutput.setIsActive( fixedDeposit.getIsActive() );
        fixedDepositOutput.setMaturityDate( fixedDeposit.getMaturityDate() );
        fixedDepositOutput.setTotalAmount( fixedDeposit.getTotalAmount() );

        return fixedDepositOutput;
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
