package com.tc.training.smallfinance.mapper;

import com.tc.training.smallfinance.model.AccountDetails;
import com.tc.training.smallfinance.model.Loan;
import com.tc.training.smallfinance.dtos.outputs.LoanOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    LoanMapper MAPPER = Mappers.getMapper(LoanMapper.class);

    @Mapping(target = "loanId", source = "id")
    @Mapping(target = "accountNumber", source = "accountNumber")
    LoanOutputDto mapToLoanOutputDto(Loan loan);

    // Custom mapping method to convert AccountDetails to String
    default String map(AccountDetails account) {
        return (account != null) ? account.getAccountNumber().toString() : null;
    }
}
