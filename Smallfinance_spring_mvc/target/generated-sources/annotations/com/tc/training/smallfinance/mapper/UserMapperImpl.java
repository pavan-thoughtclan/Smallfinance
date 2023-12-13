package com.tc.training.smallfinance.mapper;

import com.tc.training.smallfinance.dtos.outputs.UserOutputDto;
import com.tc.training.smallfinance.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-28T12:52:11+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserOutputDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserOutputDto userOutputDto = new UserOutputDto();

        userOutputDto.setUserId( user.getId() );
        userOutputDto.setFirstName( user.getFirstName() );
        userOutputDto.setLastName( user.getLastName() );
        userOutputDto.setDob( user.getDob() );
        userOutputDto.setEmail( user.getEmail() );
        userOutputDto.setAge( user.getAge() );
        userOutputDto.setAadharCardNumber( user.getAadharCardNumber() );
        userOutputDto.setPanCardNumber( user.getPanCardNumber() );
        userOutputDto.setPhoneNumber( user.getPhoneNumber() );
        userOutputDto.setRoleName( user.getRoleName() );

        mapAccountDetailsFields( user, userOutputDto );

        return userOutputDto;
    }

    @Override
    public User map(UserOutputDto userOutputDto) {
        if ( userOutputDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userOutputDto.getUserId() );
        user.setFirstName( userOutputDto.getFirstName() );
        user.setLastName( userOutputDto.getLastName() );
        user.setDob( userOutputDto.getDob() );
        user.setEmail( userOutputDto.getEmail() );
        user.setAge( userOutputDto.getAge() );
        user.setAadharCardNumber( userOutputDto.getAadharCardNumber() );
        user.setPanCardNumber( userOutputDto.getPanCardNumber() );
        user.setPhoneNumber( userOutputDto.getPhoneNumber() );
        user.setRoleName( userOutputDto.getRoleName() );

        return user;
    }
}
