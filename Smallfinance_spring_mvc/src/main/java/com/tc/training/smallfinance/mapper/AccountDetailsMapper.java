package com.tc.training.smallfinance.mapper;

import com.tc.training.smallfinance.dtos.inputs.AccountDetailsInputDto;
import com.tc.training.smallfinance.dtos.outputs.AccountDetailsOutputDto;
import com.tc.training.smallfinance.model.AccountDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountDetailsMapper {
    AccountDetailsMapper MAPPER = Mappers.getMapper(AccountDetailsMapper.class);

//    @Mapping(target = "accountNumber", ignore = true) // Ignore accountNumber during mapping

    AccountDetails mapToAccountDetails(AccountDetailsInputDto accountDetailsInputDto);


    @Mapping(source = "accountNumber", target = "accountNumber") // Map accountNumber directly
    AccountDetailsOutputDto mapToAccountDetailsOutputDto(AccountDetails accountDetails);


//    @Mapping(source = "accountNumber", target = "accountNumber")
//    AccountDetails map(AccountDetails sourceAccountDetails, AccountDetails targetAccountDetails);

    AccountDetails mapToAccountDetails1(AccountDetails source);

}
