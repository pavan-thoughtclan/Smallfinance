package com.tc.training.mapper;


import com.tc.training.dtos.inputdto.FixedDepositInputDto;
import com.tc.training.dtos.inputdto.TransactionInputDto;
import com.tc.training.dtos.outputdto.FixedDepositOutputDto;
import com.tc.training.model.FixedDeposit;
import com.tc.training.utils.TransactionType;
import com.tc.training.utils.TypeOfTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FixedDepositMapper {

    FixedDepositMapper mapper = Mappers.getMapper(FixedDepositMapper.class);

    @Mapping(target = "fdId" , source = "fd.id")
    @Mapping(target = "interestRate" , source = "fd.interestRate")
    @Mapping(target = "interestAmountAdded" , source = "fd.interestAmount")
    FixedDepositOutputDto FixedDepositToFixedDepositOutputDto(FixedDeposit fd);

    FixedDeposit FixedDepositInputDtoToFixedDeposit(FixedDepositInputDto dto);

    @Mapping(target = "to" , source = "fd.accountNumber")
    @Mapping(target = "accountNumber" , source = "fd.accountNumber")
    TransactionInputDto FixedDepositToTransactionInputDto(FixedDeposit fd);
}
