package com.smallfinance.mapper;

import com.smallfinance.dtos.outputs.LoanOutput;
import com.smallfinance.entities.Loan;
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
public class LoanMapperImpl implements LoanMapper {

    @Override
    public LoanOutput mapToLoanOutputDto(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        LoanOutput loanOutput = new LoanOutput();

        loanOutput.setLoanId( loan.getId() );
        loanOutput.setAccountNumber( map( loan.getAccountNumber() ) );
        loanOutput.setAppliedDate( loan.getAppliedDate() );
        loanOutput.setIsActive( loan.getIsActive() );
        loanOutput.setStatus( loan.getStatus() );
        loanOutput.setLoanedAmount( loan.getLoanedAmount() );
        loanOutput.setTypeOfLoan( loan.getTypeOfLoan() );
        loanOutput.setLoanEndDate( loan.getLoanEndDate() );
        loanOutput.setInterest( loan.getInterest() );
        loanOutput.setInterestAmount( loan.getInterestAmount() );
        loanOutput.setMonthlyInterestAmount( loan.getMonthlyInterestAmount() );
        loanOutput.setTotalAmount( loan.getTotalAmount() );

        return loanOutput;
    }
}
