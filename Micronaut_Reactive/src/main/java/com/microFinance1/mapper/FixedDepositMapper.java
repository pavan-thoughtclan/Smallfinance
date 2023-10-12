package com.microFinance1.mapper;

import com.microFinance1.dtos.inputs.FixedDepositInputDto;
import com.microFinance1.dtos.inputs.TransactionInputDto;
import com.microFinance1.dtos.outputs.FixedDepositOutputDto;
import com.microFinance1.entities.FixedDeposit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jsr330")
public interface FixedDepositMapper {
    FixedDepositMapper MAPPER= Mappers.getMapper(FixedDepositMapper.class);

    FixedDepositOutputDto mapToFixedDepositOutputDto(FixedDeposit fixedDeposit);
    FixedDeposit mapToFixedDeposit(FixedDepositInputDto fixedDepositInputDto);

    @Mapping(target = "to" , source = "fd.accountNumber")
    @Mapping(target = "accountNumber" , source = "fd.accountNumber")
    TransactionInputDto FixedDepositToTransactionInputDto(FixedDeposit fd);

}
