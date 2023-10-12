package com.smallfinance.mapper;

import com.smallfinance.dtos.inputs.LoanInput;
import com.smallfinance.dtos.outputs.LoanOutput;
import com.smallfinance.entities.AccountDetails;
import com.smallfinance.entities.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jsr330")
public interface LoanMapper {
    @Mapping(target = "loanId", source = "id")
    @Mapping(target = "accountNumber", source = "accountNumber")
    LoanOutput mapToLoanOutputDto(Loan loan);

    // Custom mapping method to convert AccountDetails to String
    default String map(AccountDetails account) {
        return (account != null) ? account.getAccountNumber().toString() : null;
    }
}
