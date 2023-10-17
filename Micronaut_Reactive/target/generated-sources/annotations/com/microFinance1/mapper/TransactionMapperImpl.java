package com.microFinance1.mapper;

import com.microFinance1.dtos.inputs.TransactionInputDto;
import com.microFinance1.dtos.outputs.TransactionOutputDto;
import com.microFinance1.entities.Transaction;
import com.microFinance1.utils.TransactionType;
import com.microFinance1.utils.TypeOfSlab;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-16T13:21:15+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Singleton
@Named
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction mapToTransaction(TransactionInputDto transactionInputDto) {
        if ( transactionInputDto == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setToAccountNumber( transactionInputDto.getTo() );
        if ( transactionInputDto.getType() != null ) {
            transaction.setWhichTransaction( Enum.valueOf( TypeOfSlab.class, transactionInputDto.getType() ) );
        }
        if ( transactionInputDto.getTrans() != null ) {
            transaction.setTransactionType( Enum.valueOf( TransactionType.class, transactionInputDto.getTrans() ) );
        }
        transaction.setDescription( transactionInputDto.getPurpose() );
        transaction.setAmount( transactionInputDto.getAmount() );

        return transaction;
    }

    @Override
    public TransactionOutputDto mapToTransactionOutputDto(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionOutputDto transactionOutputDto = new TransactionOutputDto();

        if ( transaction.getToAccountNumber() != null ) {
            transactionOutputDto.setToAccountNumber( String.valueOf( transaction.getToAccountNumber() ) );
        }
        if ( transaction.getFromAccountNumber() != null ) {
            transactionOutputDto.setFromAccountNumber( String.valueOf( transaction.getFromAccountNumber() ) );
        }
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
