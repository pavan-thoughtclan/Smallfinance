package com.microFinance1.mapper;

import com.microFinance1.dtos.outputs.UserOutputDto;
import com.microFinance1.entities.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-06T21:08:34+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
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

        userOutputDto.setFirstName( user.getFirstName() );
        userOutputDto.setLastName( user.getLastName() );
        userOutputDto.setDob( user.getDob() );
        userOutputDto.setEmail( user.getEmail() );
        userOutputDto.setAge( user.getAge() );
        userOutputDto.setAadharCardNumber( user.getAadharCardNumber() );
        userOutputDto.setPanCardNumber( user.getPanCardNumber() );
        userOutputDto.setPhoneNumber( user.getPhoneNumber() );
        userOutputDto.setRoleName( user.getRoleName() );

        return userOutputDto;
    }
}
