package com.tc.training.smallfinance.mapper;//package com.tc.training.smallFinance.mapper;
//
//import com.tc.training.smallFinance.dtos.outputs.UserOutputDto;
//import com.tc.training.smallFinance.model.User;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper(componentModel = "spring")
//public interface UserMapper {
//    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
//
//    @Mapping(source = "account.accountNumber", target = "accountNumber")
//    @Mapping(source = "account.kyc", target = "kyc")
//    UserOutputDto userToUserDto(User user);
//}

import com.tc.training.smallfinance.dtos.outputs.UserOutputDto;
import com.tc.training.smallfinance.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "userId", source = "id") // Map the userId from User.id
    UserOutputDto userToUserDto(User user);

    @Mapping(source = "userId", target = "id") // Map fields as needed
    User map(UserOutputDto userOutputDto);

    @AfterMapping
    default void mapAccountDetailsFields(User user, @MappingTarget UserOutputDto userOutputDto) {
        if (user.getAccountDetails() != null && !user.getAccountDetails().isEmpty()) {
            userOutputDto.setAccountNumber(user.getAccountDetails().get(0).getAccountNumber());
            userOutputDto.setKyc(user.getAccountDetails().get(0).getKyc());
        }
    }

}