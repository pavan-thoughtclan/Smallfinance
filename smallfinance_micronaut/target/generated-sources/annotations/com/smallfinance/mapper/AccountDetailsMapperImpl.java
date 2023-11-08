package com.smallfinance.mapper;

import com.smallfinance.dtos.inputs.AccountDetailsInput;
import com.smallfinance.dtos.outputs.AccountDetailsOutput;
import com.smallfinance.entities.AccountDetails;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-06T16:53:45+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Singleton
@Named
public class AccountDetailsMapperImpl implements AccountDetailsMapper {

    @Override
    public AccountDetailsOutput accountToDto(AccountDetails accountDetails) {
        if ( accountDetails == null ) {
            return null;
        }

        AccountDetailsOutput accountDetailsOutput = new AccountDetailsOutput();

        accountDetailsOutput.setAccountNumber( accountDetails.getAccountNumber() );
        accountDetailsOutput.setAccountType( accountDetails.getAccountType() );
        if ( accountDetails.getBalance() != null ) {
            accountDetailsOutput.setBalance( accountDetails.getBalance().longValue() );
        }
        accountDetailsOutput.setKyc( accountDetails.getKyc() );

        return accountDetailsOutput;
    }

    @Override
    public AccountDetails dtoToAccount(AccountDetailsInput input) {
        if ( input == null ) {
            return null;
        }

        AccountDetails accountDetails = new AccountDetails();

        accountDetails.setOpeningDate( input.getOpeningDate() );
        accountDetails.setClosingDate( input.getClosingDate() );

        return accountDetails;
    }
}
