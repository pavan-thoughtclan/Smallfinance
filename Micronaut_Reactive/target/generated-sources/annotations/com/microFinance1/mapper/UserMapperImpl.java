package com.microFinance1.mapper;

import com.microFinance1.dtos.outputs.UserOutputDto;
import com.microFinance1.entities.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-13T16:16:11+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 17.0.9 (Eclipse Adoptium)"
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
