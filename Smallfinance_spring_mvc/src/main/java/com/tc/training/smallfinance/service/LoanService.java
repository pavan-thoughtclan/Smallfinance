package com.tc.training.smallfinance.service;

import com.tc.training.smallfinance.dtos.inputs.LoanInputDto;
import com.tc.training.smallfinance.dtos.outputs.LoanOutputDto;

import java.util.List;
import java.util.UUID;

public interface LoanService {
    LoanOutputDto addLoan(LoanInputDto loanInputDto);

    LoanOutputDto getById(UUID id);

    List<LoanOutputDto> getAllByUser(Long accNo);

    List<LoanOutputDto> getAll();

    LoanOutputDto setLoan(UUID id,String status);

    List<LoanOutputDto> getBytype(Long accNo, String type);

    Double getTotalLoanAmount(Long accNo);

    List<LoanOutputDto> getAllPending();

    List<LoanOutputDto> getAllByNotPending();

    List<LoanOutputDto> getAllByStatus(String s);
}
