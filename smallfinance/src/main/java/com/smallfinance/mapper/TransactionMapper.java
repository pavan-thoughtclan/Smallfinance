package com.smallfinance.mapper;

import com.smallfinance.dtos.inputs.TransactionInput;
import com.smallfinance.dtos.outputs.TransactionOutput;
import com.smallfinance.entities.AccountDetails;
import com.smallfinance.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jsr330")
public interface TransactionMapper {

//    @Mapping(target = "to", expression = "java(mapToAccountDetails(transactionInputDto))")
//    Transaction mapToTransaction(TransactionInput transactionInputDto);
//
//    @Mapping(target = "transactionID", source = "id")
//    TransactionOutput mapToTransactionOutputDto(Transaction transaction);
//
//    default AccountDetails mapToAccountDetails(TransactionInput transactionInputDto) {
//        if (transactionInputDto == null || transactionInputDto.getAccountNumber() == null) {
//            return null;
//        }
//        AccountDetails accountDetails = new AccountDetails();
//        accountDetails.setAccountNumber(Long.valueOf(transactionInputDto.getAccountNumber()));
//        return accountDetails;
//    }

    @Mapping(target = "to", expression = "java(mapToAccountDetails(transactionInputDto))")
    Transaction mapToTransaction(TransactionInput transactionInputDto);

    @Mapping(target = "transactionID", source = "id")
    TransactionOutput mapToTransactionOutputDto(Transaction transaction);

    default AccountDetails mapToAccountDetails(TransactionInput transactionInputDto) {
        if (transactionInputDto == null || transactionInputDto.getAccountNumber() == null) {
            return null;
        }
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccountNumber(Long.valueOf(transactionInputDto.getAccountNumber()));
// Set other properties as needed
        return accountDetails;
    }
}
