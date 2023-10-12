package com.smallfinance.dtos.outputs;

import com.smallfinance.entities.AccountDetails;
import com.smallfinance.enums.RdStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class RecurringDepositOutput {
    private UUID rId;

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
