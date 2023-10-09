package com.tc.training.smallfinance.service.Impl;

import com.tc.training.smallfinance.dtos.inputs.AccountDetailsInputDto;
import com.tc.training.smallfinance.dtos.inputs.LoginInputDto;
import com.tc.training.smallfinance.dtos.outputs.LoginOutputDto;
import com.tc.training.smallfinance.dtos.outputs.UserOutputDto;
import com.tc.training.smallfinance.exception.AccountNotFoundException;
import com.tc.training.smallfinance.mapper.UserMapper;
import com.tc.training.smallfinance.model.AccountDetails;
import com.tc.training.smallfinance.model.User;
import com.tc.training.smallfinance.repository.AccountRepository;
import com.tc.training.smallfinance.repository.TransactionRepository;
import com.tc.training.smallfinance.repository.UserRepository;
import com.tc.training.smallfinance.service.TransactionService;
import com.tc.training.smallfinance.service.UserService;
import com.tc.training.smallfinance.utils.Role;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private EmailServiceImpl emailService;


    @Autowired
    private UserMapper userMapper;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*";


    public User addUser(AccountDetailsInputDto accountDetailsInputDto) {
        User user = new User();
        user.setFirstName(accountDetailsInputDto.getFirstName());
        user.setLastName(accountDetailsInputDto.getLastName());
        user.setDob(accountDetailsInputDto.getDob());
        user.setEmail(accountDetailsInputDto.getEmail());
        user.setPanCardNumber(accountDetailsInputDto.getPanCardNumber());
        user.setAadharCardNumber(accountDetailsInputDto.getAadharCardNumber());
        user.setPhoneNumber(accountDetailsInputDto.getPhoneNumber());
        user.setAge(calculateAge(accountDetailsInputDto.getDob()));
        user.setPassword(generateRandomPassword());
        return userRepository.save(user);
    }


    @Override
    public List<UserOutputDto> getAll() {
        List<User> userList = userRepository.findByRoleNameCustomer();
//        System.out.println(userList);
        List<UserOutputDto> list = userList.stream()
                .map(user -> userMapper.userToUserDto(user))
                .collect(Collectors.toList());

        // Sort the list based on custom logic (not handled by MapStruct)
        list.sort((o1, o2) -> {
            User user1 = userRepository.findById(o1.getUserId()).orElseThrow(() -> new AccountNotFoundException("no account with this id"));
            User user2 = userRepository.findById(o2.getUserId()).orElseThrow(() -> new AccountNotFoundException("no account with this id"));
            if (accountRepository.findByUser(user1).getOpeningDate().isAfter(accountRepository.findByUser(user2).getOpeningDate())) {
                return 1;
            } else if (accountRepository.findByUser(user1).getOpeningDate().isBefore(accountRepository.findByUser(user2).getOpeningDate())) {
                return -1;
            } else {
                return 0;
            }
        });

        return list;
    }


    @Override
    public UserOutputDto getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("account with this id not found"));
        UserOutputDto userOutputDto = userMapper.userToUserDto(user);
        userOutputDto.setKyc(accountRepository.findByUser(user).getKyc());
        userOutputDto.setAccountNumber(accountRepository.findByUser(user).getAccountNumber());
        return userOutputDto;
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
}