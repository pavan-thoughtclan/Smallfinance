package com.tc.training.service.impl;

import com.tc.training.dtos.inputdto.RecurringDepositInputDto;
import com.tc.training.dtos.inputdto.TransactionInputDto;
import com.tc.training.dtos.outputdto.RecurringDepositOutputDto;
import com.tc.training.dtos.outputdto.TransactionOutputDto;
import com.tc.training.exception.AccountNotFoundException;
import com.tc.training.exception.AmountNotSufficientException;
import com.tc.training.exception.CustomException;
import com.tc.training.mapper.RecurringDepositMapper;
import com.tc.training.model.RecurringDeposit;
import com.tc.training.model.RecurringDepositPayment;
import com.tc.training.repo.AccountDetailsRepository;
import com.tc.training.repo.RecurringDepositPaymentRepository;
import com.tc.training.repo.RecurringDepositRepository;
import com.tc.training.repo.SlabRepository;
import com.tc.training.service.AccountDetailsService;
import com.tc.training.service.RecurringDepositService;
import com.tc.training.service.TransactionService;
import com.tc.training.utils.PaymentStatus;
import com.tc.training.utils.Tenures;
import com.tc.training.utils.TypeOfSlab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.*;

@Service
public class RecurringDepositServiceImpl implements RecurringDepositService {

    @Autowired
    private RecurringDepositRepository recurringDepositRepository;
    @Autowired
    private RecurringDepositPaymentRepository recurringDepositPaymentRepository;
    @Autowired
    private SlabRepository slabRepository;
    @Autowired
    private AccountDetailsRepository accountRepository;
    @Autowired
    private AccountDetailsService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private RecurringDepositMapper recurringDepositMapper;


    @Override
    @Transactional
    public Mono<RecurringDepositOutputDto> saveRd(RecurringDepositInputDto recurringDepositInputDto) {
        return accountRepository.findById(recurringDepositInputDto.getAccountNumber())
                .switchIfEmpty(Mono.error(new AccountNotFoundException("no account with this id")))
                .filter(account -> account.getBalance() > recurringDepositInputDto.getMonthlyPaidAmount())
                .switchIfEmpty(Mono.error(new AmountNotSufficientException("your balance is lower than the rd amount")))
                .flatMap(account -> {
                    RecurringDeposit rd = recurringDepositMapper.inputDtToRecurringDeposit(recurringDepositInputDto);
                    return slabRepository.findByTenuresAndTypeOfSlab(Tenures.ONE_YEAR.toString(), TypeOfSlab.RD.toString())
                            .switchIfEmpty(Mono.error(new CustomException("no slab with this details")))
                            .flatMap(slab -> {
                                rd.setStartDate(LocalDate.now());
                                rd.setInterest(slab.getInterestRate());
                                rd.setMaturityDate(rd.getStartDate().plusMonths(rd.getMonthTenure()));
                                return calculateMaturityAmount(rd,rd.getMonthTenure())
                                        .flatMap(amount -> {
                                            rd.setMaturityAmount(amount);
                                            rd.setNextPaymentDate(rd.getStartDate().plusMonths(1));
                                            return Mono.just(rd);
                                        })
                                        .flatMap(rdPay -> recurringDepositRepository.save(rd))
                                        .flatMap(recurringDeposit ->  setPayment(recurringDeposit, PaymentStatus.PAID,1))
                                        .map(recurringDeposit -> recurringDepositMapper.RecurringDepositToOutputDto(recurringDeposit));
                            });
                });
    }
    @Transactional
    private Mono<RecurringDeposit> setPayment(RecurringDeposit recurringDeposit, PaymentStatus paymentStatus, int i) {

        return setTransaction(recurringDeposit,"DEBITED",recurringDeposit.getMonthlyPaidAmount())
                .flatMap(transaction -> {
                    RecurringDepositPayment rdPay = new RecurringDepositPayment();
                    rdPay.setPayAmount(recurringDeposit.getMonthlyPaidAmount());
                    rdPay.setRecurringDeposit_id(recurringDeposit.getId());
                    rdPay.setPaymentStatus(paymentStatus);
                    rdPay.setMonthNumber(i);
                    rdPay.setTransactionId(transaction.getTransactionID());
                    return Mono.just(rdPay);
                })
                .flatMap(rdPay -> recurringDepositPaymentRepository.save(rdPay))
                .thenReturn(recurringDeposit);

    }

    private Mono<TransactionOutputDto> setTransaction(RecurringDeposit recurringDeposit, String status, Double monthlyPaidAmount) {

        TransactionInputDto inputDto = recurringDepositMapper.RecurringDepositToTransactionInputDto(recurringDeposit);
        inputDto.setPurpose("Rd amount " + status);
        inputDto.setType("RD");
        inputDto.setTrans(status);
        inputDto.setAmount(monthlyPaidAmount);
        if(status.equals("DEBITED")) inputDto.setTo(null);
        return transactionService.deposit(inputDto, recurringDeposit.getAccountNumber());
    }

    private Mono<Double> calculateMaturityAmount(RecurringDeposit rd, Integer noOfMonths) {

        Double p = rd.getMonthlyPaidAmount() ;
        Double r = Double.valueOf(rd.getInterest()) / 100;
        Integer t =  noOfMonths / 12;
        Integer n=4;
        Double amount = 0D;
        return Flux.range(0, noOfMonths)
                .map(i -> {
                    double exponent = ((i / 12.0) * n);
                    return p * Math.pow(1 + r / n, exponent);
                })
                .reduce(0.0, Double::sum);
    }

    @Override
    public Mono<RecurringDepositOutputDto> getById(UUID id) {
        return recurringDepositRepository.findById(id)
                .map(recurringDepositMapper::RecurringDepositToOutputDto);
    }

    @Override
    public Flux<RecurringDepositOutputDto> getAll() {
        return recurringDepositRepository.findAll()
                .map(recurringDepositMapper::RecurringDepositToOutputDto)
                .sort(Comparator.comparing(RecurringDepositOutputDto :: getStatus));
    }


    @Override
    public Flux<RecurringDepositOutputDto> getAllRecurringDeposit(Long accNo) {
        return recurringDepositRepository.findByAccount(accNo)
                .map(recurringDepositMapper::RecurringDepositToOutputDto);
    }

    @Override
    public Mono<Double> getTotalMoneyInvested(Long accNo) {
        return recurringDepositRepository.findByAccountAndStatus(accNo, "ACTIVE")
                .flatMap(rd -> recurringDepositPaymentRepository.findByRecurringDepositId(rd.getId())
                        .map(RecurringDepositPayment::getPayAmount)
                        .reduce(0.0,Double::sum))
                .reduce(0.0,Double::sum);
    }

    @Override
    public Flux<RecurringDepositOutputDto> getByStatus(Long accNo) {
        return recurringDepositRepository.findByAccountAndStatus(accNo,"ACTIVE")
                .map(recurringDepositMapper::RecurringDepositToOutputDto);
    }
}
