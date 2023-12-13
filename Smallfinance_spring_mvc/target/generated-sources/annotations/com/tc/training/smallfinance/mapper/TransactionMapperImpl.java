package com.tc.training.smallfinance.mapper;

import com.tc.training.smallfinance.dtos.inputs.TransactionInputDto;
import com.tc.training.smallfinance.dtos.outputs.TransactionOutputDto;
import com.tc.training.smallfinance.model.Transaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-13T15:34:05+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction mapToTransaction(TransactionInputDto transactionInputDto) {
        if ( transactionInputDto == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setAmount( transactionInputDto.getAmount() );

        transaction.setTo( mapToAccountDetails(transactionInputDto) );

        return transaction;
    }

    @Override
    public TransactionOutputDto mapToTransactionOutputDto(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionOutputDto transactionOutputDto = new TransactionOutputDto();

        transactionOutputDto.setTransactionID( transaction.getId() );
        transactionOutputDto.setAmount( transaction.getAmount() );
        transactionOutputDto.setTransactionType( transaction.getTransactionType() );
        transactionOutputDto.setTimestamp( transaction.getTimestamp() );
        transactionOutputDto.setWhichTransaction( transaction.getWhichTransaction() );
        transactionOutputDto.setBalance( transaction.getBalance() );
        transactionOutputDto.setDescription( transaction.getDescription() );

        return transactionOutputDto;
    }
}
