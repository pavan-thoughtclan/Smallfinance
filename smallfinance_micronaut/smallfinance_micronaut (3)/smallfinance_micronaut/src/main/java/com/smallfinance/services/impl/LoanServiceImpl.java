package com.smallfinance.services.impl;

import com.smallfinance.dtos.inputs.LoanInput;
import com.smallfinance.dtos.inputs.TransactionInput;
import com.smallfinance.dtos.outputs.LoanOutput;
import com.smallfinance.entities.AccountDetails;
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

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service implementation for managing loans operations.
 */
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

    /**
     * Constructs a new LoanServiceImpl with the required dependencies.
     *
     * @param accountDetailsRepository Repository for account details.
     * @param loanRepository           Repository for loans.
     * @param slabRepository           Repository for slabs.
     * @param loanMapper               Mapper for converting loan entities to DTOs.
     * @param transactionService       Service for managing transactions.
     */
    public LoanServiceImpl(AccountDetailsRepository accountDetailsRepository, LoanRepository loanRepository, SlabRepository slabRepository, LoanMapper loanMapper, TransactionService transactionService) {
        this.accountDetailsRepository = accountDetailsRepository;
        this.slabRepository = slabRepository;
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.transactionService = transactionService;
    }

    /**
     * Calculate and return the total loan amount for a given account.
     *
     * @param accNo The account number for which to calculate the total loan amount.
     * @return The total loan amount.
     */
    @Override
    public Double getTotalLoanAmount(Long accNo) {
        List<Loan> loan = loanRepository.findAllByAccountNumberAndIsActive(accNo);
        Double amount = 0D;
        for(Loan l : loan){
            amount += l.getLoanedAmount();
        }
        return amount;

    }

    /**
     * Add a new loan based on the provided input.
     *
     * @param input The LoanInput data for creating a loan.
     * @return LoanOutput DTO.
     */
    @Override
    @Transactional
    public LoanOutput addLoan(LoanInput input) {
        Slabs slab = slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.valueOf(input.getType()));
        Loan loan = new Loan();

        loan.setStatus(Status.UNDER_REVIEW);
        loan.setIsActive(Boolean.TRUE);
        loan.setLoanedAmount(input.getLoanAmount());

        AccountDetails accountDetails = accountDetailsRepository.findById(Long.valueOf(input.getAccountNumber()))
                .orElseThrow(() -> new RuntimeException("Account not found"));
        loan.setAccountNumber(accountDetails);

        loan.setAppliedDate(LocalDate.now());
        loan.setLoanEndDate(loan.getAppliedDate().plusYears(Integer.parseInt(input.getTenure())));

        TypeOfLoans typeOfLoan = TypeOfLoans.valueOf(input.getType());
        loan.setTypeOfLoan(typeOfLoan);
        loan.setSlab(slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.valueOf(typeOfLoan.name())));

        loan.setInterest(loan.getSlab().getInterestRate());
        loanRepository.save(loan);

        return loanMapper.mapToLoanOutputDto(loan);
    }






//    public LoanOutput addLoan(LoanInput input) {
//        Slabs slabs = slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.valueOf(input.getType()));
//        Loan loan = new Loan();
//        loan.setStatus(Status.UNDER_REVIEW);
//        loan.setIsActive(Boolean.TRUE);
//        loan.setLoanedAmount(input.getLoanAmount());
//        loan.setAccountNumber(accountDetailsRepository.findById(Long.valueOf(input.getAccountNumber())).orElseThrow(()->new RuntimeException("account not found")));
//        loan.setAppliedDate(LocalDate.now());
//        loan.setLoanEndDate(loan.getAppliedDate().plusYears(Integer.parseInt(input.getTenure())));
//
//        if(input.getType().equals("GOLD_LOAN")){
//
//            loan.setTypeOfLoan(TypeOfLoans.GOLD_LOAN);
//            loan.setSlab(slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.GOLD_LOAN));
//
//        }
//        else if(input.getType().equals("PERSONAL_LOAN")){
//            loan.setTypeOfLoan(TypeOfLoans.PERSONAL_LOAN);
//            loan.setSlab(slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.PERSONAL_LOAN));
//
//        }
//        else if(input.getType().equals("EDUCATION_LOAN")){
//            loan.setTypeOfLoan(TypeOfLoans.EDUCATION_LOAN);
//            loan.setSlab(slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.EDUCATION_LOAN));
//
//        }
//        else if(input.getType().equals("HOME_LOAN")) {
//            loan.setTypeOfLoan(TypeOfLoans.HOME_LOAN);;
//            loan.setSlab(slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.HOME_LOAN));
//        }
//        loan.setInterest(loan.getSlab().getInterestRate());
//        loanRepository.save(loan);
////        return modelMapper.map(loan,LoanOutputDto.class);
//        return loanMapper.mapToLoanOutputDto(loan);
//    }
    /**
     * Get all loans, including pending and non-pending loans.
     *
     * @param accNo The account number for which to retrieve loans, or null to retrieve all loans.
     * @return A list of loans, sorted with pending loans first.
     */

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

    /**
     * Get all loans.
     *
     * @return A list of all loans, sorted by activity status.
     */
    @Override
    public List<LoanOutput> getAll() {
        List<LoanOutput> newList =  loanRepository.findAll().stream().map(loan->loanMapper.mapToLoanOutputDto(loan)).collect(Collectors.toList());
        Collections.sort(newList, Collections.reverseOrder(Comparator.comparing(LoanOutput::getIsActive)));
        return newList;
    }

    /**
     * Set the status of a loan based on its ID.
     *
     * @param id     The ID of the loan.
     * @param status The new status to set (APPROVED or REJECTED).
     * @return The updated loan information.
     */
    @Override
    @Transactional
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


    /**
     * Get all loans for a specific account number and loan type.
     *
     * @param accNo The account number for which to retrieve loans.
     * @param type  The type of loan (e.g., GOLD_LOAN, PERSONAL_LOAN).
     * @return A list of loans matching the specified account and loan type.
     */
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

    /**
     * Get all loans in pending status.
     *
     * @return A list of loans with pending status.
     */
    @Override
    public List<LoanOutput> getAllPending() {
        List<Loan> loans = loanRepository.findAllPending();
        return loans.stream().map(loanMapper::mapToLoanOutputDto).collect(Collectors.toList());
       }

    /**
     * Get all loans that are not in pending status.
     *
     * @return A list of loans that are not in pending status.
     */
    @Override
    public List<LoanOutput> getAllByNotPending() {
        List<Loan> loans = loanRepository.findAllNotPending();
        return loans.stream().map(loanMapper::mapToLoanOutputDto).collect(Collectors.toList());
       }

    /**
     * Get all loans by their status.
     *
     * @param s The status of loans (e.g., UNDER_REVIEW, APPROVED).
     * @return A list of loans matching the specified status.
     */
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

    /**
     * Get loan details by ID.
     *
     * @param id The unique ID of the loan.
     * @return The loan details.
     */
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
