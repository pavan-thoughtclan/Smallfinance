package com.smallfinance.mapper;

import com.smallfinance.dtos.inputs.AccountDetailsInput;
import com.smallfinance.dtos.outputs.AccountDetailsOutput;
import com.smallfinance.entities.AccountDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//@Mapper(componentModel = "jsr330")
@Mapper(componentModel = "jsr330")
public interface AccountDetailsMapper {

    @Mapping(source = "accountNumber", target = "accountNumber")
    AccountDetailsOutput accountToDto(AccountDetails accountDetails);

    AccountDetails dtoToAccount(AccountDetailsInput input);
}
