package com.tc.training.smallfinance.dtos.inputs;

import lombok.Data;

@Data
public class TransactionInputDto {

    //private String from;

    private String to;

    private Double amount;

    private String purpose;

    private String accountNumber;

    private String type;

    private String trans;
}
