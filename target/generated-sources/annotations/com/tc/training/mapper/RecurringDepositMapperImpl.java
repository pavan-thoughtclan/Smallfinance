package com.tc.training.mapper;

import com.tc.training.dtos.inputdto.RecurringDepositInputDto;
import com.tc.training.dtos.inputdto.TransactionInputDto;
import com.tc.training.dtos.outputdto.RecurringDepositOutputDto;
import com.tc.training.model.RecurringDeposit;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-06T16:12:25+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class RecurringDepositMapperImpl implements RecurringDepositMapper {

    @Override
    public RecurringDeposit inputDtToRecurringDeposit(RecurringDepositInputDto recurringDepositInputDto) {
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
    public RecurringDepositOutputDto RecurringDepositToOutputDto(RecurringDeposit recurringDeposit) {
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
    public TransactionInputDto RecurringDepositToTransactionInputDto(RecurringDeposit rd) {
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
