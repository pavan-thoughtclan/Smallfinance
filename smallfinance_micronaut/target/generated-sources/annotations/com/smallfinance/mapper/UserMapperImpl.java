package com.smallfinance.mapper;

import com.smallfinance.dtos.outputs.UserOutput;
import com.smallfinance.entities.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-06T16:53:45+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Singleton
@Named
public class UserMapperImpl implements UserMapper {

    @Override
    public UserOutput userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserOutput userOutput = new UserOutput();

        userOutput.setUserId( user.getId() );
        userOutput.setFirstName( user.getFirstName() );
        userOutput.setLastName( user.getLastName() );
        userOutput.setDob( user.getDob() );
        userOutput.setEmail( user.getEmail() );
        userOutput.setAge( user.getAge() );
        userOutput.setAadharCardNumber( user.getAadharCardNumber() );
        userOutput.setPanCardNumber( user.getPanCardNumber() );
        userOutput.setPhoneNumber( user.getPhoneNumber() );
        userOutput.setRoleName( user.getRoleName() );

        return userOutput;
    }

    @Override
    public User userDtoToUser(UserOutput output) {
        if ( output == null ) {
            return null;
        }

        User user = new User();

        user.setFirstName( output.getFirstName() );
        user.setLastName( output.getLastName() );
        user.setDob( output.getDob() );
        user.setEmail( output.getEmail() );
        user.setAge( output.getAge() );
        user.setAadharCardNumber( output.getAadharCardNumber() );
        user.setPanCardNumber( output.getPanCardNumber() );
        user.setPhoneNumber( output.getPhoneNumber() );
        user.setRoleName( output.getRoleName() );

        return user;
    }
}
