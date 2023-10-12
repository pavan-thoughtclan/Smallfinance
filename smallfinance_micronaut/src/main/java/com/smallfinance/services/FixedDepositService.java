package com.smallfinance.services;

import com.smallfinance.dtos.inputs.FixedDepositInput;
import com.smallfinance.dtos.outputs.FDDetails;
import com.smallfinance.dtos.outputs.FixedDepositOutput;

import java.util.List;
import java.util.UUID;

public interface FixedDepositService {
    List<FixedDepositOutput> getAllActive(Long accNo);

    FixedDepositOutput createFixedDeposit(FixedDepositInput fixedDepositInput);
    List<FixedDepositOutput> allFixedDeposit(Long accNo);

    FDDetails getFDDetails(Long accNo);

    FixedDepositOutput breakFixedDeposit(String id);

    List<FixedDepositOutput> getAll();

    FixedDepositOutput getById(UUID id);
}
