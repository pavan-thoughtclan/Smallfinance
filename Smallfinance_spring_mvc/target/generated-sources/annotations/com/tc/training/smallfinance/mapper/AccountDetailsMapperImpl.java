package com.tc.training.smallfinance.mapper;

import com.tc.training.smallfinance.dtos.inputs.AccountDetailsInputDto;
import com.tc.training.smallfinance.dtos.outputs.AccountDetailsOutputDto;
import com.tc.training.smallfinance.model.AccountDetails;
import com.tc.training.smallfinance.model.AccountDetails.AccountDetailsBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-28T12:52:11+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class AccountDetailsMapperImpl implements AccountDetailsMapper {

    @Override
    public AccountDetails mapToAccountDetails(AccountDetailsInputDto accountDetailsInputDto) {
        if ( accountDetailsInputDto == null ) {
            return null;
        }

        AccountDetailsBuilder accountDetails = AccountDetails.builder();

        accountDetails.openingDate( accountDetailsInputDto.getOpeningDate() );
        accountDetails.closingDate( accountDetailsInputDto.getClosingDate() );

        return accountDetails.build();
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

    @Override
    public AccountDetails mapToAccountDetails1(AccountDetails source) {
        if ( source == null ) {
            return null;
        }

        AccountDetailsBuilder accountDetails = AccountDetails.builder();

        accountDetails.accountNumber( source.getAccountNumber() );
        accountDetails.accountType( source.getAccountType() );
        accountDetails.openingDate( source.getOpeningDate() );
        accountDetails.closingDate( source.getClosingDate() );
        accountDetails.balance( source.getBalance() );
        accountDetails.kyc( source.getKyc() );
        accountDetails.user( source.getUser() );

        return accountDetails.build();
    }
}
