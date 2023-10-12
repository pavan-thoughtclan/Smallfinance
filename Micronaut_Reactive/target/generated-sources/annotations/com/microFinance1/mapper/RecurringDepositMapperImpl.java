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
    date = "2023-10-12T15:33:08+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
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
