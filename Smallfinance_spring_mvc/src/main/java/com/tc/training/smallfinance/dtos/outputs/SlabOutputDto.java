package com.tc.training.smallfinance.dtos.outputs;

import lombok.Data;

import java.util.UUID;
@Data
public class SlabOutputDto {

    private UUID slabId;

    private String tenures;

    private String interestRate;

    private String typeOfTransaction;
}
