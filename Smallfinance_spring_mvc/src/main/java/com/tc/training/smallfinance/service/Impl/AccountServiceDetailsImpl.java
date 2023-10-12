package com.tc.training.smallfinance.service.Impl;

import com.tc.training.smallfinance.dtos.inputs.AccountDetailsInputDto;
//import com.tc.training.smallFinance.dtos.inputs.FirebaseUserInputDto;

import com.tc.training.smallfinance.dtos.outputs.AccountDetailsOutputDto;
import com.tc.training.smallfinance.dtos.outputs.FDDetails;
import com.tc.training.smallfinance.dtos.outputs.HomePageOutputDto;
import com.tc.training.smallfinance.exception.AccountNotFoundException;
import com.tc.training.smallfinance.exception.CustomException;
import com.tc.training.smallfinance.mapper.AccountDetailsMapper;
import com.tc.training.smallfinance.mapper.UserMapper;
import com.tc.training.smallfinance.model.AccountDetails;
import com.tc.training.smallfinance.model.User;
import com.tc.training.smallfinance.repository.AccountRepository;
import com.tc.training.smallfinance.service.*;
import com.tc.training.smallfinance.utils.AccountType;
import org.apache.tomcat.util.codec.binary.Base64;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;


@Service
public class AccountServiceDetailsImpl implements AccountServiceDetails {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private DepositService depositService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private AccountDetailsMapper accountDetailsMapper;
    @Autowired
    private UserMapper userMapper;

    private static long lastTimestamp = 8804175630060000L;

    private static int sequence = 0;

    @Override
    public AccountDetailsOutputDto createAccount(AccountDetailsInputDto accountDetailsInputDto) {
        AccountDetails accountDetails = accountDetailsMapper.mapToAccountDetails(accountDetailsInputDto);
        accountDetails.setUser(userService.addUser(accountDetailsInputDto));
        accountDetails.setAccountNumber(generateUniqueAccountNumber());
        accountDetails.setOpeningDate(LocalDate.now());
        accountDetails.setAccountType(AccountType.Savings); // Set the account type to Savings
        accountDetails.setBalance(0D); // Set the initial balance to 0.0
        accountDetails.setKyc(false); // Set KYC to false by default
        try {
//            sendEmail(accountDetails.getUser().getEmail(), accountDetails.getUser().getPassword(), accountDetails.getAccountNumber());
            AccountDetailsOutputDto outputDto = accountDetailsMapper.mapToAccountDetailsOutputDto(accountDetails);

            outputDto.setEmail(accountDetails.getUser().getEmail());
            AccountDetails accountDetails1 = accountDetailsMapper.mapToAccountDetails1(accountDetails);

            accountDetails = accountRepository.save(accountDetails);
        }catch(Exception e){throw new CustomException("error in creating account");}
        AccountDetailsOutputDto accountDetailsOutputDto =  accountDetailsMapper.mapToAccountDetailsOutputDto(accountDetails);

        accountDetailsOutputDto.setEmail(accountDetails.getUser().getEmail());
        return accountDetailsOutputDto;
    }

    @Override
    public AccountDetailsOutputDto getAccount(Long accNo) {
        AccountDetails accountDetails =  accountRepository.getById(accNo);
        User user = accountDetails.getUser();
        String userEmail = user.getEmail();
        AccountDetailsOutputDto accountOutputDto = accountDetailsMapper.mapToAccountDetailsOutputDto(accountDetails);
        accountOutputDto.setEmail(userEmail);
        return accountOutputDto;
    }

    @Override
    public Double getBalance(Long accNo) {
        return accountRepository.findById(accNo).get().getBalance();
    }

    @Override
    public AccountDetailsOutputDto getAccountByUser(UUID userId) {
        User user = userMapper.map(userService.getById(userId));
        AccountDetailsOutputDto accountDto = accountDetailsMapper.mapToAccountDetailsOutputDto(accountRepository.findByUser(user));
        accountDto.setEmail(user.getEmail());
        return accountDto;
    }

    @Override
    public HomePageOutputDto getHomePageDetails(Long accNo) {
        AccountDetails accountDetails = accountRepository.findById(accNo).orElseThrow(()->new AccountNotFoundException("no account found with this id"));
        HomePageOutputDto homePageOutputDto = new HomePageOutputDto();
        homePageOutputDto.setBalance(accountDetails.getBalance());
        FDDetails fdDetails = depositService.getDetails(accNo);
        homePageOutputDto.setDepositAmount(fdDetails.getTotalFdAmount() + fdDetails.getTotalRdAmount());
        homePageOutputDto.setLoanAmount(loanService.getTotalLoanAmount(accNo));
        homePageOutputDto.setKyc(accountDetails.getKyc());
        return homePageOutputDto;
    }

    @Override
    public AccountDetailsOutputDto verifyKyc(Long accNo) {
        AccountDetails accountDetails = accountRepository.findById(accNo).orElseThrow(()->new AccountNotFoundException("no account found with this id"));
        accountDetails.setKyc(Boolean.TRUE);
        accountRepository.save(accountDetails);
        User user = accountDetails.getUser();
        String userEmail = user.getEmail();
        AccountDetailsOutputDto accountOutputDto = accountDetailsMapper.mapToAccountDetailsOutputDto(accountDetails);

        accountOutputDto.setEmail(userEmail);
        return accountOutputDto;
    }


    private synchronized Long generateUniqueAccountNumber() {
        List<AccountDetails> accountDetailsList = accountRepository.findAll();
        if(!accountDetailsList.isEmpty()) {
            Collections.sort(accountDetailsList, Comparator.comparing(AccountDetails::getAccountNumber));
            lastTimestamp = accountDetailsList.get(accountDetailsList.size() - 1).getAccountNumber();
        }
        return ++lastTimestamp;
    }
}
