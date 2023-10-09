package com.tc.training.smallfinance.service.Impl;

import com.tc.training.smallfinance.dtos.inputs.TransactionInputDto;
import com.tc.training.smallfinance.dtos.outputs.TransactionOutputDto;
import com.tc.training.smallfinance.exception.AccountNotFoundException;
import com.tc.training.smallfinance.exception.AmountNotSufficientException;
import com.tc.training.smallfinance.exception.KycNotCompletedException;
//import com.tc.training.smallFinance.mapper.TransactionMapper;
import com.tc.training.smallfinance.mapper.TransactionMapper;
import com.tc.training.smallfinance.model.AccountDetails;
import com.tc.training.smallfinance.model.Transaction;
import com.tc.training.smallfinance.repository.AccountRepository;
import com.tc.training.smallfinance.repository.TransactionRepository;
import com.tc.training.smallfinance.service.TransactionService;
import com.tc.training.smallfinance.utils.TransactionType;
import com.tc.training.smallfinance.utils.TypeOfSlab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    @Transactional
    public TransactionOutputDto  deposit(TransactionInputDto transactionInputDto,Long accountNumber) {
        Transaction transaction = transactionMapper.mapToTransaction(transactionInputDto);
        transaction.setTimestamp(LocalDateTime.now());
        if (transactionInputDto.getPurpose() != null) transaction.setDescription(transactionInputDto.getPurpose());
        AccountDetails accountDetails = accountRepository.findById(accountNumber).orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if(accountDetails.getKyc()==Boolean.FALSE) throw new KycNotCompletedException("kyc not completed");
        if(transactionInputDto.getType().equals("DEPOSIT")) {
            transaction.setTo(accountRepository.findById(Long.valueOf(transactionInputDto.getTo())).orElseThrow(()->new AccountNotFoundException("To account not found")));
            transaction.setFrom(accountDetails);
            transaction.setTransactionType(TransactionType.CREDITED);
            transaction.setWhichTransaction(TypeOfSlab.DEPOSIT);
            accountDetails.setBalance(accountDetails.getBalance() + transactionInputDto.getAmount());
        }
        else if(transactionInputDto.getType().equals("WITHDRAW")){
            transaction.setTo(accountRepository.findById(Long.valueOf(transactionInputDto.getTo())).orElseThrow(()->new AccountNotFoundException("To account not found")));
            transaction.setFrom(accountDetails);
            transaction.setTransactionType(TransactionType.DEBITED);
            transaction.setWhichTransaction(TypeOfSlab.WITHDRAWAL);
            accountDetails.setBalance(accountDetails.getBalance() - transactionInputDto.getAmount());
        }
        else if(transactionInputDto.getType().equals("TRANSFER")){
            if(accountDetails.getBalance()< transactionInputDto.getAmount()) throw new AmountNotSufficientException("your transfer amount exceeds yours balance amount");
            transaction.setFrom(accountDetails);
            transaction.setTo(accountRepository.findById(Long.valueOf(transactionInputDto.getTo())).orElseThrow(()->new AccountNotFoundException("To account not found")));
            transaction.setTransactionType(TransactionType.DEBITED);
            transaction.setWhichTransaction(TypeOfSlab.TRANSFER);
            accountDetails.setBalance(accountDetails.getBalance() - transactionInputDto.getAmount());
            AccountDetails accountDetails1 = transaction.getTo();
            accountDetails1.setBalance(accountDetails1.getBalance() + transactionInputDto.getAmount());
            accountRepository.save(accountDetails1);
        }
        else if(transactionInputDto.getType().equals("FD") && transactionInputDto.getTrans().equals("CREDIT")){
            transaction.setFrom(null);
            transaction.setTo(accountRepository.findById(Long.valueOf(transactionInputDto.getTo())).orElseThrow(()->new AccountNotFoundException("To account not found")));
            transaction.setTransactionType(TransactionType.CREDITED);
            transaction.setWhichTransaction(TypeOfSlab.FD);
            accountDetails.setBalance(accountDetails.getBalance() + transactionInputDto.getAmount());
        }
        else if(transactionInputDto.getType().equals("FD") && transactionInputDto.getTrans().equals("DEBIT")){
            transaction.setFrom(accountDetails);
            transaction.setTo(null);
            transaction.setTransactionType(TransactionType.DEBITED);
            transaction.setWhichTransaction(TypeOfSlab.FD);
            accountDetails.setBalance(accountDetails.getBalance() - transactionInputDto.getAmount());
        }
        else if(transactionInputDto.getTrans().equals("CREDIT")){
            transaction.setFrom(null);
            transaction.setTransactionType(TransactionType.CREDITED);
            if(transactionInputDto.getType().equals("HOME_LOAN")) {transaction.setWhichTransaction(TypeOfSlab.HOME_LOAN);}
            else if (transactionInputDto.getType().equals("GOLD_LOAN")) { transaction.setWhichTransaction(TypeOfSlab.GOLD_LOAN);}
            else if (transactionInputDto.getType().equals("EDUCATION_LOAN"))  {transaction.setWhichTransaction(TypeOfSlab.EDUCATION_LOAN);}
            else if (transactionInputDto.getType().equals("PERSONAL_LOAN")) { transaction.setWhichTransaction(TypeOfSlab.PERSONAL_LOAN);}
            else if (transactionInputDto.getType().equals("RD")) { transaction.setWhichTransaction(TypeOfSlab.RD);}
            transaction.setTo(accountRepository.findById(Long.valueOf(transactionInputDto.getTo())).orElseThrow(()->new AccountNotFoundException("To account not found")));
            accountDetails.setBalance(accountDetails.getBalance() + transactionInputDto.getAmount());
        }
        else if(transactionInputDto.getTrans().equals("DEBIT")){
            transaction.setFrom(accountDetails);
            transaction.setTransactionType(TransactionType.DEBITED);
            if(transactionInputDto.getType().equals("HOME_LOAN")) transaction.setWhichTransaction(TypeOfSlab.HOME_LOAN);
            else if (transactionInputDto.getType().equals("GOLD_LOAN"))  transaction.setWhichTransaction(TypeOfSlab.GOLD_LOAN);
            else if (transactionInputDto.getType().equals("EDUCATION_LOAN"))  transaction.setWhichTransaction(TypeOfSlab.EDUCATION_LOAN);
            else if (transactionInputDto.getType().equals("PERSONAL_LOAN"))  transaction.setWhichTransaction(TypeOfSlab.PERSONAL_LOAN);
            else if (transactionInputDto.getType().equals("RD")) { transaction.setWhichTransaction(TypeOfSlab.RD);}
            accountDetails.setBalance(accountDetails.getBalance() - transactionInputDto.getAmount());
        }
        transaction.setBalance(accountDetails.getBalance());
        transaction.setTo(accountRepository.findById(Long.valueOf(transactionInputDto.getTo())).orElseThrow(()->new AccountNotFoundException("no account with this id")));
        transactionRepository.save(transaction);
        accountRepository.save(accountDetails);
        TransactionOutputDto transactionOutputDto = transactionMapper.mapToTransactionOutputDto(transaction);
        transactionOutputDto.setAmount(transaction.getAmount());
        transactionOutputDto.setTransactionID(transaction.getId());
        if(transaction.getFrom()!=null) transactionOutputDto.setFromAccountNumber(String.valueOf(transaction.getFrom().getAccountNumber()));
        else transactionOutputDto.setFromAccountNumber("APA BANK");
        if(transaction.getTo()!=null) transactionOutputDto.setToAccountNumber(String.valueOf(transaction.getTo().getAccountNumber()));
        else transactionOutputDto.setToAccountNumber("APA BANK");
        transactionOutputDto.setBalance(accountDetails.getBalance());
        return transactionOutputDto;
    }

    @Override
    public List<TransactionOutputDto> getAllTransactions(LocalDate date1, LocalDate date2, String type,Long accNo) {
        LocalDateTime localDateTime1;
        LocalDateTime localDateTime2;
        if (date1!=null) localDateTime1= date1.atStartOfDay();
        else localDateTime1 = accountRepository.findById(accNo).orElseThrow(()->new AccountNotFoundException("wrong account number")).getOpeningDate().atStartOfDay();
        if (date2!=null) localDateTime2= LocalDateTime.of(date2, LocalTime.of(23,59,59));
        else localDateTime2 = LocalDateTime.now();
        List<Transaction> list  =  transactionRepository.findAllByUserAndDate(localDateTime1,localDateTime2,accNo);
        List<TransactionOutputDto> list1 = new ArrayList<>();
        for(Transaction t:list){
            if(type==null || type.equals("") || t.getTransactionType().equals(TransactionType.valueOf(type)) ) {
                TransactionOutputDto tdo = transactionMapper.mapToTransactionOutputDto(t);
                if (tdo.getFromAccountNumber() == null) tdo.setFromAccountNumber("APA BANK");
                if (tdo.getToAccountNumber() == null) tdo.setToAccountNumber("APA BANK");
                list1.add(tdo);
            }
        }
        Collections.sort(list1, Collections.reverseOrder(Comparator.comparing(TransactionOutputDto::getTimestamp)));
        return list1;
    }
}
