package com.tc.training.smallfinance.mapper;

import com.tc.training.smallfinance.dtos.inputs.TransactionInputDto;
import com.tc.training.smallfinance.dtos.outputs.TransactionOutputDto;
import com.tc.training.smallfinance.model.AccountDetails;
import com.tc.training.smallfinance.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper MAPPER = Mappers.getMapper(TransactionMapper.class);

//    @Mapping(source = "amount", target = "amount")
//    @Mapping(source = "purpose", target = "description")
//    @Mapping(source = "trans", target = "transactionType")

    @Mapping(target = "to", expression = "java(mapToAccountDetails(transactionInputDto))")
    Transaction mapToTransaction(TransactionInputDto transactionInputDto);

    @Mapping(target = "transactionID", source = "id")
    TransactionOutputDto mapToTransactionOutputDto(Transaction transaction);

    default AccountDetails mapToAccountDetails(TransactionInputDto transactionInputDto) {
        if (transactionInputDto == null || transactionInputDto.getAccountNumber() == null) {
            return null;
        }
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccountNumber(Long.valueOf(transactionInputDto.getAccountNumber()));
        // Set other properties as needed
        return accountDetails;
    }
}
