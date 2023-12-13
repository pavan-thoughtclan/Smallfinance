package com.smallfinance.services;

import com.smallfinance.dtos.inputs.LoanInput;
import com.smallfinance.dtos.outputs.LoanOutput;

import java.util.List;
import java.util.UUID;

public interface LoanService {
    Double getTotalLoanAmount(Long accNo);

    LoanOutput addLoan(LoanInput loanInputDto);
    List<LoanOutput> getAllByUser(Long accNo);

    List<LoanOutput> getAll();

    LoanOutput setLoan(UUID id, String status);

    List<LoanOutput> getByType(Long accNo, String type);

    List<LoanOutput> getAllPending();

    List<LoanOutput> getAllByNotPending();

    List<LoanOutput> getAllByStatus(String status);

    LoanOutput getById(UUID id);
}
