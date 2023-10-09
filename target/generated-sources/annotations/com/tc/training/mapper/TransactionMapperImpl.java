package com.tc.training.mapper;

import com.tc.training.dtos.inputdto.TransactionInputDto;
import com.tc.training.dtos.outputdto.TransactionOutputDto;
import com.tc.training.model.Transaction;
import com.tc.training.utils.TransactionType;
import com.tc.training.utils.TypeOfTransaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-06T16:55:51+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public TransactionOutputDto TransactionToTransactionOutputDto(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionOutputDto transactionOutputDto = new TransactionOutputDto();

        if ( transaction.getTo_id() != null ) {
            transactionOutputDto.setToAccountNumber( String.valueOf( transaction.getTo_id() ) );
        }
        if ( transaction.getFrom_id() != null ) {
            transactionOutputDto.setFromAccountNumber( String.valueOf( transaction.getFrom_id() ) );
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

    @Override
    public Transaction TransactionInputDtoToTransaction(TransactionInputDto dto) {
        if ( dto == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setTo_id( dto.getTo() );
        transaction.setDescription( dto.getPurpose() );
        if ( dto.getType() != null ) {
            transaction.setWhichTransaction( Enum.valueOf( TypeOfTransaction.class, dto.getType() ) );
        }
        if ( dto.getTrans() != null ) {
            transaction.setTransactionType( Enum.valueOf( TransactionType.class, dto.getTrans() ) );
        }
        transaction.setAmount( dto.getAmount() );

        return transaction;
    }
}
