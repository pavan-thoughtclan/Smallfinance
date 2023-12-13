package com.smallfinance.mapper;

import com.smallfinance.dtos.inputs.LoanInput;
import com.smallfinance.dtos.outputs.LoanOutput;
import com.smallfinance.entities.AccountDetails;
import com.smallfinance.entities.Loan;
import com.smallfinance.repositories.AccountDetailsRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "jsr330")
public interface LoanMapper {
    @Mapping(target = "loanId", source = "id")
    @Mapping(target = "accountNumber", source = "accountNumber")
    LoanOutput mapToLoanOutputDto(Loan loan);


//    @Mapping(target = "accountNumber", source = "accountNumber")
//    @Mapping(target = "loanedAmount", source = "loanAmount")
//    @Mapping(target = "typeOfLoan", source = "type")
//    @Mapping(target = "loanEndDate", ignore = true)  // Set in the service method
//    @Mapping(target = "interest", ignore = true)    // Set in the service method
//    Loan mapToLoan(LoanInput loanInput);

//    @Mapping(target = "accountNumber", source = "accountNumber", qualifiedByName = "stringToAccountDetails")
//    @Mapping(target = "loanedAmount", source = "loanAmount")
//    @Mapping(target = "typeOfLoan", source = "type")
//    Loan mapToLoan(LoanInput loanInput);

//    @Named("stringToAccountDetails")
//    default AccountDetails stringToAccountDetails(String accountNumber, @Context AccountDetailsRepository accountDetailsRepository) {
//        // Fetch AccountDetails from the repository based on the accountNumber
//        return accountDetailsRepository.findByAccountNumber(accountNumber)
//                .orElseThrow(() -> new RuntimeException("AccountDetails not found for accountNumber: " + accountNumber));
//    }

    // Custom mapping method to convert AccountDetails to String
    default String map(AccountDetails account) {
        return (account != null) ? account.getAccountNumber().toString() : null;
    }
}
