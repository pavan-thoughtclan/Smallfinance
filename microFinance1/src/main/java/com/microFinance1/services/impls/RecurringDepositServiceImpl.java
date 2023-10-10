package com.microFinance1.services.impls;

import com.microFinance1.dtos.inputs.RecurringDepositInputDto;
import com.microFinance1.dtos.inputs.TransactionInputDto;
import com.microFinance1.dtos.outputs.RecurringDepositOutputDto;
import com.microFinance1.dtos.outputs.TransactionOutputDto;
import com.microFinance1.entities.RecurringDeposit;
import com.microFinance1.entities.RecurringDepositPayment;
import com.microFinance1.exceptions.AmountNotSufficientException;
import com.microFinance1.exceptions.CustomException;
import com.microFinance1.mapper.RecurringDepositMapper;
import com.microFinance1.repository.AccountRepository;
import com.microFinance1.repository.RecurringDepositPaymentRepository;
import com.microFinance1.repository.RecurringDepositRepository;
import com.microFinance1.repository.SlabRepository;
import com.microFinance1.services.RecurringDepositService;
import com.microFinance1.services.TransactionService;
import com.microFinance1.utils.PaymentStatus;
import com.microFinance1.utils.Tenures;
import com.microFinance1.utils.TypeOfSlab;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.UUID;
@Singleton
public class RecurringDepositServiceImpl implements RecurringDepositService {
    @Inject
    private AccountRepository accountRepository;
    @Inject
    private SlabRepository slabRepository;
    @Inject
    private RecurringDepositMapper recurringDepositMapper;
    @Inject
    private RecurringDepositRepository recurringDepositRepository;
    @Inject
    private RecurringDepositPaymentRepository recurringDepositPaymentRepository;
    @Inject
    private TransactionService transactionService;
    @Override
    public Mono<RecurringDepositOutputDto> saveRd(RecurringDepositInputDto recurringDepositInputDto) {
        return accountRepository.findById(recurringDepositInputDto.getAccountNumber())
                .switchIfEmpty(Mono.error(new AccountNotFoundException("no account with this id")))
                .filter(account -> account.getBalance() > recurringDepositInputDto.getMonthlyPaidAmount())
                .switchIfEmpty(Mono.error(new AmountNotSufficientException("your balance is lower than the rd amount")))
                .flatMap(account -> {
                    RecurringDeposit rd = recurringDepositMapper.mapToRecurringDeposit(recurringDepositInputDto);
                    return slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.RD)
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
                                        .map(recurringDeposit -> recurringDepositMapper.mapToRecurringDepositOutputDto(recurringDeposit));
                            });
                });
    }
    private Mono<RecurringDeposit> setPayment(RecurringDeposit recurringDeposit, PaymentStatus paymentStatus, int i) {

        return setTransaction(recurringDeposit,"DEBITED",recurringDeposit.getMonthlyPaidAmount())
                .flatMap(transaction -> {
                    RecurringDepositPayment rdPay = new RecurringDepositPayment();
                    rdPay.setPayAmount(recurringDeposit.getMonthlyPaidAmount());
                    rdPay.setRecurringDepositId(recurringDeposit.getId());
                    rdPay.setPaymentStatus(paymentStatus);
                    rdPay.setMonthNumber(i);
                    rdPay.setTransactionId(transaction.getTransactionID());
                    return Mono.just(rdPay);
                })
                .flatMap(rdPay -> recurringDepositPaymentRepository.save(rdPay))
                .thenReturn(recurringDeposit);

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
    private Mono<TransactionOutputDto> setTransaction(RecurringDeposit recurringDeposit, String status, Double monthlyPaidAmount) {

        TransactionInputDto inputDto = recurringDepositMapper.recurringDepositToTransactionInputDto(recurringDeposit);
        inputDto.setPurpose("Rd amount " + status);
        inputDto.setType("RD");
        inputDto.setTrans(status);
        inputDto.setAmount(monthlyPaidAmount);
        if(status.equals("DEBITED")) inputDto.setTo(null);
        return transactionService.deposit(inputDto, recurringDeposit.getAccountNumber());
    }

    @Override
    public Mono<RecurringDepositOutputDto> getById(UUID id) {
        return recurringDepositRepository.findById(id)
                .map(recurringDepositMapper::mapToRecurringDepositOutputDto);
    }

    @Override
    public Flux<RecurringDepositOutputDto> getAll() {
        return recurringDepositRepository.findAll()
                .map(recurringDepositMapper::mapToRecurringDepositOutputDto)
                .sort(Comparator.comparing(RecurringDepositOutputDto :: getStatus));
    }

    @Override
    public Flux<RecurringDepositOutputDto> getAllRecurringDeposit(Long accNo) {
        return recurringDepositRepository.findByAccount(accNo)
                .map(recurringDepositMapper::mapToRecurringDepositOutputDto);
    }
    @Override
    public Mono<Double> getTotalMoneyInvested(Long accNo) {
        return recurringDepositRepository.findByAccountAndStatus(accNo, "ACTIVE")
                .flatMap(rd -> {
                    return recurringDepositPaymentRepository.findByRecurringDepositId(rd.getId())
                            .map(rdPay -> {
                                    return rdPay.getPayAmount();
                            })
                            .reduce(0.0,Double::sum);
                })
                .reduce(0.0,Double::sum);
    }

    @Override
    public Flux<RecurringDepositOutputDto> getByStatus(Long accNo) {
        return recurringDepositRepository.findByAccountAndStatus(accNo,"ACTIVE")
                .map(recurringDepositMapper::mapToRecurringDepositOutputDto);
    }
}
