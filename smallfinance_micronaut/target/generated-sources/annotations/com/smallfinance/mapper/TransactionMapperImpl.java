package com.smallfinance.mapper;

import com.smallfinance.dtos.inputs.TransactionInput;
import com.smallfinance.dtos.outputs.TransactionOutput;
import com.smallfinance.entities.Transaction;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-06T16:53:45+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Singleton
@Named
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction mapToTransaction(TransactionInput transactionInputDto) {
        if ( transactionInputDto == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setAmount( transactionInputDto.getAmount() );

        transaction.setTo( mapToAccountDetails(transactionInputDto) );

        return transaction;
    }

    @Override
    public TransactionOutput mapToTransactionOutputDto(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionOutput transactionOutput = new TransactionOutput();

        transactionOutput.setTransactionID( transaction.getId() );
        transactionOutput.setAmount( transaction.getAmount() );
        transactionOutput.setTransactionType( transaction.getTransactionType() );
        transactionOutput.setTimestamp( transaction.getTimestamp() );
        transactionOutput.setWhichTransaction( transaction.getWhichTransaction() );
        transactionOutput.setBalance( transaction.getBalance() );
        transactionOutput.setDescription( transaction.getDescription() );

        return transactionOutput;
    }
}
