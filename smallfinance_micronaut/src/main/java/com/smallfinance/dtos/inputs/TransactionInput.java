package com.smallfinance.dtos.inputs;

import lombok.Data;

@Data
public class TransactionInput {
    private Long to;

    private Double amount;

    private String purpose;

    private Long accountNumber;

    private String type;

    private String trans;

}
