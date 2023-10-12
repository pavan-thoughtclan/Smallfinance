package com.smallfinance.mapper;

import com.smallfinance.dtos.outputs.FixedDepositOutput;
import com.smallfinance.entities.FixedDeposit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jsr330")
public interface FixedDepositMapper {

    //
    @Mapping(target = "accountNumber", source = "accountNumber.accountNumber")
    @Mapping(target = "fdId", source = "id")
    FixedDepositOutput mapToFixedDepositOutputDto(FixedDeposit fixedDeposit);
}
