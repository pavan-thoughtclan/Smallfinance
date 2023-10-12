package com.smallfinance.services.impl;

import com.smallfinance.dtos.inputs.LoanInput;
import com.smallfinance.dtos.inputs.TransactionInput;
import com.smallfinance.dtos.outputs.LoanOutput;
import com.smallfinance.entities.Loan;
import com.smallfinance.entities.Slabs;
import com.smallfinance.enums.Status;
import com.smallfinance.enums.Tenures;
import com.smallfinance.enums.TypeOfLoans;
import com.smallfinance.enums.TypeOfSlab;
import com.smallfinance.mapper.LoanMapper;
import com.smallfinance.repositories.AccountDetailsRepository;
import com.smallfinance.repositories.LoanRepository;
import com.smallfinance.repositories.SlabRepository;
import com.smallfinance.services.LoanService;
import com.smallfinance.services.TransactionService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class LoanServiceImpl implements LoanService {
    @Inject
    private final AccountDetailsRepository accountDetailsRepository;
    @Inject
    private final SlabRepository slabRepository;
    @Inject
    private final LoanRepository loanRepository;
    @Inject
    private final LoanMapper loanMapper;
    @Inject
    private final TransactionService transactionService;

    public LoanServiceImpl(AccountDetailsRepository accountDetailsRepository, LoanRepository loanRepository, SlabRepository slabRepository, LoanMapper loanMapper, TransactionService transactionService) {
        this.accountDetailsRepository = accountDetailsRepository;
        this.slabRepository = slabRepository;
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.transactionService = transactionService;
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
    public LoanOutput addLoan(LoanInput input) {
        Slabs slabs = slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.valueOf(input.getType()));
        Loan loan = new Loan();
        loan.setStatus(Status.UNDER_REVIEW);
        loan.setIsActive(Boolean.TRUE);
        loan.setLoanedAmount(input.getLoanAmount());
        loan.setAccountNumber(accountDetailsRepository.findById(Long.valueOf(input.getAccountNumber())).orElseThrow(()->new RuntimeException("account not found")));
        loan.setAppliedDate(LocalDate.now());
        loan.setLoanEndDate(loan.getAppliedDate().plusYears(Integer.parseInt(input.getTenure())));

        if(input.getType().equals("GOLD_LOAN")){

            loan.setTypeOfLoan(TypeOfLoans.GOLD_LOAN);
            loan.setSlab(slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.GOLD_LOAN));

        }
        else if(input.getType().equals("PERSONAL_LOAN")){
            loan.setTypeOfLoan(TypeOfLoans.PERSONAL_LOAN);
            loan.setSlab(slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.PERSONAL_LOAN));

        }
        else if(input.getType().equals("EDUCATION_LOAN")){
            loan.setTypeOfLoan(TypeOfLoans.EDUCATION_LOAN);
            loan.setSlab(slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.EDUCATION_LOAN));

        }
        else if(input.getType().equals("HOME_LOAN")) {
            loan.setTypeOfLoan(TypeOfLoans.HOME_LOAN);;
            loan.setSlab(slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.HOME_LOAN));
        }
        loan.setInterest(loan.getSlab().getInterestRate());
        loanRepository.save(loan);
//        return modelMapper.map(loan,LoanOutputDto.class);
        return loanMapper.mapToLoanOutputDto(loan);
    }

    @Override
    public List<LoanOutput> getAllByUser(Long accNo) {
        List<Loan> list;
        if(accNo != null) list = loanRepository.findAllByAccountNumber(accNo);

        else  list = loanRepository.findAll();
        List<LoanOutput> newList = list.stream()
                .map(loanMapper::mapToLoanOutputDto)
                .sorted(Comparator.comparing(loanOutput -> {
                    Boolean isActive = loanOutput.getIsActive();
                    return isActive != null ? isActive : Boolean.FALSE; // Treat null as FALSE
                }, Collections.reverseOrder()))
                .collect(Collectors.toList());
        return newList;
    }

    @Override
    public List<LoanOutput> getAll() {
        List<LoanOutput> newList =  loanRepository.findAll().stream().map(loan->loanMapper.mapToLoanOutputDto(loan)).collect(Collectors.toList());
        Collections.sort(newList, Collections.reverseOrder(Comparator.comparing(LoanOutput::getIsActive)));
        return newList;
    }

    @Override
    public LoanOutput setLoan(UUID id, String status) {
        Loan loan = loanRepository.findById(id).orElseThrow(()->new RuntimeException("account not found"));
        Period period = Period.between(loan.getAppliedDate(),loan.getLoanEndDate());
        int months = period.getYears() * 12 + period.getMonths();
        if(status.equals("APPROVED") && loan.getStatus()==Status.UNDER_REVIEW) {
            loan.setStatus(Status.APPROVED);
            Double totalAmountToPay = (loan.getLoanedAmount()*(Double.parseDouble(loan.getInterest())/100))*(months/12) + loan.getLoanedAmount();
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
        loanRepository.update(loan);
        return loanMapper.mapToLoanOutputDto(loan);
    }
    private Double monthlyPayAmount(Double remainingAmount,Integer noOfMonths){
        /*Double si  = remainingAmount * (interest/100) * (noOfMonths/12);
        Double monthlyRepayment = (remainingAmount + si) / noOfMonths;*/
        Double monthlyRepayment = remainingAmount/noOfMonths;
//        System.out.println(monthlyRepayment);
        return monthlyRepayment;
    }
    private UUID setTransaction(Loan loan,String status,Double amount){
//
        TransactionInput transaction = new TransactionInput();
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

    @Override
    public List<LoanOutput> getByType(Long accNo, String type) {
        List<Loan> list = loanRepository.findAllByAccountNumber(accNo);
        List<LoanOutput> typeLoanList = new ArrayList<>();
        TypeOfLoans loanType = TypeOfLoans.valueOf(type);
        for(Loan l : list){

            if(l.getTypeOfLoan().equals(loanType)) typeLoanList.add(loanMapper.mapToLoanOutputDto(l));

        }
        return typeLoanList;
    }

    @Override
    public List<LoanOutput> getAllPending() {
        List<Loan> loans = loanRepository.findAllPending();
        return loans.stream().map(loanMapper::mapToLoanOutputDto).collect(Collectors.toList());
       }

    @Override
    public List<LoanOutput> getAllByNotPending() {
        List<Loan> loans = loanRepository.findAllNotPending();
        return loans.stream().map(loanMapper::mapToLoanOutputDto).collect(Collectors.toList());
       }

    @Override
    public List<LoanOutput> getAllByStatus(String s) {
        Status status = Status.valueOf(s);
        List<Loan> loans = loanRepository.findByStatus(status);
        List<LoanOutput> loanOutputDtos = new ArrayList<>();
        for(Loan loan : loans){
            LoanOutput lout = loanMapper.mapToLoanOutputDto(loan);
            lout.setAccountNumber(String.valueOf(loan.getAccountNumber().getAccountNumber()));
            lout.setTenure(loan.getSlab().getTenures().toString());
            loanOutputDtos.add(lout);
        }
        return loanOutputDtos;
    }

    @Override
    public LoanOutput getById(UUID id) {
        Loan loan = loanRepository.findById(id).orElseThrow(()->new RuntimeException("loan account not found"));
        LoanOutput lout = loanMapper.mapToLoanOutputDto(loan);
        lout.setTenure(loan.getSlab().getTenures().toString());
        lout.setDob(loan.getAccountNumber().getUser().getDob());
        lout.setAge(loan.getAccountNumber().getUser().getAge());
        lout.setEmail(loan.getAccountNumber().getUser().getEmail());
        lout.setFirstName(loan.getAccountNumber().getUser().getFirstName());
        lout.setLastName(loan.getAccountNumber().getUser().getLastName());
        lout.setAccountNumber(String.valueOf(loan.getAccountNumber().getAccountNumber()));
        lout.setTenure(loan.getSlab().getTenures().toString());
        return lout;    }

}
