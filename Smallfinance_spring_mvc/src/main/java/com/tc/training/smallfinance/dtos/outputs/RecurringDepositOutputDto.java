package com.tc.training.smallfinance.dtos.outputs;

import com.tc.training.smallfinance.utils.RdStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class RecurringDepositOutputDto
{

    private UUID id;

    private String account;

    private Integer monthTenure;

    private Double monthlyPaidAmount;

    private  Double maturityAmount;

    private LocalDate startDate;

    private LocalDate maturityDate;

    private List<Long> transactionIds;

    private RdStatus status ;

    private String interest;



}
