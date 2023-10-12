package com.microFinance1.services.impls;

import com.microFinance1.dtos.inputs.TransactionInputDto;
import com.microFinance1.dtos.outputs.TransactionOutputDto;
import com.microFinance1.entities.Transaction;
import com.microFinance1.exceptions.AmountNotSufficientException;
import com.microFinance1.exceptions.KycNotCompletedException;
import com.microFinance1.mapper.TransactionMapper;
import com.microFinance1.repository.AccountRepository;
import com.microFinance1.repository.TransactionRepository;
import com.microFinance1.services.TransactionService;
import com.microFinance1.utils.TransactionType;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
@Singleton
public class TransactionServiceImpl implements TransactionService {
    @Inject
    private TransactionMapper transactionMapper;
    @Inject
    private TransactionRepository transactionRepository;
    @Inject
    private AccountRepository accountRepository;
    @Override
    public Mono<TransactionOutputDto> deposit(TransactionInputDto transactionInputDto, Long accountNumber) {

        return performTransaction(transactionInputDto,accountNumber);

    }
    @Transactional
    protected Mono<TransactionOutputDto> performTransaction(TransactionInputDto transactionInputDto,Long accountNumber) {

        switch(transactionInputDto.getType()){
            case "DEPOSIT" : return depositTransaction(transactionInputDto,accountNumber);
            case "WITHDRAWAL" : return withdrawTransaction(transactionInputDto,accountNumber);
            case "TRANSFER" : return transferTransaction(transactionInputDto,accountNumber);
            case "FD": return fdTransaction(transactionInputDto, accountNumber);
            case "RD":return rdTransaction(transactionInputDto,accountNumber);
            default: return loanTransaction(transactionInputDto,accountNumber);
        }

    }
    @Transactional

    protected Mono<TransactionOutputDto> transferTransaction( TransactionInputDto transactionInputDto,Long accountNumber) {

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
                    Transaction transaction = transactionMapper.mapToTransaction(transactionInputDto);
                    transaction.setTimestamp(LocalDateTime.now());
                    transaction.setFromAccountNumber(account.getAccountNumber());
                    transaction.setBalance(account.getBalance());
                    return transactionRepository.save(transaction);
                })
                .map(transaction -> transactionMapper.mapToTransactionOutputDto(transaction));

    }
    @Transactional
    protected Mono<TransactionOutputDto> withdrawTransaction( TransactionInputDto transactionInputDto,Long accountNumber) {
        return accountRepository.findById(accountNumber)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("no account with this id")))
                .flatMap(account -> {
                    if(!account.getKyc()) return Mono.error(new KycNotCompletedException("kyc not completed"));
                    if(account.getBalance() < transactionInputDto.getAmount()) return Mono.error(new AmountNotSufficientException("amount exceeds balance"));
                    account.setBalance(account.getBalance() - transactionInputDto.getAmount());
                    return accountRepository.save(account);
                })
                .flatMap(account -> {
                    Transaction transaction = transactionMapper.mapToTransaction(transactionInputDto);
                    transaction.setTimestamp(LocalDateTime.now());
                    transaction.setFromAccountNumber(account.getAccountNumber());
                    transaction.setBalance(account.getBalance());
                    return transactionRepository.save(transaction);
                })
                .map(transaction -> transactionMapper.mapToTransactionOutputDto(transaction));
    }
    @Transactional

    protected Mono<TransactionOutputDto> depositTransaction( TransactionInputDto transactionInputDto , Long accountNumber) {

        return accountRepository.findById(accountNumber)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("no account with this id")))
                .flatMap(account -> {
                    if(!account.getKyc()) return Mono.error(new KycNotCompletedException("kyc not completed"));
                    account.setBalance(account.getBalance() + transactionInputDto.getAmount());
                    return accountRepository.update(account);
                })
                .flatMap(account-> {
                    Transaction transaction = transactionMapper.mapToTransaction(transactionInputDto);
                    transaction.setTimestamp(LocalDateTime.now());
                    transaction.setFromAccountNumber(account.getAccountNumber());
                    transaction.setTransactionType(TransactionType.CREDITED);
                    transaction.setBalance(account.getBalance());
                    return transactionRepository.save(transaction);
                })
                .map(transaction -> transactionMapper.mapToTransactionOutputDto(transaction));
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
                                TransactionOutputDto tdo = transactionMapper.mapToTransactionOutputDto(transaction);
                                if (tdo.getFromAccountNumber() == null) {
                                    tdo.setFromAccountNumber("APA BANK");
                                }
                                if (tdo.getToAccountNumber() == null) {
                                    tdo.setToAccountNumber("APA BANK");
                                }
                                return tdo;
                            })
                            .collectSortedList(Comparator.comparing(TransactionOutputDto::getTimestamp))
                            .flatMapMany(Flux::fromIterable);
                });

    }
    @Transactional
    protected Mono<TransactionOutputDto> fdTransaction(TransactionInputDto transactionInputDto, Long accountNumber) {

        return accountRepository.findById(transactionInputDto.getAccountNumber())
                .switchIfEmpty(Mono.error(new AccountNotFoundException("Account with this id not found")))
                .flatMap(account -> {
                    if(transactionInputDto.getTrans().equals("DEBITED"))
                        account.setBalance(account.getBalance() - transactionInputDto.getAmount());
                    else account.setBalance(account.getBalance() + transactionInputDto.getAmount());
                    return accountRepository.update(account);
                })
                .flatMap(account -> {
                    Transaction transaction = transactionMapper.mapToTransaction(transactionInputDto);
                    transaction.setTimestamp(LocalDateTime.now());
                    transaction.setFromAccountNumber(account.getAccountNumber());
                    transaction.setBalance(account.getBalance());
                    return transactionRepository.save(transaction);
                })
                .map(transaction -> transactionMapper.mapToTransactionOutputDto(transaction));
    }
    @Transactional
    protected Mono<TransactionOutputDto> loanTransaction(TransactionInputDto transactionInputDto, Long accountNumber) {

        return accountRepository.findById(transactionInputDto.getAccountNumber())
                .switchIfEmpty(Mono.error(new AccountNotFoundException("Account with this id not found")))
                .flatMap(account -> {
                    if(transactionInputDto.getTrans().equals("DEBITED"))
                        account.setBalance(account.getBalance() - transactionInputDto.getAmount());
                    else account.setBalance(account.getBalance() + transactionInputDto.getAmount());
                    return accountRepository.update(account);
                })
                .flatMap(account -> {
                    Transaction transaction = transactionMapper.mapToTransaction(transactionInputDto);
                    transaction.setTimestamp(LocalDateTime.now());
                    if(transaction.getTransactionType().equals(TransactionType.DEBITED))
                        transaction.setFromAccountNumber(account.getAccountNumber());
                    //transaction.setTransactionType(TransactionType.DEBITED);
                    transaction.setBalance(account.getBalance());
                    return transactionRepository.save(transaction);
                })
                .map(transaction -> transactionMapper.mapToTransactionOutputDto(transaction));

    }
    @Transactional
    protected Mono<TransactionOutputDto> rdTransaction(TransactionInputDto transactionInputDto, Long accountNumber) {


        return accountRepository.findById(transactionInputDto.getAccountNumber())
                .switchIfEmpty(Mono.error(new AccountNotFoundException("Account with this id not found")))
                .flatMap(account -> {
                    if(transactionInputDto.getTrans().equals("DEBITED"))
                        account.setBalance(account.getBalance() - transactionInputDto.getAmount());
                    else account.setBalance(account.getBalance() + transactionInputDto.getAmount());
                    return accountRepository.update(account);
                })
                .flatMap(account -> {
                    Transaction transaction = transactionMapper.mapToTransaction(transactionInputDto);
                    transaction.setTimestamp(LocalDateTime.now());
                    if(transaction.getTransactionType().equals(TransactionType.DEBITED))
                        transaction.setFromAccountNumber(account.getAccountNumber());

                    transaction.setBalance(account.getBalance());
                    return transactionRepository.save(transaction);
                })
                .map(transaction -> transactionMapper.mapToTransactionOutputDto(transaction));

    }
}
