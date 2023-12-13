package com.smallfinance.services.impl;

import com.smallfinance.dtos.inputs.TransactionInput;
import com.smallfinance.dtos.outputs.TransactionOutput;
import com.smallfinance.entities.AccountDetails;
import com.smallfinance.entities.Transaction;
import com.smallfinance.enums.TransactionType;
import com.smallfinance.enums.TypeOfSlab;
import com.smallfinance.mapper.TransactionMapper;
import com.smallfinance.repositories.AccountDetailsRepository;
import com.smallfinance.repositories.TransactionRepository;
import com.smallfinance.services.TransactionService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class provides the implementation of the TransactionService interface for all Transaction operations.
 */
@Singleton
public class TransactionServiceImpl implements TransactionService {

    @Inject
    private final AccountDetailsRepository accountDetailsRepository;
    @Inject
    private final TransactionRepository transactionRepository;

    @Singleton
    @Inject
    private final TransactionMapper transactionMapper;

    /**
     * Constructs a new instance of the TransactionServiceImpl class.
     *
     * @param accountDetailsRepository   Repository for account details.
     * @param transactionRepository      Repository for financial transactions.
     * @param transactionMapper          Mapper for converting transaction data transfer objects.
     */
    public TransactionServiceImpl(AccountDetailsRepository accountDetailsRepository, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.accountDetailsRepository = accountDetailsRepository;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    /**
     * Handles deposit transactions, updating account balances and transaction records.
     * Additionally, it checks the account's KYC status and performs account balance validations.
     *
     * @param transactionInputDto   Input data for the transaction.
     * @param accountNumber         Account number for the transaction.
     * @return                      TransactionOutput- Details of the completed transaction.
     */
    @Override
    @Transactional
    public TransactionOutput deposit(TransactionInput transactionInputDto, Long accountNumber) {
        Transaction transaction = transactionMapper.mapToTransaction(transactionInputDto);
        transaction.setTimestamp(LocalDateTime.now());

        if (transactionInputDto.getPurpose() != null) {
            transaction.setDescription(transactionInputDto.getPurpose());
        }

        AccountDetails accountDetails = accountDetailsRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!accountDetails.getKyc()) {
            throw new RuntimeException("KYC not completed");
        }

        if (transactionInputDto.getType().equals("DEPOSIT")) {
            transaction.setTo(accountDetailsRepository.findById(Long.valueOf(transactionInputDto.getTo()))
                    .orElseThrow(() -> new RuntimeException("To account not found")));
            transaction.setFrom(accountDetails);
            transaction.setTransactionType(TransactionType.CREDITED);
            transaction.setWhichTransaction(TypeOfSlab.DEPOSIT);
            accountDetails.setBalance(accountDetails.getBalance() + transactionInputDto.getAmount());
        } else if (transactionInputDto.getType().equals("WITHDRAWAL")) {
            transaction.setTo(accountDetailsRepository.findById(Long.valueOf(transactionInputDto.getTo()))
                    .orElseThrow(() -> new RuntimeException("To account not found")));
            transaction.setFrom(accountDetails);
            transaction.setTransactionType(TransactionType.DEBITED);
            transaction.setWhichTransaction(TypeOfSlab.WITHDRAWAL);
            accountDetails.setBalance(accountDetails.getBalance() - transactionInputDto.getAmount());
        } else if (transactionInputDto.getType().equals("TRANSFER")) {
            if (accountDetails.getBalance() < transactionInputDto.getAmount()) {
                throw new RuntimeException("Your transfer amount exceeds your balance amount");
            }
            transaction.setFrom(accountDetails);
            transaction.setTo(accountDetailsRepository.findById(Long.valueOf(transactionInputDto.getTo()))
                    .orElseThrow(() -> new RuntimeException("To account not found")));
            transaction.setTransactionType(TransactionType.DEBITED);
            transaction.setWhichTransaction(TypeOfSlab.TRANSFER);
            accountDetails.setBalance(accountDetails.getBalance() - transactionInputDto.getAmount());
            AccountDetails accountDetails1 = transaction.getTo();
            accountDetails1.setBalance(accountDetails1.getBalance() + transactionInputDto.getAmount());
            accountDetailsRepository.save(accountDetails1);
        } else if (transactionInputDto.getType().equals("FD") && transactionInputDto.getTrans().equals("CREDIT")) {
            transaction.setFrom(null);
            transaction.setTo(accountDetailsRepository.findById(Long.valueOf(transactionInputDto.getTo()))
                    .orElseThrow(() -> new RuntimeException("To account not found")));
            transaction.setTransactionType(TransactionType.CREDITED);
            transaction.setWhichTransaction(TypeOfSlab.FD);
            accountDetails.setBalance(accountDetails.getBalance() + transactionInputDto.getAmount());
        } else if (transactionInputDto.getType().equals("FD") && transactionInputDto.getTrans().equals("DEBIT")) {
            transaction.setFrom(accountDetails);
            transaction.setTo(null);
            transaction.setTransactionType(TransactionType.DEBITED);
            transaction.setWhichTransaction(TypeOfSlab.FD);
            accountDetails.setBalance(accountDetails.getBalance() - transactionInputDto.getAmount());
        } else if (transactionInputDto.getTrans().equals("CREDIT")) {
            transaction.setFrom(null);
            transaction.setTransactionType(TransactionType.CREDITED);

            if (transactionInputDto.getType().equals("HOME_LOAN")) {
                transaction.setWhichTransaction(TypeOfSlab.HOME_LOAN);
            } else if (transactionInputDto.getType().equals("GOLD_LOAN")) {
                transaction.setWhichTransaction(TypeOfSlab.GOLD_LOAN);
            } else if (transactionInputDto.getType().equals("EDUCATION_LOAN")) {
                transaction.setWhichTransaction(TypeOfSlab.EDUCATION_LOAN);
            } else if (transactionInputDto.getType().equals("PERSONAL_LOAN")) {
                transaction.setWhichTransaction(TypeOfSlab.PERSONAL_LOAN);
            } else if (transactionInputDto.getType().equals("RD")) {
                transaction.setWhichTransaction(TypeOfSlab.RD);
            }
            transaction.setTo(accountDetailsRepository.findById(Long.valueOf(transactionInputDto.getTo()))
                    .orElseThrow(() -> new RuntimeException("To account not found")));
            accountDetails.setBalance(accountDetails.getBalance() + transactionInputDto.getAmount());
        } else if (transactionInputDto.getTrans().equals("DEBIT")) {
            transaction.setFrom(accountDetails);
            transaction.setTransactionType(TransactionType.DEBITED);
            if (transactionInputDto.getType().equals("HOME_LOAN")) {
                transaction.setWhichTransaction(TypeOfSlab.HOME_LOAN);
            } else if (transactionInputDto.getType().equals("GOLD_LOAN")) {
                transaction.setWhichTransaction(TypeOfSlab.GOLD_LOAN);
            } else if (transactionInputDto.getType().equals("EDUCATION_LOAN")) {
                transaction.setWhichTransaction(TypeOfSlab.EDUCATION_LOAN);
            } else if (transactionInputDto.getType().equals("PERSONAL_LOAN")) {
                transaction.setWhichTransaction(TypeOfSlab.PERSONAL_LOAN);
            } else if (transactionInputDto.getType().equals("RD")) {
                transaction.setWhichTransaction(TypeOfSlab.RD);
            }
            accountDetails.setBalance(accountDetails.getBalance() - transactionInputDto.getAmount());
        }
        transaction.setBalance(accountDetails.getBalance());
        transaction.setTo ( accountDetailsRepository.findById(Long.valueOf(transactionInputDto.getTo()))
                .orElseThrow(() -> new RuntimeException("No account with this ID")));
        transactionRepository.save(transaction);
        accountDetailsRepository.update(accountDetails);

        TransactionOutput transactionOutputDto = transactionMapper.mapToTransactionOutputDto(transaction);
        transactionOutputDto.setAmount(transaction.getAmount());
        transactionOutputDto.setTransactionID(transaction.getId());
        if (transaction.getFrom() != null) {
            transactionOutputDto.setFromAccountNumber(String.valueOf(transaction.getFrom().getAccountNumber()));
        } else {
            transactionOutputDto.setFromAccountNumber("APA BANK");
        }
        if (transaction.getTo() != null) {
            transactionOutputDto.setToAccountNumber(String.valueOf(transaction.getTo().getAccountNumber()));
        } else {
            transactionOutputDto.setToAccountNumber("APA BANK");
        }
        transactionOutputDto.setBalance(accountDetails.getBalance());
        return transactionOutputDto;

    }

    /**
     * Retrieves a list of transactions within a specified date range and optional type
     * for a specific account.
     *
     * @param date1     Start date of the date range.
     * @param date2     End date of the date range.
     * @param type      Type of transaction (optional).
     * @param accNo     Account number for which transactions are retrieved.
     * @return          List of filtered transactions within the date range.
     */
    @Override
    public List<TransactionOutput> getAllTransactions(LocalDate date1, LocalDate date2, String type, Long accNo) {
        AccountDetails account = accountDetailsRepository.findById(accNo)
                .orElseThrow(() -> new RuntimeException("no account with this id"));

        LocalDateTime localDateTime1;
        LocalDateTime localDateTime2;

        if (date1 != null) {
            localDateTime1 = date1.atStartOfDay();
        } else {
            localDateTime1 = account.getOpeningDate().atStartOfDay();
        }

        if (date2 != null) {
            localDateTime2 = LocalDateTime.of(date2, LocalTime.of(23, 59, 59));
        } else {
            localDateTime2 = LocalDateTime.now();
        }

        return transactionRepository.findAllByUserAndDate(localDateTime1, localDateTime2, accNo)
                .stream()
                .filter(transaction -> {
                    if (type != null) {
                        return transaction.getTransactionType().equals(TransactionType.valueOf(type));
                    }
                    return true;
                })
                .map(transaction -> {
                    TransactionOutput tdo = transactionMapper.mapToTransactionOutputDto(transaction);
                    if (tdo.getFromAccountNumber() == null) {
                        tdo.setFromAccountNumber("APA BANK");
                    }
                    if (tdo.getToAccountNumber() == null) {
                        tdo.setToAccountNumber("APA BANK");
                    }
                    return tdo;
                })
                .sorted(Comparator.comparing(TransactionOutput::getTimestamp))
                .collect(Collectors.toList());
    }
}
