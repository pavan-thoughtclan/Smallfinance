package com.smallfinance.services;

import com.smallfinance.dtos.inputs.AccountDetailsInput;
import com.smallfinance.dtos.outputs.AccountDetailsOutput;
import com.smallfinance.dtos.outputs.HomePageOutput;

import java.util.UUID;

public interface AccountDetailsService {
    AccountDetailsOutput create(AccountDetailsInput input);

    //AccountDetailsOutput getById(Long accountNumber);

    AccountDetailsOutput getById(Long accountNumber);

    AccountDetailsOutput getByUser(UUID userId);

    HomePageOutput getHomePageById(Long id);

    Double getBalance(Long accountNumber);

    AccountDetailsOutput verifyKyc(Long accNo);

//    AccountDetailsOutput getAccountDetails(Long accNum);


//    HomePageOutput getHomePageById(UUID id);
}
