package com.smallfinance.services;

import com.smallfinance.dtos.outputs.FDDetails;

import java.util.List;

public interface DepositService {
    FDDetails getDetails(Long accNo);

    List<Object> getAccounts(Long accNo);
}
