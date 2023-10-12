package com.smallfinance.mapper;

import com.smallfinance.dtos.inputs.RecurringDepositInput;
import com.smallfinance.dtos.inputs.TransactionInput;
import com.smallfinance.dtos.outputs.RecurringDepositOutput;
import com.smallfinance.entities.AccountDetails;
import com.smallfinance.entities.RecurringDeposit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jsr330")

public interface RecurringDepositMapper {

    RecurringDepositMapper MAPPER = Mappers.getMapper(RecurringDepositMapper.class);

    @Mapping(target = "account", source = "accountNumber") // Map accountNumber to account
    RecurringDepositOutput mapToRecurringDepositOutputDto(RecurringDeposit recurringDeposit);
    @Mapping(target = "accountNumber", source = "accountNumber")
    RecurringDeposit mapToRecurringDeposit(RecurringDepositInput recurringDepositInputDto);

    // Custom mapping method to convert AccountDetails to String
    default String mapAccountDetailsToString(AccountDetails accountDetails) {
        return accountDetails != null ? accountDetails.getAccountNumber().toString() : null;
    }

    // Custom mapping method to convert Long to AccountDetails
    default AccountDetails mapAccountNumberToAccountDetails(Long accountNumber) {
        if (accountNumber != null) {
            AccountDetails accountDetails = new AccountDetails();
            accountDetails.setAccountNumber(accountNumber);
            return accountDetails;
        }
        return null;
    }
}
