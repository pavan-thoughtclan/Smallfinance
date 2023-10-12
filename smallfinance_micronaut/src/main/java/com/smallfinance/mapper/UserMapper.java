package com.smallfinance.mapper;

import com.smallfinance.dtos.outputs.UserOutput;
import com.smallfinance.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jsr330")
public interface UserMapper {


    @Mapping(target = "userId" , source = "id")
    UserOutput userToUserDto(User user);

    User userDtoToUser(UserOutput output);
}
