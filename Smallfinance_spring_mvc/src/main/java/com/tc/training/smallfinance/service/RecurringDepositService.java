package com.tc.training.smallfinance.service;

import com.tc.training.smallfinance.dtos.inputs.RecurringDepositInputDto;
import com.tc.training.smallfinance.dtos.outputs.RecurringDepositOutputDto;

import java.util.List;
import java.util.UUID;

public interface RecurringDepositService {
    RecurringDepositOutputDto saveRd(RecurringDepositInputDto recurringDepositService);

    RecurringDepositOutputDto getById(UUID id);

    List<RecurringDepositOutputDto> getAll();

    List<RecurringDepositOutputDto> getAllRecurringDeposit(Long accNo);

    Double getTotalMoneyInvested(Long accNo);

    List<RecurringDepositOutputDto> getByStatus(Long accNo);
}
