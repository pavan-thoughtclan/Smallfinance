package com.tc.training.service.impl;

import com.tc.training.dtos.inputdto.AccountDetailsInputDto;
import com.tc.training.dtos.outputdto.AccountDetailsOutputDto;
import com.tc.training.dtos.outputdto.HomePageOutputDto;
import com.tc.training.exception.AccountNotFoundException;
import com.tc.training.exception.CustomException;
import com.tc.training.mapper.AccountDetailsMapper;
import com.tc.training.model.AccountDetails;
import com.tc.training.repo.AccountDetailsRepository;
import com.tc.training.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AccountDetailsServiceImpl implements AccountDetailsService {


    @Autowired
    private AccountDetailsMapper accountDetailsMapper;
    @Autowired
    private DepositService depositService;
    @Autowired
    private LoanService loanService;

    @Autowired
    private UserService userService;
    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    private static Boolean flag = Boolean.TRUE;

    private static long lastTimestamp = 8804175630060000L;

    @Override
    public Mono<Double> getBalance(Long accNo) {
        return accountDetailsRepository.findById(accNo)
                .map( account -> account.getBalance())
                .onErrorMap(exception -> new AccountNotFoundException("this account does not exist"));

    }

    @Override
    @Transactional
    public synchronized Mono<AccountDetailsOutputDto> createAccount(AccountDetailsInputDto accountDetailsInputDto) {


                        AccountDetails accountDetails = accountDetailsMapper.AccountDetailsInputDtoToAccountDetails(accountDetailsInputDto);
                        return userService.addUser(accountDetailsInputDto)
                                        .flatMap(user -> {
                                            accountDetails.setUser_id(user.getId());
                                            return generateUniqueAccountNumber()
                                                    .flatMap(accNo -> {
                                                        accountDetails.setNew(Boolean.TRUE);
                                                        accountDetails.setAccountNumber(accNo);
                                                        accountDetails.setOpeningDate(LocalDate.now());
                                                        return Mono.just(accountDetails);

                                                    })
                                                    .flatMap(account -> accountDetailsRepository.save(accountDetails))
                                                    .map(account -> {
                                                        account.setNew(Boolean.FALSE);
                                                        return account;
                                                    });
                                        })

                                .map(savedAccount -> accountDetailsMapper.AccountDetailsToAccountDetailsOutputDto(savedAccount));

    }

    @Override
    public Mono<AccountDetailsOutputDto> getAccount(Long accountNumber) {
        return accountDetailsRepository.findById(accountNumber)
                .flatMap( account -> Mono.just(accountDetailsMapper.AccountDetailsToAccountDetailsOutputDto(account)))
                .onErrorMap(exception -> new AccountNotFoundException("this account does not exist"));

    }

    @Override
    public Mono<AccountDetailsOutputDto> getAccountByUser(UUID userId) {
        return accountDetailsRepository.findByUserID(userId)
                .map(accountDetails -> accountDetailsMapper.AccountDetailsToAccountDetailsOutputDto(accountDetails));

    }

    @Override
    public Mono<HomePageOutputDto> getHomePageDetails(Long accNo) {
        return accountDetailsRepository.findById(accNo)
                .flatMap(account->{
                    HomePageOutputDto homePage = new HomePageOutputDto();
                    homePage.setBalance(account.getBalance());
                    homePage.setKyc(account.getKyc());
                    return depositService.getDetails(accNo)
                            .map(deposit -> {
                                homePage.setDepositAmount(deposit.getTotalFdAmount() + deposit.getTotalRdAmount());
                                return homePage;
                            })
                            .flatMap(home -> loanService.getTotalLoanAmount(account.getAccountNumber())
                                            .flatMap(loanAmount -> {
                                                home.setLoanAmount(loanAmount);
                                                return Mono.just(home);
                                            }));
                });

    }

    @Override
    public Mono<AccountDetailsOutputDto> verifyKyc(Long accNo) {
        return accountDetailsRepository.findById(accNo)
                .flatMap(account -> {
                    account.setKyc(Boolean.TRUE);
                    return accountDetailsRepository.save(account);
                })
                .map(account-> accountDetailsMapper.AccountDetailsToAccountDetailsOutputDto(account));
    }

    private Mono<Long> generateUniqueAccountNumber() {
        final Object lock = new Object();
     //   AtomicLong al = new AtomicLong((long) 0.0);
      //  al.set((long) 9.0);
        return Mono.defer(() -> {
//            synchronized (lock) {
                return accountDetailsRepository.findAll()
                        .collectList()
                        .flatMap(list -> {
                            if(flag) {
                                list.sort(Comparator.comparing(AccountDetails::getAccountNumber).reversed());
                                lastTimestamp = list.get(0).getAccountNumber();
                                lastTimestamp = lastTimestamp + 1;
                                flag = Boolean.FALSE;
                                return Mono.just(lastTimestamp);
  //                              return Mono.just(al.get());
                            } else return Mono.just(++lastTimestamp);
                        })
                        .onErrorResume(exception ->{
                            return Mono.just(++lastTimestamp);
                        }  );
//            }
        });

    }


}
