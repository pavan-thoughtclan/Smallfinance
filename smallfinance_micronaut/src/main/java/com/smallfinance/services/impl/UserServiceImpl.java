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

@Singleton

public class UserServiceImpl implements UserService {
    @Inject
    private final UserRepository userRepository;
    @Inject
    private final AccountDetailsRepository accountDetailsRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*";

    @Singleton
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, AccountDetailsRepository accountDetailsRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.accountDetailsRepository = accountDetailsRepository;
        this.userMapper = userMapper;
    }

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
        user.setRoleName(Role.CUSTOMER);
        return userRepository.save(user);
    }

    public static int calculateAge(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dob, currentDate);
        return period.getYears();
    }

    public static String generateRandomPassword() {
        StringBuilder password = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < 9; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(randomIndex));

        }

        return password.toString();
    }

    @Override
    public UserOutput getById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with this id"));
        return userMapper.userToUserDto(user);
    }

    @Override
    public List<UserOutput> getAll() {
        List<User> users = userRepository.findAll();
//        return users.map(user -> {
//            UserOutputDto dto = userMapper.mapToUserOutputDto(user);
//            return dto;
//        })
        return users.stream().map(user -> userMapper.userToUserDto(user)).toList();
    }
}
