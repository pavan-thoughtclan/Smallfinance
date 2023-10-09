package com.tc.training.smallfinance.mapper;

import com.tc.training.smallfinance.dtos.outputs.FixedDepositOutputDto;
import com.tc.training.smallfinance.model.FixedDeposit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FixedDepositMapper {
    FixedDepositMapper MAPPER = Mappers.getMapper(FixedDepositMapper.class);

    @Mapping(target = "accountNumber", source = "accountNumber.accountNumber")
    @Mapping(target = "fdId", source = "id")
    FixedDepositOutputDto mapToFixedDepositOutputDto(FixedDeposit fixedDeposit);
}
