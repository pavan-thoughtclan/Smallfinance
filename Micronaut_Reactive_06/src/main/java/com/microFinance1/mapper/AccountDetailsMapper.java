package com.microFinance1.mapper;

import com.microFinance1.dtos.inputs.AccountDetailsInputDto;
import com.microFinance1.dtos.outputs.AccountDetailsOutputDto;
import com.microFinance1.entities.AccountDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jsr330")
public interface AccountDetailsMapper {
    AccountDetailsMapper MAPPER= Mappers.getMapper(AccountDetailsMapper.class);

    AccountDetails mapToAccountDetails(AccountDetailsInputDto accountDetailsInputDto);
    AccountDetailsOutputDto mapToAccountDetailsOutputDto(AccountDetails accountDetails);


}
