package com.microFinance1.mapper;

import com.microFinance1.dtos.outputs.UserOutputDto;
import com.microFinance1.entities.User;
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
public class UserMapperImpl implements UserMapper {

    @Override
    public UserOutputDto mapToUserOutputDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserOutputDto userOutputDto = new UserOutputDto();

        userOutputDto.setAadharCardNumber( user.getAadharCardNumber() );
        userOutputDto.setAge( user.getAge() );
        userOutputDto.setDob( user.getDob() );
        userOutputDto.setEmail( user.getEmail() );
        userOutputDto.setFirstName( user.getFirstName() );
        userOutputDto.setLastName( user.getLastName() );
        userOutputDto.setPanCardNumber( user.getPanCardNumber() );
        userOutputDto.setPhoneNumber( user.getPhoneNumber() );
        userOutputDto.setRoleName( user.getRoleName() );

        return userOutputDto;
    }
}
