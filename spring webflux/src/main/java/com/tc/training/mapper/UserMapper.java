package com.tc.training.mapper;

import com.tc.training.dtos.inputdto.AccountDetailsInputDto;
import com.tc.training.dtos.outputdto.UserOutputDto;
import com.tc.training.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper mapper = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "userId", source = "id")
    UserOutputDto userToUserDto(User user);

    User accountInputDtoToUser(AccountDetailsInputDto accountDetailsInputDto);

}
