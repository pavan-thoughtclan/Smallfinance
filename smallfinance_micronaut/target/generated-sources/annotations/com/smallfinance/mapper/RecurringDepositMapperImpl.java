package com.smallfinance.mapper;

import com.smallfinance.dtos.inputs.RecurringDepositInput;
import com.smallfinance.dtos.outputs.RecurringDepositOutput;
import com.smallfinance.entities.RecurringDeposit;
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
public class RecurringDepositMapperImpl implements RecurringDepositMapper {

    @Override
    public RecurringDepositOutput mapToRecurringDepositOutputDto(RecurringDeposit recurringDeposit) {
        if ( recurringDeposit == null ) {
            return null;
        }

        RecurringDepositOutput recurringDepositOutput = new RecurringDepositOutput();

        recurringDepositOutput.setAccount( mapAccountDetailsToString( recurringDeposit.getAccountNumber() ) );
        recurringDepositOutput.setId( recurringDeposit.getId() );
        recurringDepositOutput.setMonthTenure( recurringDeposit.getMonthTenure() );
        recurringDepositOutput.setMonthlyPaidAmount( recurringDeposit.getMonthlyPaidAmount() );
        recurringDepositOutput.setMaturityAmount( recurringDeposit.getMaturityAmount() );
        recurringDepositOutput.setStartDate( recurringDeposit.getStartDate() );
        recurringDepositOutput.setMaturityDate( recurringDeposit.getMaturityDate() );
        recurringDepositOutput.setStatus( recurringDeposit.getStatus() );
        recurringDepositOutput.setInterest( recurringDeposit.getInterest() );

        return recurringDepositOutput;
    }

    @Override
    public RecurringDeposit mapToRecurringDeposit(RecurringDepositInput recurringDepositInputDto) {
        if ( recurringDepositInputDto == null ) {
            return null;
        }

        RecurringDeposit recurringDeposit = new RecurringDeposit();

        recurringDeposit.setAccountNumber( mapAccountNumberToAccountDetails( recurringDepositInputDto.getAccountNumber() ) );
        recurringDeposit.setMonthTenure( recurringDepositInputDto.getMonthTenure() );
        recurringDeposit.setMonthlyPaidAmount( recurringDepositInputDto.getMonthlyPaidAmount() );

        return recurringDeposit;
    }
}
