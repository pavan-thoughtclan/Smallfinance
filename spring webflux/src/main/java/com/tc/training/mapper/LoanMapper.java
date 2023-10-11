package com.tc.training.mapper;

import com.tc.training.dtos.inputdto.LoanInputDto;
import com.tc.training.dtos.inputdto.TransactionInputDto;
import com.tc.training.dtos.outputdto.LoanOutputDto;
import com.tc.training.model.FixedDeposit;
import com.tc.training.model.Loan;
import com.tc.training.service.LoanService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(source = "inputDto.accountNumber", target = "account")
    @Mapping(source = "inputDto.loanAmount" , target = "loanedAmount")
    @Mapping(source = "inputDto.type" , target = "typeOfLoan")
    Loan LoanInputDtoToLoan(LoanInputDto inputDto);

    @Mapping(target = "loanId" , source = "loan.id")
    @Mapping(target = "accountNumber" , source = "loan.account")
  /*  @Mapping(target = "loanId" , source = "loan.id")
    @Mapping(target = "loanId" , source = "loan.id")*/
    LoanOutputDto LoanToLoanOutputDto(Loan loan);

    @Mapping(target = "to" , source = "loan.account")
    @Mapping(target = "accountNumber" , source = "loan.account")
    @Mapping(target = "amount" , source = "loan.loanedAmount")
    TransactionInputDto LoanToTransactionInputDto(Loan loan);
}
