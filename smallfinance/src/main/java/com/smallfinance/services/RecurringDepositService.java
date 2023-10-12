package com.smallfinance.services;

import com.smallfinance.dtos.inputs.RecurringDepositInput;
import com.smallfinance.dtos.outputs.RecurringDepositOutput;

import java.util.List;
import java.util.UUID;

public interface RecurringDepositService {
//    List<RecurringDepositOutput> getByStatus(Long accNo);
//
//    Double getTotalMoneyInvested(Long accNo);

    RecurringDepositOutput create(RecurringDepositInput recurringDepositInputDto);

    RecurringDepositOutput getById(UUID id);

    List<RecurringDepositOutput> getAll();

    List<RecurringDepositOutput> getAllRecurringDeposit(Long accNo);

    Double getTotalMoneyInvested(Long accNo);

    List<RecurringDepositOutput> getByStatus(Long accNo);
}
