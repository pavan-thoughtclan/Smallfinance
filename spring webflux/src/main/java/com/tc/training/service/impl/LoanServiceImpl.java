package com.tc.training.service.impl;

import com.tc.training.dtos.inputdto.LoanInputDto;
import com.tc.training.dtos.inputdto.TransactionInputDto;
import com.tc.training.dtos.outputdto.LoanOutputDto;
import com.tc.training.dtos.outputdto.TransactionOutputDto;
import com.tc.training.exception.AccountNotFoundException;
import com.tc.training.exception.CustomException;
import com.tc.training.mapper.LoanMapper;
import com.tc.training.mapper.TransactionMapper;
import com.tc.training.model.Loan;
import com.tc.training.model.Repayment;
import com.tc.training.repo.*;
import com.tc.training.service.AccountDetailsService;
import com.tc.training.service.LoanService;
import com.tc.training.service.TransactionService;
import com.tc.training.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.UUID;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanMapper loanMapper;
    @Autowired
    private SlabRepository slabRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountDetailsRepository accountDetailsRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private RepaymentRepository repaymentRepository;
    @Autowired
    private AccountDetailsService accountDetailsService;

    Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Override
    public Mono<LoanOutputDto> addLoan(LoanInputDto loanInputDto) {

        return slabRepository.findByTenuresAndTypeOfSlab(Tenures.ONE_YEAR.toString(), TypeOfSlab.valueOf(loanInputDto.getType()).toString())
                .switchIfEmpty(Mono.error(new CustomException("no slab with this value")))
                .flatMap(slabs -> {
                    Loan loan =  loanMapper.LoanInputDtoToLoan(loanInputDto);
                    loan.setSlab_id(slabs.getId());
                    loan.setAppliedDate(LocalDate.now());
                    loan.setLoanEndDate(loan.getAppliedDate().plusYears(Integer.parseInt(loanInputDto.getTenure())));
                    loan.setInterest(slabs.getInterestRate());
                    return Mono.just(loan);
                })
                .flatMap(loan -> loanRepository.save(loan))
                .map(loan -> loanMapper.LoanToLoanOutputDto(loan));

    }

    @Override
    public Mono<LoanOutputDto> getById(UUID id) {
        return loanRepository.findById(id)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("no account with this id")))
                .flatMap(loan -> {
                    LoanOutputDto lout = loanMapper.LoanToLoanOutputDto(loan);
                    return slabRepository.findById(loan.getSlab_id())
                                    .flatMap(slab -> {
                                        lout.setTenure(slab.getTenures().toString());
                                        return accountDetailsRepository.findById(loan.getAccount())
                                                .flatMap(account -> {
                                                    return userRepository.findById(account.getUser_id())
                                                            .flatMap(user -> {
                                                                lout.setAge(user.getAge());
                                                                lout.setDob(user.getDob());
                                                                lout.setFirstName(user.getFirstName());
                                                                lout.setEmail(user.getEmail());
                                                                lout.setLastName(user.getLastName());
                                                                return Mono.just(lout);
                                                            });

                                                });
                                    });
                });

    }

    @Override
    public Flux<LoanOutputDto> getAllByUser(Long accNo) {
        return loanRepository.findAllByAccountNumber(accNo)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("no account with this id")))
                .map(loan -> {
                    return loanMapper.LoanToLoanOutputDto(loan);
                })
                .sort(Comparator.comparing(LoanOutputDto::getActive).reversed());

    }

    @Override
    public Flux<LoanOutputDto> getAll() {
        return loanRepository.findAll()
                .map(loan -> loanMapper.LoanToLoanOutputDto(loan));
    }

    @Override
    @Transactional
    public Mono<LoanOutputDto> setLoan(UUID id, String status) {
        return loanRepository.findById(id)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("no account with this id")))
                .filter(loan -> {
                    if(loan.getStatus().equals(Status.UNDER_REVIEW)) return Boolean.TRUE;
                    return Boolean.FALSE;
                })
                .flatMap(loan -> {
                        Period period = Period.between(loan.getAppliedDate(),loan.getLoanEndDate());
                        int months = period.getYears() * 12 + period.getMonths();
                        if(status.equals("APPROVE")){
                            loan.setStatus(Status.APPROVED);
                            loan.setRemainingAmount((loan.getLoanedAmount()*(Double.valueOf(loan.getInterest())/100))*(months/12) + loan.getLoanedAmount());
                            loan.setStartDate(LocalDate.now());
                            loan.setNextPaymentDate(loan.getStartDate().plusMonths(1));
                            return monthlyPayAmount(loan.getRemainingAmount(),months)
                                    .map(amount -> {
                                        loan.setMonthlyInterestAmount((int) Math.round(amount));
                                        return loan;
                                    })
                                    .flatMap(loan2 -> setTransaction(loan2,"CREDITED",loan2.getLoanedAmount())
                                            .then(Mono.defer(() -> loanRepository.save(loan2))));
                        }
                        else {
                            loan.setStatus(Status.REJECTED);
                            closeLoan(loan);
                            return loanRepository.save(loan);
                        }

                })
                .map(loan -> loanMapper.LoanToLoanOutputDto(loan));
    }

    private Mono<Loan> closeLoan(Loan loan) {

        loan.setActive(Boolean.FALSE);
        loan.setLoanEndDate(LocalDate.now());
        if(loan.getStatus().equals(Status.REJECTED)) {
            loan.setInterestAmount(0D);
            loan.setTotalAmount(0D);
        }
        else {
            loan.setInterestAmount(loan.getLoanedAmount()*Double.valueOf(loan.getInterest())/(12) - loan.getLoanedAmount());
            loan.setTotalAmount(loan.getLoanedAmount() + loan.getInterestAmount());
        }

        loan.setMonthlyInterestAmount(0);
        return Mono.just(loan);

    }

    private Mono<TransactionOutputDto> setTransaction(Loan loan, String credit, Double loanedAmount) {

        TransactionInputDto transactionInputDto = loanMapper.LoanToTransactionInputDto(loan);
        transactionInputDto.setPurpose("Loan amount " + credit);
        transactionInputDto.setTrans(credit);
        transactionInputDto.setType(loan.getTypeOfLoan().toString());

        if(credit.equals("DEBITED")) {
            transactionInputDto.setTo(null);
        }
        return transactionService.deposit(transactionInputDto,loan.getAccount());


    }

    private Mono<Double> monthlyPayAmount(Double remainingAmount, int months) {
        Double monthlyRepayment = remainingAmount/months;
        return Mono.just(monthlyRepayment);
    }


    @Override
    public Flux<LoanOutputDto> getBytype(Long accNo, String type) {
        return loanRepository.findAllByAccountNumber(accNo)
                .filter(loan -> loan.getTypeOfLoan().equals(TypeOfLoans.valueOf(type)))
                .map(loanMapper::LoanToLoanOutputDto);
    }

    @Override
    public Mono<Double> getTotalLoanAmount(Long accNo) {
        return loanRepository.findAllByAccountNumberAndIsActive(accNo)
                .map(Loan::getLoanedAmount)
                .reduce(0D, Double::sum);
    }

    @Override
    public Flux<LoanOutputDto> getAllPending() {
        return loanRepository.findAllPending()
                .map(loanMapper::LoanToLoanOutputDto);
    }

    @Override
    public Flux<LoanOutputDto> getAllByNotPending() {
        return loanRepository.findAllNotPending()
                .map(loanMapper::LoanToLoanOutputDto);
    }

    @Override
    public Flux<LoanOutputDto> getAllByStatus(String s) {
        return loanRepository.findByStatus(Status.valueOf(s))
                .map(loanMapper::LoanToLoanOutputDto);
    }


}
