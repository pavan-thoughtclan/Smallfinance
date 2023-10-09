package com.tc.training.mapper;

import com.tc.training.dtos.inputdto.LoanInputDto;
import com.tc.training.dtos.inputdto.TransactionInputDto;
import com.tc.training.dtos.outputdto.LoanOutputDto;
import com.tc.training.model.Loan;
import com.tc.training.utils.TypeOfLoans;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-05T11:31:48+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class LoanMapperImpl implements LoanMapper {

    @Override
    public Loan LoanInputDtoToLoan(LoanInputDto inputDto) {
        if ( inputDto == null ) {
            return null;
        }

        Loan loan = new Loan();

        loan.setAccount( inputDto.getAccountNumber() );
        loan.setLoanedAmount( inputDto.getLoanAmount() );
        if ( inputDto.getType() != null ) {
            loan.setTypeOfLoan( Enum.valueOf( TypeOfLoans.class, inputDto.getType() ) );
        }

        return loan;
    }

    @Override
    public LoanOutputDto LoanToLoanOutputDto(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        LoanOutputDto loanOutputDto = new LoanOutputDto();

        loanOutputDto.setLoanId( loan.getId() );
        if ( loan.getAccount() != null ) {
            loanOutputDto.setAccountNumber( String.valueOf( loan.getAccount() ) );
        }
        loanOutputDto.setAppliedDate( loan.getAppliedDate() );
        loanOutputDto.setActive( loan.getActive() );
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

        transactionInputDto.setTo( loan.getAccount() );
        transactionInputDto.setAccountNumber( loan.getAccount() );
        transactionInputDto.setAmount( loan.getLoanedAmount() );

        return transactionInputDto;
    }
}
