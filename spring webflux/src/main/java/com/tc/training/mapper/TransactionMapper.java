package com.tc.training.mapper;

import com.tc.training.dtos.inputdto.AccountDetailsInputDto;
import com.tc.training.dtos.inputdto.TransactionInputDto;
import com.tc.training.dtos.outputdto.AccountDetailsOutputDto;
import com.tc.training.dtos.outputdto.TransactionOutputDto;
import com.tc.training.model.AccountDetails;
import com.tc.training.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper mapper = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "toAccountNumber" , source = "transaction.to_id")
    @Mapping(target = "fromAccountNumber", source = "transaction.from_id")
    @Mapping(target = "transactionID", source = "transaction.id")
    TransactionOutputDto TransactionToTransactionOutputDto(Transaction transaction);

    @Mapping(target = "to_id" , source = "dto.to")
    @Mapping(target = "description" , source = "dto.purpose")
    @Mapping(target = "whichTransaction" , source = "dto.type")
    @Mapping(target = "transactionType", source = "dto.trans")
    Transaction TransactionInputDtoToTransaction(TransactionInputDto dto);
}
