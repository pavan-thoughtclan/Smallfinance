package com.smallfinance.services.impl;

import com.smallfinance.dtos.inputs.RecurringDepositInput;
import com.smallfinance.dtos.inputs.TransactionInput;
import com.smallfinance.dtos.outputs.RecurringDepositOutput;
import com.smallfinance.dtos.outputs.TransactionOutput;
import com.smallfinance.entities.*;
import com.smallfinance.enums.PaymentStatus;
import com.smallfinance.enums.RdStatus;
import com.smallfinance.enums.Tenures;
import com.smallfinance.enums.TypeOfSlab;
import com.smallfinance.mapper.RecurringDepositMapper;
import com.smallfinance.repositories.AccountDetailsRepository;
import com.smallfinance.repositories.RecurringDepositPaymentRepository;
import com.smallfinance.repositories.RecurringDepositRepository;
import com.smallfinance.repositories.SlabRepository;
import com.smallfinance.services.RecurringDepositService;
import com.smallfinance.services.TransactionService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDate;
import java.util.*;

@Singleton
public class RecurringDepositServiceImpl implements RecurringDepositService {
    @Inject
    private final RecurringDepositRepository recurringDepositRepository;
    @Inject
    private final AccountDetailsRepository accountDetailsRepository;
    @Inject
    private final SlabRepository slabRepository;
    @Inject
    private final TransactionService transactionService;
    @Inject
    private final RecurringDepositMapper recurringDepositMapper;
    @Inject
    private final RecurringDepositPaymentRepository recurringDepositPaymentRepository;

    public RecurringDepositServiceImpl(RecurringDepositRepository recurringDepositRepository, AccountDetailsRepository accountDetailsRepository, SlabRepository slabRepository, TransactionService transactionService, RecurringDepositMapper recurringDepositMapper, RecurringDepositPaymentRepository recurringDepositPaymentRepository) {
        this.recurringDepositRepository = recurringDepositRepository;
        this.accountDetailsRepository = accountDetailsRepository;
        this.slabRepository = slabRepository;
        this.transactionService = transactionService;
        this.recurringDepositMapper = recurringDepositMapper;
        this.recurringDepositPaymentRepository = recurringDepositPaymentRepository;
    }
    @Override
    public RecurringDepositOutput create(RecurringDepositInput recurringDepositInputDto) {
        RecurringDeposit rd = recurringDepositMapper.mapToRecurringDeposit(recurringDepositInputDto);
        rd.setAccountNumber(accountDetailsRepository.findById(recurringDepositInputDto.getAccountNumber()).orElseThrow(() -> new RuntimeException("account with this id not found")));
        Slabs slab = slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.RD);
        if(rd.getAccountNumber().getBalance() < recurringDepositInputDto.getMonthlyPaidAmount()) throw new RuntimeException("no sufficient funds to open the account");
        rd.setInterest(slab.getInterestRate());
        rd.setStartDate(LocalDate.now());
        rd.setMaturityDate(rd.getStartDate().plusMonths(rd.getMonthTenure()));
        rd.setMaturityAmount(calculateMaturityAmount(rd,rd.getMonthTenure()));
        rd.setNextPaymentDate(rd.getStartDate().plusMonths(1));
        RecurringDepositPayment rdPay = new RecurringDepositPayment();
        rdPay.setRecurringDeposit(rd);
        rdPay.setPaymentStatus(PaymentStatus.PAID);
        rdPay.setPayAmount(rd.getMonthlyPaidAmount());
        rdPay.setMonthNumber(1);
        rdPay.setTransactionId(setTransaction(rd,"DEBIT",rd.getMonthlyPaidAmount()));
        rd = recurringDepositRepository.save(rd);
        recurringDepositPaymentRepository.save(rdPay);
        RecurringDepositOutput rdout = recurringDepositMapper.mapToRecurringDepositOutputDto(rd);
        rdout.setAccount(String.valueOf(rd.getAccountNumber().getAccountNumber()));
        return rdout;
    }

    private Double calculateMaturityAmount(RecurringDeposit rd,Integer noOfMonths) {
        Double p = rd.getMonthlyPaidAmount() ;
        Double r = Double.valueOf(rd.getInterest()) / 100;
        Integer t =  noOfMonths / 12;
        Integer n=4;
        // Double amount = Math.pow(principal * (1 + quarterlyInterest) , (4 * years));
        Double amount = 0D;
        for(int i=noOfMonths;i>=0;i--){
            amount +=  p * Math.pow(( 1 + r/n) ,  ((i/12) * n));
        }
        return amount;
    }

    @Override
    public RecurringDepositOutput getById(UUID id) {
        RecurringDeposit rd = recurringDepositRepository.findById(id).orElseThrow(() -> new RuntimeException("rd account not found"));
        RecurringDepositOutput rdout = recurringDepositMapper.mapToRecurringDepositOutputDto(rd);
        rdout.setAccount(String.valueOf(rd.getAccountNumber().getAccountNumber()));
        return rdout;
    }

    @Override
    public List<RecurringDepositOutput> getAll() {
        List<RecurringDeposit> rds = recurringDepositRepository.findAll();
        List<RecurringDepositOutput> rdouts = new ArrayList<>();
        for (RecurringDeposit r : rds) {
            RecurringDepositOutput rd = recurringDepositMapper.mapToRecurringDepositOutputDto(r);
            rd.setAccount(String.valueOf(r.getAccountNumber().getAccountNumber()));
            rdouts.add(rd);
        }
        Collections.sort(rdouts, new Comparator<RecurringDepositOutput>() {
            @Override
            public int compare(RecurringDepositOutput o1, RecurringDepositOutput o2) {
                if (o1.getStatus().equals(RdStatus.ACTIVE) && o2.getStatus().equals(RdStatus.ACTIVE)) return 0;
                else if (o1.getStatus().equals(RdStatus.ACTIVE) && o2.getStatus().equals(RdStatus.CLOSED)) return 1;
                else if (o1.getStatus().equals(RdStatus.ACTIVE) && o2.getStatus().equals(RdStatus.MATURED)) return 1;
                else return -1;
            }
        });
        return rdouts;
    }


    @Override
    public List<RecurringDepositOutput> getAllRecurringDeposit(Long accNo) {
        List<RecurringDeposit> rdList = recurringDepositRepository.findByAccount(accNo);
        List<RecurringDepositOutput> rdoutList = new ArrayList<>();
        for (RecurringDeposit rd : rdList) {
            RecurringDepositOutput rdout = recurringDepositMapper.mapToRecurringDepositOutputDto(rd);
            rdout.setAccount(String.valueOf(rd.getAccountNumber().getAccountNumber()));
            rdoutList.add(rdout);
        }
        return rdoutList;
    }

    @Override
    public Double getTotalMoneyInvested(Long accNo) {
        List<RecurringDeposit> rds = recurringDepositRepository.findByAccountAndStatus(accNo,RdStatus.ACTIVE.name());
        Double sum = 0D;
        for (RecurringDeposit r : rds) {
            List<RecurringDepositPayment> rpay = recurringDepositPaymentRepository.findAllByRecurringDeposit(r);
            for (RecurringDepositPayment recurringDepositPayment : rpay) {
                if (recurringDepositPayment.getPaymentStatus().equals(PaymentStatus.PAID))
                    sum += recurringDepositPayment.getPayAmount();
            }
        }
        return sum;
    }

    @Override
    public List<RecurringDepositOutput> getByStatus(Long accNo) {
        List<RecurringDeposit> rdList = recurringDepositRepository.findByAccountAndStatus(accNo, RdStatus.ACTIVE.name());
        List<RecurringDepositOutput> rdoutList = new ArrayList<>();
        for (RecurringDeposit rd : rdList) {
            RecurringDepositOutput rdout = recurringDepositMapper.mapToRecurringDepositOutputDto(rd);
            rdout.setAccount(String.valueOf(rd.getAccountNumber().getAccountNumber()));
            rdoutList.add(rdout);
        }
        return rdoutList;
    }

    private UUID setTransaction(RecurringDeposit rd, String status, Double amount) {
        TransactionInput transaction = new TransactionInput();
        if (status.equals("CREDIT")) {
            transaction.setType("RD");
            transaction.setAmount(amount);
            transaction.setAccountNumber(Long.valueOf(String.valueOf(rd.getAccountNumber().getAccountNumber())));
            transaction.setTo(Long.valueOf(String.valueOf(rd.getAccountNumber().getAccountNumber())));
            transaction.setPurpose("RD amount credited");
        } else {
            transaction.setType("RD");
            transaction.setAmount(amount);
            transaction.setAccountNumber(Long.valueOf(String.valueOf(rd.getAccountNumber().getAccountNumber())));
            transaction.setTo(Long.valueOf(String.valueOf(rd.getAccountNumber().getAccountNumber())));
            transaction.setPurpose("RD amount debited");
        }
        transaction.setTrans(status);
        UUID tId = transactionService.deposit(transaction, rd.getAccountNumber().getAccountNumber()).getTransactionID();
        return tId;
    }


    private void closeAccount(RecurringDeposit rd, String status) {
        if (status.equals("closed")) rd.setStatus(RdStatus.CLOSED);
        else rd.setStatus(RdStatus.MATURED);
        recurringDepositRepository.save(rd);
    }
}
