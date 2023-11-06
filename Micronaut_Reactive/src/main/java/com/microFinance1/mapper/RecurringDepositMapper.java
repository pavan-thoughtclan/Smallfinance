package com.microFinance1.mapper;

import com.microFinance1.dtos.inputs.RecurringDepositInputDto;
import com.microFinance1.dtos.inputs.TransactionInputDto;
import com.microFinance1.dtos.outputs.RecurringDepositOutputDto;
import com.microFinance1.dtos.outputs.TransactionOutputDto;
import com.microFinance1.entities.RecurringDeposit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel ="jsr330" )
public interface RecurringDepositMapper {
    RecurringDepositMapper MAPPER= Mappers.getMapper(RecurringDepositMapper.class);
    RecurringDeposit mapToRecurringDeposit(RecurringDepositInputDto recurringDepositInputDto);
    @Mapping(target = "account",source = "recurringDeposit.accountNumber")
    RecurringDepositOutputDto mapToRecurringDepositOutputDto(RecurringDeposit recurringDeposit);
    @Mapping(target = "to",source = "rd.accountNumber")
    @Mapping(target = "amount",source = "rd.monthlyPaidAmount")
    TransactionInputDto recurringDepositToTransactionInputDto(RecurringDeposit rd);

}
