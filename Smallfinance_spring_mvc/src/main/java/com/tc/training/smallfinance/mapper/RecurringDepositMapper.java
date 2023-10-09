package com.tc.training.smallfinance.mapper;

import com.tc.training.smallfinance.dtos.inputs.RecurringDepositInputDto;
import com.tc.training.smallfinance.dtos.outputs.RecurringDepositOutputDto;
import com.tc.training.smallfinance.model.AccountDetails;
import com.tc.training.smallfinance.model.RecurringDeposit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecurringDepositMapper {
    RecurringDepositMapper MAPPER = Mappers.getMapper(RecurringDepositMapper.class);

    @Mapping(target = "account", source = "accountNumber") // Map accountNumber to account
    RecurringDepositOutputDto mapToRecurringDepositOutputDto(RecurringDeposit recurringDeposit);
    @Mapping(target = "accountNumber", source = "accountNumber")
    RecurringDeposit mapToRecurringDeposit(RecurringDepositInputDto recurringDepositInputDto);

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
