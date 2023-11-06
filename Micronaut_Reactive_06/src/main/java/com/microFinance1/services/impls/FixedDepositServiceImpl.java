package com.microFinance1.services.impls;

import com.microFinance1.dtos.inputs.FixedDepositInputDto;
import com.microFinance1.dtos.inputs.TransactionInputDto;
import com.microFinance1.dtos.outputs.FDDetails;
import com.microFinance1.dtos.outputs.FixedDepositOutputDto;
import com.microFinance1.entities.FixedDeposit;
import com.microFinance1.entities.Slabs;
import com.microFinance1.exceptions.AmountNotSufficientException;
import com.microFinance1.exceptions.KycNotCompletedException;
import com.microFinance1.mapper.FixedDepositMapper;
import com.microFinance1.repository.AccountRepository;
import com.microFinance1.repository.FixedDepositRepository;
import com.microFinance1.repository.SlabRepository;
import com.microFinance1.services.FixedDepositService;
import com.microFinance1.services.TransactionService;
import com.microFinance1.utils.Tenures;
import com.microFinance1.utils.TypeOfSlab;
import io.micronaut.http.HttpStatus;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;
@Singleton
public class FixedDepositServiceImpl implements FixedDepositService {
    Logger logger = LoggerFactory.getLogger(FixedDeposit.class);
    @Inject
    private AccountRepository accountRepository;
    @Inject
    private FixedDepositMapper fixedDepositMapper;
    @Inject
    private SlabRepository slabRepository;
    @Inject
    private TransactionService transactionService;
    @Inject
    private FixedDepositRepository fixedDepositRepository;

    @Override
    @Transactional
    public Mono<FixedDepositOutputDto> createFixedDeposit(FixedDepositInputDto fixedDepositInputDto) {
        FixedDeposit fixedDeposit = fixedDepositMapper.mapToFixedDeposit(fixedDepositInputDto);
        return accountRepository.findById(fixedDepositInputDto.getAccountNumber())
                .switchIfEmpty(Mono.error(new AccountNotFoundException("account with this id not found")))
                .flatMap(account -> {
                    if (account.getKyc() == Boolean.FALSE)
                        return Mono.error(new KycNotCompletedException("kyc not completed"));
                    if (account.getBalance() < fixedDepositInputDto.getAmount())
                        return Mono.error(new AmountNotSufficientException("amount not suffiecient to open fd"));
                    return slabRepository.findByTenuresAndTypeOfTransaction(Tenures.valueOf(fixedDepositInputDto.getTenures()), TypeOfSlab.FD)
                            .flatMap(slabs -> {
                                fixedDeposit.setSlabId(slabs.getId());
                                fixedDeposit.setDepositedDate(LocalDate.now());
                                fixedDeposit.setInterestRate(slabs.getInterestRate());
                                return getOverDate(slabs, fixedDeposit.getDepositedDate())
                                        .flatMap(maturityDate -> {
                                            fixedDeposit.setMaturityDate(maturityDate);
                                            return Mono.just(fixedDeposit);
                                        });
                            });
                })
                .flatMap(fd -> fixedDepositRepository.save(fd))
                .flatMap(fd -> {
                    return performTransaction(fd, "DEBITED")
                            .thenReturn(fixedDepositMapper.mapToFixedDepositOutputDto(fd));
                });
    }

    @Override
    public Flux<FixedDepositOutputDto> allFixedDeposit(Long accNo) {
        return fixedDepositRepository.findByAccountNumber(accNo)
                .map(fd->fixedDepositMapper.mapToFixedDepositOutputDto(fd));
    }

    @Override
    public Mono<FDDetails> getFDDetails(Long accNo) {
        return fixedDepositRepository.findByAccountNumber(accNo)
                .collectList()
                .flatMap(fdList -> {
                    double sum = fdList.stream()
                            .mapToDouble(FixedDeposit::getAmount)
                            .sum();

                    FDDetails fdDetails = new FDDetails();
                    fdDetails.setTotalFdAmount(sum);
                    fdDetails.setTotalNoOfFD(fdList.size());
                    return Mono.just(fdDetails);
                });
    }

    @Override
    @Transactional
    public Mono<FixedDepositOutputDto> breakFixedDeposit(String uid) {
        UUID id = UUID.fromString(uid);

        return fixedDepositRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("No fixed repository with that id")))
                .flatMap(fd -> {
                    fd.setPreMatureWithdrawal(LocalDate.now());

                    if (!fd.getIsActive()) {
                        return Mono.error(new RuntimeException("This account is already closed"));
                    }

                    Period period = Period.between(fd.getDepositedDate(), LocalDate.now());


                    if (period.getMonths() < 1 && period.getYears() == 0) {
                        String interest = "0";
                        Double interestAmount = 0D;
                        interestAmount = 0D;
                        fd.setInterestAmount(interestAmount);
                        fd.setInterestRate(interest);
                        fd.setTotalAmount(fd.getAmount() + interestAmount);
                        fd.setIsActive(Boolean.FALSE);
                        return performTransaction(fd, "CREDITED")
                                .thenReturn(fd);
                    } else if (period.getMonths() > 1 && period.getMonths() < 3 && period.getYears() == 0) {
                        return slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_MONTH, TypeOfSlab.FD)
                                .switchIfEmpty(Mono.error(new RuntimeException("Slab not found")))
                                .flatMap(slab -> {
                                    String interest;
                                    Double interestAmount;
                                    interest = String.valueOf(Double.valueOf(slab.getInterestRate()) - 1D);
                                    interestAmount = (fd.getAmount() * Double.valueOf(interest) * period.getMonths()) / 100;
                                    fd.setInterestAmount(interestAmount);
                                    fd.setInterestRate(interest);
                                    fd.setTotalAmount(fd.getAmount() + interestAmount);
                                    fd.setIsActive(Boolean.FALSE);
                                    return performTransaction(fd, "CREDITED")
                                            .thenReturn(fd);
                                });
                    } else if (period.getMonths() > 3 && period.getMonths() < 6 && period.getYears() == 0) {
                        return slabRepository.findByTenuresAndTypeOfTransaction(Tenures.THREE_MONTHS, TypeOfSlab.FD)
                                .switchIfEmpty(Mono.error(new RuntimeException("Slab not found")))
                                .flatMap(slab -> {
                                    String interest = "0";
                                    Double interestAmount = 0D;
                                    interest = String.valueOf(Double.valueOf(slab.getInterestRate()) - 1D);
                                    interestAmount = (fd.getAmount() * Double.valueOf(interest) * (period.getMonths()) / 3) / 100;
                                    fd.setInterestAmount(interestAmount);
                                    fd.setInterestRate(interest);
                                    fd.setTotalAmount(fd.getAmount() + interestAmount);
                                    fd.setIsActive(Boolean.FALSE);
                                    return performTransaction(fd, "CREDITED")
                                            .thenReturn(fd);
                                });
                    } else if (period.getMonths() > 6 && period.getYears() == 0) {
                        return slabRepository.findByTenuresAndTypeOfTransaction(Tenures.SIX_MONTHS, TypeOfSlab.FD)
                                .switchIfEmpty(Mono.error(new RuntimeException("Slab not found")))
                                .flatMap(slab -> {
                                    String interest = "0";
                                    Double interestAmount = 0D;
                                    interest = String.valueOf(Double.valueOf(slab.getInterestRate()) - 1D);
                                    interestAmount = (fd.getAmount() * Double.valueOf(interest) * (period.getMonths()) / 6) / 100;
                                    fd.setInterestAmount(interestAmount);
                                    fd.setInterestRate(interest);
                                    fd.setTotalAmount(fd.getAmount() + interestAmount);
                                    fd.setIsActive(Boolean.FALSE);
                                    return performTransaction(fd, "CREDITED")
                                            .thenReturn(fd);
                                });
                    }

                    return Mono.just(fd);
                })
                .flatMap(fd -> {
                    fd.setIsActive(Boolean.FALSE);
                    return fixedDepositRepository.save(fd);
                })
                .map(fd -> {
                    FixedDepositOutputDto fixedDepositOutputDto = fixedDepositMapper.mapToFixedDepositOutputDto(fd);
                    fixedDepositOutputDto.setInterestRate(fd.getInterestRate());
                    fixedDepositOutputDto.setInterestAmountAdded(fd.getInterestAmount());
                    return fixedDepositOutputDto;
                });
    }

    @Override
    public Flux<FixedDepositOutputDto> getAll() {
        return fixedDepositRepository.findAll()
                .map(fd -> fixedDepositMapper.mapToFixedDepositOutputDto(fd));
    }

    @Override
    public Mono<FixedDepositOutputDto> getById(UUID id) {
        return fixedDepositRepository.findById(id)
                .map(fd->fixedDepositMapper.mapToFixedDepositOutputDto(fd));
    }

    @Override
    public Flux<FixedDepositOutputDto> getAllActive(Long accNo) {
        return fixedDepositRepository.findByAccountNumberAndIsActive(accNo)
                .map(fd->fixedDepositMapper.mapToFixedDepositOutputDto(fd));
    }

    private Mono<LocalDate> getOverDate(Slabs slabs, LocalDate date) {
        Tenures tenure = slabs.getTenures();
        return Mono.just(tenure)
                .map(tenures -> {
                    switch (tenure) {
                        case ONE_MONTH:
                            return date.plusMonths(1);
                        case THREE_MONTHS:
                            return date.plusMonths(3);
                        case SIX_MONTHS:
                            return date.plusMonths(6);
                        case ONE_YEAR:
                            return date.plusYears(1);
                        default:
                            throw new IllegalArgumentException("Invalid tenure: " + tenure);

                    }
                });
    }
    @Transactional
    protected Mono<UUID> performTransaction(FixedDeposit fd, String type) {

        TransactionInputDto transactionInputDto = fixedDepositMapper.FixedDepositToTransactionInputDto(fd);
        transactionInputDto.setPurpose("FD amount " + type);
        transactionInputDto.setTrans(type);
        transactionInputDto.setType("FD");

        if (type.equals("DEBITED")) {
            transactionInputDto.setTo(null);
        }
        return transactionService.deposit(transactionInputDto, fd.getAccountNumber())
                .map(transaction -> transaction.getTransactionID());

    }
}
