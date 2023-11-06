package com.microFinance1.mapper;

import com.microFinance1.dtos.outputs.UserOutputDto;
import com.microFinance1.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jsr330")
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    UserOutputDto mapToUserOutputDto(User user);
}
