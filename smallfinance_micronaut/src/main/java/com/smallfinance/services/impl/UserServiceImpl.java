package com.smallfinance.services.impl;

import com.smallfinance.dtos.inputs.AccountDetailsInput;
import com.smallfinance.dtos.outputs.UserOutput;
import com.smallfinance.entities.User;
import com.smallfinance.enums.Role;
import com.smallfinance.mapper.UserMapper;
import com.smallfinance.repositories.AccountDetailsRepository;
import com.smallfinance.repositories.UserRepository;
import com.smallfinance.services.UserService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

/**
 * User Service class implementation responsible for managing user-related operations.
 */
@Singleton
public class UserServiceImpl implements UserService {
    @Inject
    private final UserRepository userRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*";

    @Singleton
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Adds a new user based on the provided AccountDetailsInput data.
     *
     * @param input The input data for creating a new user.
     * @return The created User entity.
     */
    @Override
    public User addUser(AccountDetailsInput input) {
        User user = new User();
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setDob(input.getDob());
        user.setEmail(input.getEmail());
        user.setPanCardNumber(input.getPanCardNumber());
        user.setAadharCardNumber(input.getAadharCardNumber());
        user.setPhoneNumber(input.getPhoneNumber());
        user.setAge(calculateAge(input.getDob()));
        user.setPassword(generateRandomPassword());
        user.setRoleName(Role.ROLE_CUSTOMER);
        return userRepository.save(user);
    }

    /**
     * Calculates the age based on the provided date of birth.
     *
     * @param dob The date of birth.
     * @return The calculated age.
     */
    public static int calculateAge(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dob, currentDate);
        return period.getYears();
    }

    /**
     * Generates a random password string for new users.
     *
     * @return A randomly generated password.
     */
    public static String generateRandomPassword() {
        StringBuilder password = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < 9; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(randomIndex));

        }

        return password.toString();
    }

    /**
     * Retrieves a user's information by their unique ID.
     *
     * @param id The id of the user.
     * @return The UserOutput DTO representing the user's information.
     */
    @Override
    public UserOutput getById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with this id"));
        return userMapper.userToUserDto(user);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return A list of UserOutput DTOs representing user information.
     */
    @Override
    public List<UserOutput> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::userToUserDto).toList();
    }
}
