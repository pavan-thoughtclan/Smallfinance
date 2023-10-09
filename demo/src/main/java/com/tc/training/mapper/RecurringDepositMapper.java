package com.tc.training.mapper;

import com.tc.training.dtos.inputdto.RecurringDepositInputDto;
import com.tc.training.dtos.inputdto.TransactionInputDto;
import com.tc.training.dtos.outputdto.RecurringDepositOutputDto;
import com.tc.training.model.RecurringDeposit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.persistence.ManyToOne;

@Mapper(componentModel = "spring")
public interface RecurringDepositMapper {


    RecurringDeposit inputDtToRecurringDeposit(RecurringDepositInputDto recurringDepositInputDto);

    @Mapping(target = "account" , source = "recurringDeposit.accountNumber")
    RecurringDepositOutputDto RecurringDepositToOutputDto(RecurringDeposit recurringDeposit);


    @Mapping(target = "to" , source = "rd.accountNumber")
    @Mapping(target = "amount" , source = "rd.monthlyPaidAmount")
    TransactionInputDto RecurringDepositToTransactionInputDto(RecurringDeposit rd);

}
