package com.microFinance1.mapper;

import com.microFinance1.dtos.inputs.TransactionInputDto;
import com.microFinance1.dtos.outputs.TransactionOutputDto;
import com.microFinance1.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jsr330")
public interface TransactionMapper {
    TransactionMapper MAPPER= Mappers.getMapper(TransactionMapper.class);


    @Mapping(target = "toAccountNumber",source = "transactionInputDto.to")
    @Mapping(target = "whichTransaction",source = "transactionInputDto.type")
    @Mapping(target = "transactionType",source = "transactionInputDto.trans")
    @Mapping(target = "description",source = "transactionInputDto.purpose")
    Transaction mapToTransaction(TransactionInputDto transactionInputDto);


    @Mapping(target = "toAccountNumber" , source = "transaction.toAccountNumber")
    @Mapping(target = "fromAccountNumber",source = "transaction.fromAccountNumber")
    @Mapping(target = "transactionID",source = "transaction.id")
    TransactionOutputDto mapToTransactionOutputDto(Transaction transaction);

}
