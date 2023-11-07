package com.tc.training.service.impl;

import com.tc.training.dtos.inputdto.FixedDepositInputDto;
import com.tc.training.dtos.inputdto.TransactionInputDto;
import com.tc.training.dtos.outputdto.FDDetails;
import com.tc.training.dtos.outputdto.FixedDepositOutputDto;
import com.tc.training.exception.AccountNotFoundException;
import com.tc.training.exception.AmountNotSufficientException;
import com.tc.training.exception.CustomException;
import com.tc.training.exception.KycNotCompletedException;
import com.tc.training.mapper.FixedDepositMapper;
import com.tc.training.model.FixedDeposit;
import com.tc.training.model.Slabs;
import com.tc.training.repo.AccountDetailsRepository;
import com.tc.training.repo.FixedDepositRepository;
import com.tc.training.repo.SlabRepository;
import com.tc.training.service.FixedDepositService;
import com.tc.training.service.TransactionService;
import com.tc.training.utils.Tenures;
import com.tc.training.utils.TypeOfSlab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Service
public class FixedDepositServiceImpl implements FixedDepositService {

    @Autowired
    private FixedDepositRepository fixedDepositRepository;
    @Autowired
    private AccountDetailsRepository accountRepository;

    @Autowired
    private SlabRepository slabRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private FixedDepositMapper fixedDepositMapper;


    @Override
    @Transactional
    public Mono<FixedDepositOutputDto> createFixedDeposit(FixedDepositInputDto fixedDepositInputDto) {

        FixedDeposit fixedDeposit = fixedDepositMapper.FixedDepositInputDtoToFixedDeposit(fixedDepositInputDto);
        return accountRepository.findById(fixedDepositInputDto.getAccountNumber())
                .switchIfEmpty(Mono.error(new AccountNotFoundException("account with this id not found")))
                .flatMap(account -> {
                    if(account.getKyc() == Boolean.FALSE) return Mono.error(new KycNotCompletedException("kyc not completed"));
                    if(account.getBalance() < fixedDepositInputDto.getAmount()) return Mono.error(new AmountNotSufficientException("amount not suffiecient to open fd"));
                    return slabRepository.findByTenuresAndTypeOfSlab(Tenures.valueOf(fixedDepositInputDto.getTenures()).toString(), TypeOfSlab.FD.toString())
                            .flatMap(slab  -> {
                                fixedDeposit.setSlab_id(slab.getId());
                                fixedDeposit.setDepositedDate(LocalDate.now());
                                fixedDeposit.setInterestRate(slab.getInterestRate());
                                return getOverDate(slab , fixedDeposit.getDepositedDate())
                                        .flatMap(maturityDate -> {
                                            fixedDeposit.setMaturityDate(maturityDate);
                                            return Mono.just(fixedDeposit);
                                        });
                            });
                })
                .flatMap(fd -> fixedDepositRepository.save(fd))
                .flatMap(fd -> {
                    return performTransaction(fd,"DEBITED")
                            .thenReturn(fixedDepositMapper.FixedDepositToFixedDepositOutputDto(fd));
                });



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


    @Override
    public Flux<FixedDepositOutputDto> getAllFixedDeposit(Long accNo) {
        return fixedDepositRepository.findByAccountNumber(accNo)
                .map(fixedDepositMapper::FixedDepositToFixedDepositOutputDto);
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
                    fd.setPreMatureWithDrawl(LocalDate.now());

                    if (!fd.getActive()) {
                        return Mono.error(new CustomException("This account is already closed"));
                    }

                    Period period = Period.between(fd.getDepositedDate(), LocalDate.now());


                    if (period.getMonths() < 1 && period.getYears() == 0) {
                        String interest = "0";
                        Double interestAmount = 0D;
                        interestAmount = 0D;
                        fd.setInterestAmount(interestAmount);
                        fd.setInterestRate(interest);
                        fd.setTotalAmount(fd.getAmount() + interestAmount);
                        fd.setActive(Boolean.FALSE);
                        return performTransaction(fd, "CREDITED")
                                .thenReturn(fd);
                    } else if (period.getMonths() > 1 && period.getMonths() < 3 && period.getYears() == 0) {
                        return slabRepository.findByTenuresAndTypeOfSlab(Tenures.ONE_MONTH.toString(), TypeOfSlab.FD.toString())
                                .switchIfEmpty(Mono.error(new RuntimeException("Slab not found")))
                                .flatMap(slab -> {
                                    String interest;
                                    Double interestAmount;
                                    interest = String.valueOf(Double.valueOf(slab.getInterestRate()) - 1D);
                                    interestAmount = (fd.getAmount() * Double.valueOf(interest) * period.getMonths()) / 100;
                                    fd.setInterestAmount(interestAmount);
                                    fd.setInterestRate(interest);
                                    fd.setTotalAmount(fd.getAmount() + interestAmount);
                                    fd.setActive(Boolean.FALSE);
                                    return performTransaction(fd, "CREDITED")
                                            .thenReturn(fd);
                                });
                    } else if (period.getMonths() > 3 && period.getMonths() < 6 && period.getYears() == 0) {
                        return slabRepository.findByTenuresAndTypeOfSlab(Tenures.THREE_MONTHS.toString(), TypeOfSlab.FD.toString())
                                .switchIfEmpty(Mono.error(new RuntimeException("Slab not found")))
                                .flatMap(slab -> {
                                    String interest = "0";
                                    Double interestAmount = 0D;
                                    interest = String.valueOf(Double.valueOf(slab.getInterestRate()) - 1D);
                                    interestAmount = (fd.getAmount() * Double.valueOf(interest) * (period.getMonths()) / 3) / 100;
                                    fd.setInterestAmount(interestAmount);
                                    fd.setInterestRate(interest);
                                    fd.setTotalAmount(fd.getAmount() + interestAmount);
                                    fd.setActive(Boolean.FALSE);
                                    return performTransaction(fd, "CREDITED")
                                            .thenReturn(fd);
                                });
                    } else if (period.getMonths() > 6 && period.getYears() == 0) {
                        return slabRepository.findByTenuresAndTypeOfSlab(Tenures.SIX_MONTHS.toString(), TypeOfSlab.FD.toString())
                                .switchIfEmpty(Mono.error(new RuntimeException("Slab not found")))
                                .flatMap(slab -> {
                                    String interest = "0";
                                    Double interestAmount = 0D;
                                    interest = String.valueOf(Double.valueOf(slab.getInterestRate()) - 1D);
                                    interestAmount = (fd.getAmount() * Double.valueOf(interest) * (period.getMonths()) / 6) / 100;
                                    fd.setInterestAmount(interestAmount);
                                    fd.setInterestRate(interest);
                                    fd.setTotalAmount(fd.getAmount() + interestAmount);
                                    fd.setActive(Boolean.FALSE);
                                    return performTransaction(fd, "CREDITED")
                                            .thenReturn(fd);
                                });
                    }

                    return Mono.just(fd);
                })
                .flatMap(fd -> {
                    fd.setActive(Boolean.FALSE);
                    return fixedDepositRepository.save(fd);
                })
                .map(fd -> {
                    FixedDepositOutputDto fixedDepositOutputDto = fixedDepositMapper.FixedDepositToFixedDepositOutputDto(fd);
                    fixedDepositOutputDto.setInterestRate(fd.getInterestRate());
                    fixedDepositOutputDto.setInterestAmountAdded(fd.getInterestAmount());
                    return fixedDepositOutputDto;
                });
    }



    @Override
    public Flux<FixedDepositOutputDto> getAll() {
            return fixedDepositRepository.findAll()
                .map(fd -> fixedDepositMapper.FixedDepositToFixedDepositOutputDto(fd));
    }

    @Override
    public Mono<FixedDepositOutputDto> getById(UUID id) {
        return fixedDepositRepository.findById(id)
                .map(fd -> fixedDepositMapper.FixedDepositToFixedDepositOutputDto(fd));
    }

    @Override
    public Flux<FixedDepositOutputDto> getAllActive(Long accNo) {
        return fixedDepositRepository.findByAccountNumberAndIsActive(accNo)
                .map(fd -> fixedDepositMapper.FixedDepositToFixedDepositOutputDto(fd));
    }

    private Mono<UUID> performTransaction(FixedDeposit fd,String type) {

        TransactionInputDto transactionInputDto = fixedDepositMapper.FixedDepositToTransactionInputDto(fd);
        transactionInputDto.setPurpose("FD amount " + type);
        transactionInputDto.setTrans(type);
        transactionInputDto.setType("FD");

        if(type.equals("DEBITED")) {
            transactionInputDto.setTo(null);
        }

        return transactionService.deposit(transactionInputDto, fd.getAccountNumber())
                .map(transaction -> transaction.getTransactionID());


    }

    @Scheduled(cron = "0 0 0 * * *")
    public void mature(){

         fixedDepositRepository.findAllByIsActive()
                 .map(fdAccount ->{
                     if(fdAccount.getMaturityDate().equals(LocalDate.now()))
                         maturedAccount(fdAccount);
                     return fdAccount;

                 });
    }

    private void maturedAccount(FixedDeposit fd) {
        String interest = fd.getInterestRate();
        Double interestAmount = (fd.getAmount() * Double.valueOf(interest) * 1)/100;
        fd.setMaturityDate(LocalDate.now());
        fd.setInterestAmount(interestAmount);
        fd.setTotalAmount(fd.getAmount()+interestAmount);
        fd.setActive(Boolean.FALSE);
        performTransaction(fd,"CREDITED");

    }
}

