package com.tc.training.smallfinance.mapper;

import com.tc.training.smallfinance.dtos.outputs.LoanOutputDto;
import com.tc.training.smallfinance.model.Loan;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-13T15:34:05+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class LoanMapperImpl implements LoanMapper {

    @Override
    public LoanOutputDto mapToLoanOutputDto(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        LoanOutputDto loanOutputDto = new LoanOutputDto();

        loanOutputDto.setLoanId( loan.getId() );
        loanOutputDto.setAccountNumber( map( loan.getAccountNumber() ) );
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
}
