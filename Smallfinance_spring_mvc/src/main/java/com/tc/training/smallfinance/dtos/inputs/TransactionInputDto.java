package com.tc.training.smallfinance.dtos.inputs;

import lombok.Data;

@Data
public class TransactionInputDto {

    //private String from;

    private Long to;   //STring

    private Double amount;

    private String purpose;

    private Long accountNumber; //string

    private String type;

    private String trans;
}
