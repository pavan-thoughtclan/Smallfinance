package com.tc.training.smallfinance.mapper;

import com.tc.training.smallfinance.dtos.inputs.RecurringDepositInputDto;
import com.tc.training.smallfinance.dtos.outputs.RecurringDepositOutputDto;
import com.tc.training.smallfinance.model.RecurringDeposit;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-08T15:32:49+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class RecurringDepositMapperImpl implements RecurringDepositMapper {

    @Override
    public RecurringDepositOutputDto mapToRecurringDepositOutputDto(RecurringDeposit recurringDeposit) {
        if ( recurringDeposit == null ) {
            return null;
        }

        RecurringDepositOutputDto recurringDepositOutputDto = new RecurringDepositOutputDto();

        recurringDepositOutputDto.setAccount( mapAccountDetailsToString( recurringDeposit.getAccountNumber() ) );
        recurringDepositOutputDto.setId( recurringDeposit.getId() );
        recurringDepositOutputDto.setMonthTenure( recurringDeposit.getMonthTenure() );
        recurringDepositOutputDto.setMonthlyPaidAmount( recurringDeposit.getMonthlyPaidAmount() );
        recurringDepositOutputDto.setMaturityAmount( recurringDeposit.getMaturityAmount() );
        recurringDepositOutputDto.setStartDate( recurringDeposit.getStartDate() );
        recurringDepositOutputDto.setMaturityDate( recurringDeposit.getMaturityDate() );
        recurringDepositOutputDto.setStatus( recurringDeposit.getStatus() );
        recurringDepositOutputDto.setInterest( recurringDeposit.getInterest() );

        return recurringDepositOutputDto;
    }

    @Override
    public RecurringDeposit mapToRecurringDeposit(RecurringDepositInputDto recurringDepositInputDto) {
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
