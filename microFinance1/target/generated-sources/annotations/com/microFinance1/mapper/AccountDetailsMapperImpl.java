package com.microFinance1.mapper;

import com.microFinance1.dtos.inputs.AccountDetailsInputDto;
import com.microFinance1.dtos.outputs.AccountDetailsOutputDto;
import com.microFinance1.entities.AccountDetails;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-11T15:15:06+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230814-2020, environment: Java 17.0.8.1 (Eclipse Adoptium)"
)
@Singleton
@Named
public class AccountDetailsMapperImpl implements AccountDetailsMapper {

    @Override
    public AccountDetails mapToAccountDetails(AccountDetailsInputDto accountDetailsInputDto) {
        if ( accountDetailsInputDto == null ) {
            return null;
        }

        AccountDetails accountDetails = new AccountDetails();

        accountDetails.setClosingDate( accountDetailsInputDto.getClosingDate() );
        accountDetails.setOpeningDate( accountDetailsInputDto.getOpeningDate() );

        return accountDetails;
    }

    @Override
    public AccountDetailsOutputDto mapToAccountDetailsOutputDto(AccountDetails accountDetails) {
        if ( accountDetails == null ) {
            return null;
        }

        AccountDetailsOutputDto accountDetailsOutputDto = new AccountDetailsOutputDto();

        accountDetailsOutputDto.setAccountNumber( accountDetails.getAccountNumber() );
        accountDetailsOutputDto.setAccountType( accountDetails.getAccountType() );
        if ( accountDetails.getBalance() != null ) {
            accountDetailsOutputDto.setBalance( accountDetails.getBalance().longValue() );
        }
        accountDetailsOutputDto.setKyc( accountDetails.getKyc() );

        return accountDetailsOutputDto;
    }
}
