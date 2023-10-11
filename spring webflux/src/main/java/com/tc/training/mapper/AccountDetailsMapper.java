package com.tc.training.mapper;


import com.tc.training.dtos.inputdto.AccountDetailsInputDto;
import com.tc.training.dtos.outputdto.AccountDetailsOutputDto;
import com.tc.training.dtos.outputdto.UserOutputDto;
import com.tc.training.model.AccountDetails;
import com.tc.training.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface AccountDetailsMapper {

    AccountDetailsMapper mapper = Mappers.getMapper(AccountDetailsMapper.class);

    AccountDetailsOutputDto AccountDetailsToAccountDetailsOutputDto(AccountDetails accountDetails);

    AccountDetails AccountDetailsInputDtoToAccountDetails(AccountDetailsInputDto dto);

    //AccountDetailsPojo AccountDetailsToAccountDetailsPojo(AccountDetails accountDetails);

}
