package com.tc.training.mapper;

import com.tc.training.dtos.inputdto.AccountDetailsInputDto;
import com.tc.training.dtos.outputdto.AccountDetailsOutputDto;
import com.tc.training.model.AccountDetails;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-06T16:55:51+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class AccountDetailsMapperImpl implements AccountDetailsMapper {

    @Override
    public AccountDetailsOutputDto AccountDetailsToAccountDetailsOutputDto(AccountDetails accountDetails) {
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
    public AccountDetails AccountDetailsInputDtoToAccountDetails(AccountDetailsInputDto dto) {
        if ( dto == null ) {
            return null;
        }

        AccountDetails accountDetails = new AccountDetails();

        accountDetails.setOpeningDate( dto.getOpeningDate() );
        accountDetails.setClosingDate( dto.getClosingDate() );

        return accountDetails;
    }
}
