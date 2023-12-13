package com.microFinance1.mapper;

import com.microFinance1.dtos.inputs.RecurringDepositInputDto;
import com.microFinance1.dtos.inputs.TransactionInputDto;
import com.microFinance1.dtos.outputs.RecurringDepositOutputDto;
import com.microFinance1.entities.RecurringDeposit;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-13T16:16:11+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Singleton
@Named
public class RecurringDepositMapperImpl implements RecurringDepositMapper {

    @Override
    public RecurringDeposit mapToRecurringDeposit(RecurringDepositInputDto recurringDepositInputDto) {
        if ( recurringDepositInputDto == null ) {
            return null;
        }

        RecurringDeposit recurringDeposit = new RecurringDeposit();

        recurringDeposit.setAccountNumber( recurringDepositInputDto.getAccountNumber() );
        recurringDeposit.setMonthTenure( recurringDepositInputDto.getMonthTenure() );
        recurringDeposit.setMonthlyPaidAmount( recurringDepositInputDto.getMonthlyPaidAmount() );

        return recurringDeposit;
    }

    @Override
    public RecurringDepositOutputDto mapToRecurringDepositOutputDto(RecurringDeposit recurringDeposit) {
        if ( recurringDeposit == null ) {
            return null;
        }

        RecurringDepositOutputDto recurringDepositOutputDto = new RecurringDepositOutputDto();

        if ( recurringDeposit.getAccountNumber() != null ) {
            recurringDepositOutputDto.setAccount( String.valueOf( recurringDeposit.getAccountNumber() ) );
        }
        recurringDepositOutputDto.setId( recurringDeposit.getId() );
        recurringDepositOutputDto.setInterest( recurringDeposit.getInterest() );
        recurringDepositOutputDto.setMaturityAmount( recurringDeposit.getMaturityAmount() );
        recurringDepositOutputDto.setMaturityDate( recurringDeposit.getMaturityDate() );
        recurringDepositOutputDto.setMonthTenure( recurringDeposit.getMonthTenure() );
        recurringDepositOutputDto.setMonthlyPaidAmount( recurringDeposit.getMonthlyPaidAmount() );
        recurringDepositOutputDto.setStartDate( recurringDeposit.getStartDate() );
        recurringDepositOutputDto.setStatus( recurringDeposit.getStatus() );

        return recurringDepositOutputDto;
    }

    @Override
    public TransactionInputDto recurringDepositToTransactionInputDto(RecurringDeposit rd) {
        if ( rd == null ) {
            return null;
        }

        TransactionInputDto transactionInputDto = new TransactionInputDto();

        transactionInputDto.setTo( rd.getAccountNumber() );
        transactionInputDto.setAmount( rd.getMonthlyPaidAmount() );
        transactionInputDto.setAccountNumber( rd.getAccountNumber() );

        return transactionInputDto;
    }
}
