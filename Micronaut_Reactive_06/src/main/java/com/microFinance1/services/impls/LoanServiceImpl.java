package com.microFinance1.services.impls;

import com.microFinance1.dtos.inputs.LoanInputDto;
import com.microFinance1.dtos.inputs.TransactionInputDto;
import com.microFinance1.dtos.outputs.LoanOutputDto;
import com.microFinance1.dtos.outputs.TransactionOutputDto;
import com.microFinance1.entities.Loan;
import com.microFinance1.mapper.LoanMapper;
import com.microFinance1.mapper.TransactionMapper;
import com.microFinance1.repository.AccountRepository;
import com.microFinance1.repository.LoanRepository;
import com.microFinance1.repository.SlabRepository;
import com.microFinance1.repository.UserRepository;
import com.microFinance1.services.LoanService;
import com.microFinance1.services.TransactionService;
import com.microFinance1.utils.Status;
import com.microFinance1.utils.Tenures;
import com.microFinance1.utils.TypeOfLoans;
import com.microFinance1.utils.TypeOfSlab;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.UUID;
@Singleton
public class LoanServiceImpl implements LoanService {
    @Inject
    private LoanMapper loanMapper;
    @Inject
    private AccountRepository accountRepository;
    @Inject
    private SlabRepository slabRepository;
    @Inject
    private LoanRepository loanRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private TransactionService transactionService;
    @Inject
    private TransactionMapper transactionMapper;
    @Override
    @Transactional
    public Mono<LoanOutputDto> addLoan(LoanInputDto loanInputDto) {
        return slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_YEAR, TypeOfSlab.valueOf(loanInputDto.getType()))
                .flatMap(slabs -> {
                    Loan loan =  loanMapper.mapToLoan(loanInputDto);
                    loan.setSlabId(slabs.getId());
                    loan.setAppliedDate(LocalDate.now());
                    loan.setLoanEndDate(loan.getAppliedDate().plusYears(Integer.parseInt(loanInputDto.getTenure())));
                    loan.setInterest(slabs.getInterestRate());
                    return Mono.just(loan);
                })
                .flatMap(loan -> loanRepository.save(loan))
                .map(loan -> loanMapper.mapToLoanOutputDto(loan));
    }

    @Override
    public Mono<LoanOutputDto> getById(UUID id) {
        return loanRepository.findById(id)
                .flatMap(loan -> {
                    LoanOutputDto lout = loanMapper.mapToLoanOutputDto(loan);
                    return slabRepository.findById(loan.getSlabId())
                            .flatMap(slab -> {
                                lout.setTenure(slab.getTenures().toString());
                                return accountRepository.findById(loan.getAccountNumber())
                                        .flatMap(account -> {
                                            return userRepository.findById(account.getUserId())
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
                .map(loan -> {
                    return loanMapper.mapToLoanOutputDto(loan);
                })
                .sort(Comparator.comparing(LoanOutputDto::getIsActive).reversed());
    }

    @Override
    public Flux<LoanOutputDto> getAll() {
        return loanRepository.findAll()
                .map(loan -> loanMapper.mapToLoanOutputDto(loan));
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
                                        .then(Mono.defer(() -> loanRepository.update(loan2))));
                    }
                    else {
                        loan.setStatus(Status.REJECTED);
                        closeLoan(loan);
                        return loanRepository.save(loan);
                    }

                })
                .map(loan -> loanMapper.mapToLoanOutputDto(loan));
    }
    private Mono<Loan> closeLoan(Loan loan) {

        loan.setIsActive(Boolean.FALSE);
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
    private Mono<Double> monthlyPayAmount(Double remainingAmount, int months) {
        Double monthlyRepayment = remainingAmount/months;
        return Mono.just(monthlyRepayment);
    }
    @Transactional
    protected Mono<TransactionOutputDto> setTransaction(Loan loan, String credit, Double loanedAmount) {

        TransactionInputDto transactionInputDto = loanMapper.LoanToTransactionInputDto(loan);
        transactionInputDto.setPurpose("Loan amount " + credit);
        transactionInputDto.setTrans(credit);
        transactionInputDto.setType(loan.getTypeOfLoan().toString());

        if(credit.equals("DEBITED")) {
            transactionInputDto.setTo(null);
        }
        return transactionService.deposit(transactionInputDto,loan.getAccountNumber());
    }

    @Override
    public Flux<LoanOutputDto> getByType(Long accNo, String type) {
        return loanRepository.findAllByAccountNumber(accNo)
                .filter(loan -> loan.getTypeOfLoan().equals(TypeOfLoans.valueOf(type)))
                .map(loanMapper::mapToLoanOutputDto);
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
                .map(loanMapper::mapToLoanOutputDto);
    }

    @Override
    public Flux<LoanOutputDto> getAllByNotPending() {
        return loanRepository.findAllNotPending()
                .map(loanMapper::mapToLoanOutputDto);
    }

    @Override
    public Flux<LoanOutputDto> getAllByStatus(String s) {
        return loanRepository.findByStatus(Status.valueOf(s))
                .map(loanMapper::mapToLoanOutputDto);
    }
}
