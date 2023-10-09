package com.tc.training.service.impl;

import com.tc.training.dtos.inputdto.TransactionInputDto;
import com.tc.training.dtos.outputdto.TransactionOutputDto;
import com.tc.training.exception.AccountNotFoundException;
import com.tc.training.exception.AmountNotSufficientException;
import com.tc.training.exception.KycNotCompletedException;
import com.tc.training.mapper.TransactionMapper;
import com.tc.training.model.Transaction;
import com.tc.training.repo.AccountDetailsRepository;
import com.tc.training.repo.TransactionRepository;
import com.tc.training.service.TransactionService;
import com.tc.training.utils.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountDetailsRepository accountRepository;
    @Autowired
    private TransactionMapper transactionMapper;


    @Override
    public Mono<TransactionOutputDto> deposit(TransactionInputDto transactionInputDto, Long accountNumber) {

        return performTransaction(transactionInputDto,accountNumber);

    }

    private Mono<TransactionOutputDto> performTransaction(TransactionInputDto transactionInputDto,Long accountNumber) {

        switch(transactionInputDto.getType()){
            case "DEPOSIT" : return depositTransaction(transactionInputDto,accountNumber);
            case "WITHDRAWAL" : return withdrawTransaction(transactionInputDto,accountNumber);
            case "TRANSFER" : return transferTransaction(transactionInputDto,accountNumber);
            case "FD" : return FdTransaction(transactionInputDto,accountNumber);
            case "RD" : return RdTransaction(transactionInputDto,accountNumber);
            default: return loantransaction(transactionInputDto,accountNumber);
        }

    }

    private Mono<TransactionOutputDto> RdTransaction(TransactionInputDto transactionInputDto, Long accountNumber) {


        return accountRepository.findById(transactionInputDto.getAccountNumber())
                .switchIfEmpty(Mono.error(new AccountNotFoundException("Account with this id not found")))
                .flatMap(account -> {
                    if(transactionInputDto.getTrans().equals("DEBITED"))
                        account.setBalance(account.getBalance() - transactionInputDto.getAmount());
                    else account.setBalance(account.getBalance() + transactionInputDto.getAmount());
                    return accountRepository.save(account);
                })
                .flatMap(account -> {
                    Transaction transaction = transactionMapper.TransactionInputDtoToTransaction(transactionInputDto);
                    transaction.setTimestamp(LocalDateTime.now());
                    if(transaction.getTransactionType().equals(TransactionType.DEBITED))
                        transaction.setFrom_id(account.getAccountNumber());

                    transaction.setBalance(account.getBalance());
                    return transactionRepository.save(transaction);
                })
                .map(transaction -> transactionMapper.TransactionToTransactionOutputDto(transaction));

    }

    private Mono<TransactionOutputDto> loantransaction(TransactionInputDto transactionInputDto, Long accountNumber) {

        return accountRepository.findById(transactionInputDto.getAccountNumber())
                .switchIfEmpty(Mono.error(new AccountNotFoundException("Account with this id not found")))
                .flatMap(account -> {
                    if(transactionInputDto.getTrans().equals("DEBITED"))
                        account.setBalance(account.getBalance() - transactionInputDto.getAmount());
                    else account.setBalance(account.getBalance() + transactionInputDto.getAmount());
                    return accountRepository.save(account);
                })
                .flatMap(account -> {
                    Transaction transaction = transactionMapper.TransactionInputDtoToTransaction(transactionInputDto);
                    transaction.setTimestamp(LocalDateTime.now());
                    if(transaction.getTransactionType().equals(TransactionType.DEBITED))
                        transaction.setFrom_id(account.getAccountNumber());
                    //transaction.setTransactionType(TransactionType.DEBITED);
                    transaction.setBalance(account.getBalance());
                    return transactionRepository.save(transaction);
                })
                .map(transaction -> transactionMapper.TransactionToTransactionOutputDto(transaction));

    }

    private Mono<TransactionOutputDto> FdTransaction(TransactionInputDto transactionInputDto, Long accountNumber) {

        return accountRepository.findById(transactionInputDto.getAccountNumber())
                .switchIfEmpty(Mono.error(new AccountNotFoundException("Account with this id not found")))
                .flatMap(account -> {
                    if(transactionInputDto.getTrans().equals("DEBITED"))
                    account.setBalance(account.getBalance() - transactionInputDto.getAmount());
                    else account.setBalance(account.getBalance() + transactionInputDto.getAmount());
                    return accountRepository.save(account);
                })
                .flatMap(account -> {
                    Transaction transaction = transactionMapper.TransactionInputDtoToTransaction(transactionInputDto);
                    transaction.setTimestamp(LocalDateTime.now());
                    if(transaction.getTransactionType().equals(TransactionType.DEBITED))
                        transaction.setFrom_id(account.getAccountNumber());

                    transaction.setBalance(account.getBalance());
                    return transactionRepository.save(transaction);
                })
                .map(transaction -> transactionMapper.TransactionToTransactionOutputDto(transaction));
    }




    private Mono<TransactionOutputDto> transferTransaction( TransactionInputDto transactionInputDto,Long accountNumber) {

        return accountRepository.findById(transactionInputDto.getAccountNumber())
                .switchIfEmpty(Mono.error(new AccountNotFoundException("no account with this id")))
                .flatMap(account -> {
                    if(!account.getKyc()) return Mono.error(new KycNotCompletedException("kyc not completed"));
                    if(account.getBalance() < transactionInputDto.getAmount()) return Mono.error(new AmountNotSufficientException("amount exceeds balance"));

                    return accountRepository.findById(transactionInputDto.getTo())
                            .switchIfEmpty(Mono.error(new AccountNotFoundException("no account with this id")))
                            .flatMap(toAccount -> {
                                toAccount.setBalance(toAccount.getBalance() + transactionInputDto.getAmount());
                                return accountRepository.save(toAccount);

                            })
                            .flatMap(saveAcc -> {
                                account.setBalance(account.getBalance() - transactionInputDto.getAmount());
                                return accountRepository.save(account);
                            });

                })
                .flatMap(account -> {
                    Transaction transaction = transactionMapper.TransactionInputDtoToTransaction(transactionInputDto);
                    transaction.setTimestamp(LocalDateTime.now());
                    transaction.setFrom_id(account.getAccountNumber());
                    transaction.setBalance(account.getBalance());
                    return transactionRepository.save(transaction);
                })
                .map(transaction -> transactionMapper.TransactionToTransactionOutputDto(transaction));

    }

    private Mono<TransactionOutputDto> withdrawTransaction( TransactionInputDto transactionInputDto,Long accountNumber) {

        return accountRepository.findById(accountNumber)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("no account with this id")))
                .flatMap(account -> {
                    if(!account.getKyc()) return Mono.error(new KycNotCompletedException("kyc not completed"));
                    if(account.getBalance() < transactionInputDto.getAmount()) return Mono.error(new AmountNotSufficientException("amount exceeds balance"));
                    account.setBalance(account.getBalance() - transactionInputDto.getAmount());
                    return accountRepository.save(account);
                })
                .flatMap(account -> {
                    Transaction transaction = transactionMapper.TransactionInputDtoToTransaction(transactionInputDto);
                    transaction.setTimestamp(LocalDateTime.now());
                    transaction.setFrom_id(account.getAccountNumber());
                    //transaction.setTransactionType(TransactionType.DEBITED);
                    transaction.setBalance(account.getBalance());
                    return transactionRepository.save(transaction);
                })
                .map(transaction -> transactionMapper.TransactionToTransactionOutputDto(transaction));
    }

    private Mono<TransactionOutputDto> depositTransaction( TransactionInputDto transactionInputDto , Long accountNumber) {

        return accountRepository.findById(accountNumber)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("no account with this id")))
                .filter(account -> account.getKyc().equals(Boolean.TRUE))
                .switchIfEmpty(Mono.error(new KycNotCompletedException("kyc not done")))
                .flatMap(account -> {
                    account.setBalance(account.getBalance() + transactionInputDto.getAmount());
                    return accountRepository.save(account);
                })
                .flatMap(account -> {
                    Transaction transaction = transactionMapper.TransactionInputDtoToTransaction(transactionInputDto);
                    transaction.setTimestamp(LocalDateTime.now());
                    transaction.setFrom_id(account.getAccountNumber());
                    transaction.setTransactionType(TransactionType.CREDITED);
                    transaction.setBalance(account.getBalance());
                    return transactionRepository.save(transaction);
                })
                .map(transaction -> transactionMapper.TransactionToTransactionOutputDto(transaction));
    }

    @Override
    public Flux<TransactionOutputDto> getAllTransactions(LocalDate date1, LocalDate date2, String type, Long accNo) {


        return accountRepository.findById(accNo)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("no account with this id")))
                .flatMapMany(account -> {
                    LocalDateTime localDateTime1;
                    LocalDateTime localDateTime2;
                    if(date1 != null) localDateTime1 =date1.atStartOfDay();
                    else localDateTime1 = account.getOpeningDate().atStartOfDay();

                    if(date2 != null) localDateTime2 = LocalDateTime.of(date2, LocalTime.of(23,59,59));
                    else localDateTime2 = LocalDateTime.now();

                    return transactionRepository.findAllByUserAndDate(localDateTime1,localDateTime2,accNo)
                            .filter(transaction -> {
                                if(type!=null) return transaction.getTransactionType().equals(TransactionType.valueOf(type));
                                return Boolean.TRUE;
                            })
                            .log()
                            .map(transaction -> {
                                TransactionOutputDto tdo = transactionMapper.TransactionToTransactionOutputDto(transaction);
                                if (tdo.getFromAccountNumber() == null) {
                                    tdo.setFromAccountNumber("APA BANK");
                                }
                                if (tdo.getToAccountNumber() == null) {
                                    tdo.setToAccountNumber("APA BANK");
                                }
                                return tdo;
                            })
                            .collectSortedList(Comparator.comparing(TransactionOutputDto::getTimestamp).reversed())
                            .flatMapMany(Flux::fromIterable);
                });

    }
}
