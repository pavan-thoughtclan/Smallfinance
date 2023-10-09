package com.tc.training.smallfinance.service;

import com.tc.training.smallfinance.dtos.outputs.FDDetails;

import java.util.List;

public interface DepositService {
    FDDetails getDetails(Long accNo);

    List<Object> getAccounts(Long accNo);
}
