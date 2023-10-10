package com.microFinance1.mapper;

import com.microFinance1.dtos.inputs.LoanInputDto;
import com.microFinance1.dtos.inputs.TransactionInputDto;
import com.microFinance1.dtos.outputs.LoanOutputDto;
import com.microFinance1.entities.Loan;
import com.microFinance1.utils.TypeOfLoans;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-10T20:18:09+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Singleton
@Named
public class LoanMapperImpl implements LoanMapper {

    @Override
    public Loan mapToLoan(LoanInputDto loanInputDto) {
        if ( loanInputDto == null ) {
            return null;
        }

        Loan loan = new Loan();

        if ( loanInputDto.getAccountNumber() != null ) {
            loan.setAccountNumber( Long.parseLong( loanInputDto.getAccountNumber() ) );
        }
        loan.setLoanedAmount( loanInputDto.getLoanAmount() );
        if ( loanInputDto.getType() != null ) {
            loan.setTypeOfLoan( Enum.valueOf( TypeOfLoans.class, loanInputDto.getType() ) );
        }

        return loan;
    }

    @Override
    public LoanOutputDto mapToLoanOutputDto(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        LoanOutputDto loanOutputDto = new LoanOutputDto();

        loanOutputDto.setLoanId( loan.getId() );
        if ( loan.getAccountNumber() != null ) {
            loanOutputDto.setAccountNumber( String.valueOf( loan.getAccountNumber() ) );
        }
        loanOutputDto.setAppliedDate( loan.getAppliedDate() );
        loanOutputDto.setIsActive( loan.getIsActive() );
        loanOutputDto.setStatus( loan.getStatus() );
        loanOutputDto.setLoanedAmount( loan.getLoanedAmount() );
        loanOutputDto.setTypeOfLoan( loan.getTypeOfLoan() );
        loanOutputDto.setLoanEndDate( loan.getLoanEndDate() );
        loanOutputDto.setInterest( loan.getInterest() );
        loanOutputDto.setInterestAmount( loan.getInterestAmount() );
        loanOutputDto.setMonthlyInterestAmount( loan.getMonthlyInterestAmount() );
        loanOutputDto.setTotalAmount( loan.getTotalAmount() );

        return loanOutputDto;
    }

    @Override
    public TransactionInputDto LoanToTransactionInputDto(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        TransactionInputDto transactionInputDto = new TransactionInputDto();

        transactionInputDto.setTo( loan.getAccountNumber() );
        transactionInputDto.setAccountNumber( loan.getAccountNumber() );
        transactionInputDto.setAmount( loan.getLoanedAmount() );

        return transactionInputDto;
    }
}
