package com.tc.training.smallfinance.service.Impl;

import com.tc.training.smallfinance.dtos.inputs.LoanInputDto;
import com.tc.training.smallfinance.dtos.inputs.TransactionInputDto;
import com.tc.training.smallfinance.dtos.outputs.LoanOutputDto;
import com.tc.training.smallfinance.exception.AccountNotFoundException;
import com.tc.training.smallfinance.exception.MyMailException;
import com.tc.training.smallfinance.mapper.LoanMapper;
import com.tc.training.smallfinance.model.*;
import com.tc.training.smallfinance.repository.*;
import com.tc.training.smallfinance.service.*;
import com.tc.training.smallfinance.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RePaymentRepository paymentRepository;
    @Autowired
    private SlabRepository slabRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountServiceDetails accountService;
    @Autowired
    private LoanMapper loanMapper;


    Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Override
    public LoanOutputDto addLoan(LoanInputDto loanInputDto) {
        Loan loan = new Loan();
        loan.setLoanedAmount(loanInputDto.getLoanAmount());
        loan.setAccountNumber(accountRepository.findById(Long.valueOf(loanInputDto.getAccountNumber())).orElseThrow(()->new AccountNotFoundException("account not found")));
        loan.setAppliedDate(LocalDate.now());
        loan.setLoanEndDate(loan.getAppliedDate().plusYears(Integer.parseInt(loanInputDto.getTenure())));
        if(loanInputDto.getType().equals("GOLD_LOAN")){
            loan.setTypeOfLoan(TypeOfLoans.GOLD_LOAN);
            loan.setSlab(slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.GOLD_LOAN));
        }
        else if(loanInputDto.getType().equals("PERSONAL_LOAN")){
            loan.setTypeOfLoan(TypeOfLoans.PERSONAL_LOAN);
            loan.setSlab(slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.PERSONAL_LOAN));
        }
        else if(loanInputDto.getType().equals("EDUCATION_LOAN")){
            loan.setTypeOfLoan(TypeOfLoans.EDUCATION_LOAN);
            loan.setSlab(slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.EDUCATION_LOAN));
        }
        else if(loanInputDto.getType().equals("HOME_LOAN")) {
            loan.setTypeOfLoan(TypeOfLoans.HOME_LOAN);;
            loan.setSlab(slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.HOME_LOAN));
        }
        loan.setInterest(loan.getSlab().getInterestRate());
        loanRepository.save(loan);
        return loanMapper.mapToLoanOutputDto(loan);
    }


    @Override
    public LoanOutputDto getById(UUID id) {
        Loan loan = loanRepository.findById(id).orElseThrow(()->new AccountNotFoundException("loan account not found"));
        LoanOutputDto lout = loanMapper.mapToLoanOutputDto(loan);
        lout.setTenure(loan.getSlab().getTenures().toString());
        lout.setDob(loan.getAccountNumber().getUser().getDob());
        lout.setAge(loan.getAccountNumber().getUser().getAge());
        lout.setEmail(loan.getAccountNumber().getUser().getEmail());
        lout.setFirstName(loan.getAccountNumber().getUser().getFirstName());
        lout.setLastName(loan.getAccountNumber().getUser().getLastName());
        lout.setAccountNumber(String.valueOf(loan.getAccountNumber().getAccountNumber()));
        lout.setTenure(loan.getSlab().getTenures().toString());
        return lout;
    }

    @Override
    public List<LoanOutputDto> getAllByUser(Long accNo) {
        List<Loan> list;
        if(accNo != null) list = loanRepository.findAllByAccountNumber(accNo);
        else  list = loanRepository.findAll();
        List<LoanOutputDto> newList = list.stream().map(loan ->loanMapper.mapToLoanOutputDto(loan)).collect(Collectors.toList());
        Collections.sort(newList, Collections.reverseOrder(Comparator.comparing(LoanOutputDto::getIsActive)));
        return newList;
    }

    @Override
    public List<LoanOutputDto> getAll() {
        List<LoanOutputDto> newList =  loanRepository.findAll().stream().map(loan->loanMapper.mapToLoanOutputDto(loan)).collect(Collectors.toList());
        Collections.sort(newList, Collections.reverseOrder(Comparator.comparing(LoanOutputDto::getIsActive)));
        return newList;
    }

    @Override
    public LoanOutputDto setLoan(UUID id,String status) {

        Loan loan = loanRepository.findById(id).orElseThrow(()->new AccountNotFoundException("account not found"));
        Period period = Period.between(loan.getAppliedDate(),loan.getLoanEndDate());
        int months = period.getYears() * 12 + period.getMonths();
        if(status.equals("APPROVE") && loan.getStatus()==Status.UNDER_REVIEW) {
            loan.setStatus(Status.APPROVED);
            Double totalAmountToPay = (loan.getLoanedAmount()*(Double.valueOf(loan.getInterest())/100))*(months/12) + loan.getLoanedAmount();
            loan.setRemainingAmount(totalAmountToPay);
            loan.setStartDate(LocalDate.now());
            loan.setNextPaymentDate(loan.getStartDate().plusMonths(1));
            loan.setMonthlyInterestAmount((int) Math.round(monthlyPayAmount(loan.getRemainingAmount(),months)));
            setTransaction(loan,"CREDIT",loan.getLoanedAmount());
        }
        else if(status.equals("REJECT") && loan.getStatus()==Status.UNDER_REVIEW) {
            loan.setStatus(Status.REJECTED);
            loan = closeLoan(loan);
        }
        loanRepository.save(loan);
        return loanMapper.mapToLoanOutputDto(loan);
    }


    @Override
    public List<LoanOutputDto> getBytype(Long accNo, String type) {

        List<Loan> list = loanRepository.findAllByAccountNumber(accNo);
        List<LoanOutputDto> typeLoanList = new ArrayList<>();
        TypeOfLoans loanType = TypeOfLoans.valueOf(type);
        for(Loan l : list){
            if(l.getTypeOfLoan().equals(loanType)) typeLoanList.add(loanMapper.mapToLoanOutputDto(l));
        }
        return typeLoanList;
    }

    @Override
    public Double getTotalLoanAmount(Long accNo) {
        List<Loan> loan = loanRepository.findAllByAccountNumberAndIsActive(accNo);
        Double amount = 0D;
        for(Loan l : loan){
            amount += l.getLoanedAmount();
        }
        return amount;
    }

    @Override
    public List<LoanOutputDto> getAllPending() {
        List<Loan> loans = loanRepository.findAllPending();
        return loans.stream().map(loan -> loanMapper.mapToLoanOutputDto(loan)).collect(Collectors.toList());
    }

    @Override
    public List<LoanOutputDto> getAllByNotPending() {
        List<Loan> loans = loanRepository.findAllNotPending();
        return loans.stream().map(loan -> loanMapper.mapToLoanOutputDto(loan)).collect(Collectors.toList());
    }



    @Override
    public List<LoanOutputDto> getAllByStatus(String s) {
        Status status = Status.valueOf(s);
        List<Loan> loans = loanRepository.findByStatus(status);
        List<LoanOutputDto> loanOutputDtos = new ArrayList<>();
        for(Loan loan : loans){
            LoanOutputDto lout = loanMapper.mapToLoanOutputDto(loan);
            lout.setAccountNumber(String.valueOf(loan.getAccountNumber().getAccountNumber()));
            lout.setTenure(loan.getSlab().getTenures().toString());
            loanOutputDtos.add(lout);
        }
        return loanOutputDtos;
    }

    private Loan closeLoan(Loan loan) {

        loan.setIsActive(Boolean.FALSE);
        loan.setLoanEndDate(LocalDate.now());
        if(loan.getStatus().equals(Status.REJECTED)) {
            loan.setInterestAmount(0D);
            loan.setTotalAmount(0D);
        }
        else {
           // loan.setInterestAmount(loan.getLoanedAmount()*Double.valueOf(loan.getInterest())/(loan.getRepayments().size()/12) - loan.getLoanedAmount());
            loan.setTotalAmount(loan.getLoanedAmount() + loan.getInterestAmount());
        }

        loan.setMonthlyInterestAmount(0);
        return loan;
    }

    private Double monthlyPayAmount(Double remainingAmount,Integer noOfMonths){
        /*Double si  = remainingAmount * (interest/100) * (noOfMonths/12);
        Double monthlyRepayment = (remainingAmount + si) / noOfMonths;*/
        Double monthlyRepayment = remainingAmount/noOfMonths;
//        System.out.println(monthlyRepayment);
        return monthlyRepayment;
    }

    private UUID setTransaction(Loan loan,String status,Double amount){

        TransactionInputDto transaction = new TransactionInputDto();
        if(status.equals("CREDIT")) {
            //transaction.setFrom(loan.getAccount());
            transaction.setType(loan.getTypeOfLoan().toString());
           // transaction.setAmount(loan.getLoanedAmount());
            transaction.setAmount(amount);
            transaction.setAccountNumber(Long.valueOf(String.valueOf(loan.getAccountNumber().getAccountNumber())));
            transaction.setTo(Long.valueOf(String.valueOf(loan.getAccountNumber().getAccountNumber())));
            transaction.setPurpose("Loan amount credited");
        }
        else{
            //transaction.setFrom(loan.getAccount());
            transaction.setType(loan.getTypeOfLoan().toString());
            transaction.setAmount(amount);
            transaction.setAccountNumber(Long.valueOf(String.valueOf(loan.getAccountNumber().getAccountNumber())));
           // transaction.setTo(String.valueOf(loan.getAccount().getAccountNumber()));
            transaction.setPurpose("Loan interest debited");
        }
        transaction.setTrans(status);
        return transactionService.deposit(transaction,loan.getAccountNumber().getAccountNumber()).getTransactionID();
    }


//    @Scheduled(cron = "0 0 0 * * *")
//    @Async
//    private void diffSchedule() {
//        logger.info("entered diff schedule");
//        List<Loan> loans = loanRepository.findByIsActiveAndAccepted();
//        for (Loan loan : loans) {
//            if(!loan.getNextPaymentDate().equals(LocalDate.now())) continue;
//            Period period = Period.between(loan.getStartDate(), LocalDate.now());
//            Period noOfMonths = Period.between(loan.getStartDate(), loan.getLoanEndDate());
//            Integer totalNoOfMonthsLeft = noOfMonths.getYears() * 12 + noOfMonths.getMonths();
//            Integer month = period.getYears() * 12 + period.getMonths();
//            Integer count = 0;
//            Double monthlyPay = Double.valueOf(loan.getMonthlyInterestAmount());   //monthlyPayAmount(loan.getRemainingAmount(), totalNoOfMonthsLeft - month - 1);
//            loan.setMonthlyInterestAmount((int) Math.round(monthlyPay));
//          //  if(loan.getRepayments() == null) loan.setRepayments(new ArrayList<>());
//
//            if (accountService.getBalance(loan.getAccountNumber().getAccountNumber()) > monthlyPay) {
//                Repayment repayment = new Repayment();
//                repayment.setPayAmount(monthlyPay);
//                repayment.setLoan(loan);
//                repayment.setMonthNumber(month);
//                repayment.setTransactionId(setTransaction(loan, "DEBIT", monthlyPay));
//                repayment.setPaymentStatus(PaymentStatus.PAID);
//                paymentRepository.save(repayment);
//                loan.setRemainingAmount(loan.getRemainingAmount() - loan.getMonthlyInterestAmount());
//                loan.setNextPaymentDate(loan.getStartDate().plusMonths(month+1));
//                loan.setMonthlyInterestAmount((int) Math.round(monthlyPayAmount(loan.getRemainingAmount(), totalNoOfMonthsLeft - month - 1)));
//            } else {
//                try {
////                    ResponseEntity rs = sendEmail(loan);
//                }catch(MyMailException e) {}
//                if(period.getDays()!=0 && loan.getMissedPaymentCount()<3){
//                    loan.setMonthlyInterestAmount((int) Math.round(loan.getMonthlyInterestAmount() * 0.1));
//                    loan.setMissedPaymentCount(loan.getMissedPaymentCount()+1);
//                    loan.setNextPaymentDate(loan.getNextPaymentDate().plusDays(3));
//                }
//                else if(period.getDays()==0 && loan.getMissedPaymentCount()<3){
//                    loan.setNextPaymentDate(loan.getNextPaymentDate().plusDays(3));
//                    loan.setMissedPaymentCount(loan.getMissedPaymentCount()+1);
//                }
//                else if(loan.getMissedPaymentCount()>3){
//                    Repayment repayment = new Repayment();
//                    repayment.setPayAmount(Double.valueOf(loan.getMonthlyInterestAmount()));
//                    repayment.setLoan(loan);
//                    repayment.setMonthNumber(month);
//                    repayment.setPaymentStatus(PaymentStatus.UNPAID);
//                    repayment.setPenalty(Boolean.TRUE);
//                    paymentRepository.save(repayment);
//                    loan.setNextPaymentDate(loan.getStartDate().plusMonths(month+1));
//                    loan.setMissedPaymentCount(0);
//                    Double pay = monthlyPayAmount(loan.getRemainingAmount(),totalNoOfMonthsLeft-month);
//                    loan.setRemainingAmount(loan.getRemainingAmount() + loan.getMonthlyInterestAmount() - pay);
//                    loan.setMonthlyInterestAmount((int) Math.round(monthlyPayAmount(loan.getRemainingAmount(),totalNoOfMonthsLeft - month - 1)));
//                    loan.setTotalMissedPayments(loan.getTotalMissedPayments()+1);
//                }
//
//            }
//            if(loan.getRemainingAmount().equals(Double.valueOf(0D))) closeLoan(loan);
//
//            loanRepository.save(loan);
//        }
//    }
}
