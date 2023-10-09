package com.tc.training.smallfinance.service.Impl;

import com.tc.training.smallfinance.dtos.inputs.RecurringDepositInputDto;
import com.tc.training.smallfinance.dtos.inputs.TransactionInputDto;
import com.tc.training.smallfinance.dtos.outputs.RecurringDepositOutputDto;
import com.tc.training.smallfinance.exception.AccountNotFoundException;
import com.tc.training.smallfinance.exception.AmountNotSufficientException;
import com.tc.training.smallfinance.mapper.RecurringDepositMapper;
import com.tc.training.smallfinance.model.*;
import com.tc.training.smallfinance.repository.AccountRepository;
import com.tc.training.smallfinance.repository.RdPaymentRepository;
import com.tc.training.smallfinance.repository.RecurringDepositRepository;
import com.tc.training.smallfinance.repository.SlabRepository;
import com.tc.training.smallfinance.service.AccountServiceDetails;
import com.tc.training.smallfinance.service.EmailService;
import com.tc.training.smallfinance.service.RecurringDepositService;
import com.tc.training.smallfinance.service.TransactionService;
import com.tc.training.smallfinance.utils.PaymentStatus;
import com.tc.training.smallfinance.utils.RdStatus;
import com.tc.training.smallfinance.utils.Tenures;
import com.tc.training.smallfinance.utils.TypeOfSlab;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class RecurringDepositServiceImpl implements RecurringDepositService {
//    @Autowired
//    private ModelMapper modelMapper;
    @Autowired
    private RecurringDepositRepository recurringDepositRepository;
    @Autowired
    private RdPaymentRepository rdPaymentRepository;
    @Autowired
    private SlabRepository slabRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountServiceDetails accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private RecurringDepositMapper recurringDepositMapper;


    @Override
    @Transactional
    public RecurringDepositOutputDto saveRd(RecurringDepositInputDto recurringDepositInputDto) {
//        RecurringDeposit rd = modelMapper.map(recurringDepositInputDto, RecurringDeposit.class);
        RecurringDeposit rd = recurringDepositMapper.mapToRecurringDeposit(recurringDepositInputDto);
        rd.setAccountNumber(accountRepository.findById(recurringDepositInputDto.getAccountNumber()).orElseThrow(() -> new AccountNotFoundException("account with this id not found")));
        Slabs slab = slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.RD);
        if(rd.getAccountNumber().getBalance() < recurringDepositInputDto.getMonthlyPaidAmount()) throw new AmountNotSufficientException("no sufficient funds to open the account");
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
        rdPaymentRepository.save(rdPay);
        RecurringDepositOutputDto rdout = recurringDepositMapper.mapToRecurringDepositOutputDto(rd);
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
    public RecurringDepositOutputDto getById(UUID id) {
        RecurringDeposit rd = recurringDepositRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("rd account not found"));
        RecurringDepositOutputDto rdout = recurringDepositMapper.mapToRecurringDepositOutputDto(rd);
        rdout.setAccount(String.valueOf(rd.getAccountNumber().getAccountNumber()));
        return rdout;
    }

    @Override
    public List<RecurringDepositOutputDto> getAll() {

        List<RecurringDeposit> rds = recurringDepositRepository.findAll();
        List<RecurringDepositOutputDto> rdouts = new ArrayList<>();
        for (RecurringDeposit r : rds) {
            RecurringDepositOutputDto rd = recurringDepositMapper.mapToRecurringDepositOutputDto(r);
            rd.setAccount(String.valueOf(r.getAccountNumber().getAccountNumber()));
            rdouts.add(rd);
        }
        Collections.sort(rdouts, new Comparator<RecurringDepositOutputDto>() {

            @Override
            public int compare(RecurringDepositOutputDto o1, RecurringDepositOutputDto o2) {
                if (o1.getStatus().equals(RdStatus.ACTIVE) && o2.getStatus().equals(RdStatus.ACTIVE)) return 0;
                else if (o1.getStatus().equals(RdStatus.ACTIVE) && o2.getStatus().equals(RdStatus.CLOSED)) return 1;
                else if (o1.getStatus().equals(RdStatus.ACTIVE) && o2.getStatus().equals(RdStatus.MATURED)) return 1;
                else return -1;
            }
        });
        return rdouts;
    }


    @Override
    public List<RecurringDepositOutputDto> getAllRecurringDeposit(Long accNo) {
        List<RecurringDeposit> rdList = recurringDepositRepository.findByAccount(accNo);
        List<RecurringDepositOutputDto> rdoutList = new ArrayList<>();
        for (RecurringDeposit rd : rdList) {
            RecurringDepositOutputDto rdout = recurringDepositMapper.mapToRecurringDepositOutputDto(rd);
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
            List<RecurringDepositPayment> rpay = rdPaymentRepository.findAllByRecurringDeposit(r);
            for (RecurringDepositPayment recurringDepositPayment : rpay) {
                if (recurringDepositPayment.getPaymentStatus().equals(PaymentStatus.PAID))
                    sum += recurringDepositPayment.getPayAmount();
            }
        }
        return sum;
    }

    @Override
    public List<RecurringDepositOutputDto> getByStatus(Long accNo) {
        List<RecurringDeposit> rdList = recurringDepositRepository.findByAccountAndStatus(accNo, RdStatus.ACTIVE.name());
        List<RecurringDepositOutputDto> rdoutList = new ArrayList<>();
        for (RecurringDeposit rd : rdList) {
            RecurringDepositOutputDto rdout = recurringDepositMapper.mapToRecurringDepositOutputDto(rd);
            rdout.setAccount(String.valueOf(rd.getAccountNumber().getAccountNumber()));
            rdoutList.add(rdout);
        }
        return rdoutList;
    }

    private UUID setTransaction(RecurringDeposit rd, String status, Double amount) {

        TransactionInputDto transaction = new TransactionInputDto();
        if (status.equals("CREDIT")) {
            //transaction.setFrom(loan.getAccount());
            transaction.setType("RD");
            // transaction.setAmount(loan.getLoanedAmount());
            transaction.setAmount(amount);
            transaction.setAccountNumber(Long.valueOf(String.valueOf(rd.getAccountNumber().getAccountNumber())));
            transaction.setTo(Long.valueOf(String.valueOf(rd.getAccountNumber().getAccountNumber())));
            transaction.setPurpose("RD amount credited");
        } else {
            //transaction.setFrom(loan.getAccount());
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


    @Scheduled(cron = "0 0 0 * * *")
    public void scheduler() {

        List<RecurringDeposit> rdList = recurringDepositRepository.findByIsActive();

        for (RecurringDeposit rd : rdList) {

            Period period = Period.between(rd.getStartDate(), LocalDate.now());
            Integer month = period.getYears() * 12 + period.getMonths();

            if (!rd.getNextPaymentDate().equals(LocalDate.now())) continue;

            if (accountService.getBalance(rd.getAccountNumber().getAccountNumber()) > rd.getMonthlyPaidAmount()) {
                RecurringDepositPayment rdPay = new RecurringDepositPayment();
                rdPay.setRecurringDeposit(rd);
                rdPay.setTransactionId(setTransaction(rd, "DEBIT", rd.getMonthlyPaidAmount()));
                rdPay.setPaymentStatus(PaymentStatus.PAID);
                rdPay.setPayAmount(rd.getMonthlyPaidAmount());
                rdPay.setMonthNumber(month + 1);
                rd.setNextPaymentDate(rd.getStartDate().plusMonths(month + 1));
                rdPaymentRepository.save(rdPay);
            } else {
                if (rd.getMissedPayments() < 4) {
                    rd.setNextPaymentDate(rd.getNextPaymentDate().plusDays(3));
                    rd.setMissedPayments(rd.getMissedPayments()+1);
                } else {
                    RecurringDepositPayment rdPay = new RecurringDepositPayment();
                    rdPay.setRecurringDeposit(rd);
                    rdPay.setPaymentStatus(PaymentStatus.UNPAID);
                    rdPay.setPayAmount(rd.getMonthlyPaidAmount());
                    rdPay.setMonthNumber(month + 1);
                    rd.setNextPaymentDate(rd.getStartDate().plusMonths(month + 1));
                    rd.setTotalMissedPaymentCount(rd.getTotalMissedPaymentCount() + 1);
                    rdPaymentRepository.save(rdPay);
                }

            }
            if (rd.getMaturityDate().equals(LocalDate.now())) closeAccount(rd, "matured");
            List<RecurringDepositPayment> payList = rdPaymentRepository.findAllByRecurringDeposit(rd);

            if (rd.getTotalMissedPaymentCount() > 3) {
                closeAccount(rd, "closed");
                Double total = 0D;
                for (RecurringDepositPayment rpay : payList) {
                    total += rpay.getPayAmount();
                }
                /*total = total * (1 + (Double.valueOf(rd.getInterest()) / 4) * rd.getRdPayments().size());
                total -= total * 0.2;*/
                total = calculateMaturityAmount(rd,payList.size()+1);
                setTransaction(rd, "CREDIT", total);
            }

            recurringDepositRepository.save(rd);
        }


    }

    private void closeAccount(RecurringDeposit rd, String status) {

        if (status.equals("closed")) rd.setStatus(RdStatus.CLOSED);
        else rd.setStatus(RdStatus.MATURED);

        recurringDepositRepository.save(rd);
    }

    private ResponseEntity sendEmail(RecurringDeposit rd) {
        String to = rd.getAccountNumber().getUser().getEmail();
        String subject = "Repayment of RD";
        String body = "As your balance is lesser than the emi " + rd.getMonthlyPaidAmount() + " please add money to your account within 3 days";
        try {
            emailService.sendEmail(to, subject, body);
        } catch (MailException e) {
            ResponseEntity.badRequest();
        }
        return new ResponseEntity(HttpStatusCode.valueOf(200));
    }
}
