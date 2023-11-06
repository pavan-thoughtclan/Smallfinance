package com.microFinance1.mapper;

import com.microFinance1.dtos.inputs.LoanInputDto;
import com.microFinance1.dtos.inputs.TransactionInputDto;
import com.microFinance1.dtos.outputs.LoanOutputDto;
import com.microFinance1.entities.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "jsr330")
public interface LoanMapper {
    LoanMapper MAPPER= Mappers.getMapper(LoanMapper.class);
    @Mapping(source = "loanInputDto.accountNumber", target = "accountNumber")
    @Mapping(source = "loanInputDto.loanAmount" , target = "loanedAmount")
    @Mapping(source = "loanInputDto.type" , target = "typeOfLoan")
    Loan mapToLoan(LoanInputDto loanInputDto);

    @Mapping(target = "loanId" , source = "loan.id")
    @Mapping(target = "accountNumber" , source = "loan.accountNumber")
    LoanOutputDto mapToLoanOutputDto(Loan loan);

    @Mapping(target = "to" , source = "loan.accountNumber")
    @Mapping(target = "accountNumber" , source = "loan.accountNumber")
    @Mapping(target = "amount" , source = "loan.loanedAmount")
    TransactionInputDto LoanToTransactionInputDto(Loan loan);
}