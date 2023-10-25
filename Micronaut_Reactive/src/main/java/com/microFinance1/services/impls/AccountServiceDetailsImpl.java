package com.microFinance1.services.impls;

import com.microFinance1.dtos.inputs.AccountDetailsInputDto;
import com.microFinance1.dtos.outputs.AccountDetailsOutputDto;
import com.microFinance1.dtos.outputs.HomePageOutputDto;
import com.microFinance1.entities.AccountDetails;
import com.microFinance1.mapper.AccountDetailsMapper;
import com.microFinance1.repository.AccountRepository;
import com.microFinance1.services.AccountServiceDetails;
import com.microFinance1.services.DepositService;
import com.microFinance1.services.LoanService;
import com.microFinance1.services.UserService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.UUID;
@Singleton
public class AccountServiceDetailsImpl implements AccountServiceDetails {
    @Inject
    private AccountRepository accountRepository;
    @Inject
    private UserService userService;
    private static long lastTimestamp = 8804175630060000L;
    @Inject
    private AccountDetailsMapper accountDetailsMapper;
    @Inject
    private DepositService depositService;
    @Inject
    private LoanService loanService;

    @Override
    @Transactional
    public Mono<AccountDetailsOutputDto> createAccount(AccountDetailsInputDto accountDetailsInputDto) {
        AccountDetails accountDetails = accountDetailsMapper.mapToAccountDetails(accountDetailsInputDto);
        return userService.addUser(accountDetailsInputDto)
                .flatMap(user -> {
                    accountDetails.setUserId(user.getId());
                    return generateUniqueAccountNumber()
                            .map(accNumber -> {
                                accountDetails.setAccountNumber(accNumber);
                                accountDetails.setOpeningDate(LocalDate.now());
                                return accountDetails;
                            })
                            .flatMap(acc -> accountRepository.save(acc));
                })
                .map(savedAccount->{
//                    sendEmail(savedAccount.getUser_id().getEmail(), accountDetails.getUser_id().getPassword(), accountDetails.getAccountNumber());
                    AccountDetailsOutputDto outputDto = accountDetailsMapper.mapToAccountDetailsOutputDto(savedAccount);
//                    outputDto.setEmail(savedAccount.getUser_id().getEmail());
                    return outputDto;
    });
    }
    private Mono<Long> generateUniqueAccountNumber() {
        return accountRepository.findAll()
                .collectList()
                .flatMap(list -> {
                    list.sort(Comparator.comparing(AccountDetails::getAccountNumber));
                    lastTimestamp = list.get(list.size() - 1).getAccountNumber();
                    return Mono.just(lastTimestamp + 1);
                })
                .onErrorResume(exception -> Mono.just(++lastTimestamp));
    }
    @Override
    public Mono<AccountDetailsOutputDto> getAccount(Long accountNumber) {
        return accountRepository.findById(accountNumber).map(account-> accountDetailsMapper.mapToAccountDetailsOutputDto(account));
    }

    @Override
    public Mono<Double> getBalance(Long accNo) {
        return accountRepository.findById(accNo).map(AccountDetails::getBalance);
    }

    @Override
    public Mono<AccountDetailsOutputDto> getAccountByUser(UUID userId) {
        return accountRepository.findByUserID(userId)
                .map(accountDetails -> accountDetailsMapper.mapToAccountDetailsOutputDto(accountDetails));
    }

    @Override
    public Mono<HomePageOutputDto> getHomePageDetails(Long accNo) {
        return accountRepository.findById(accNo)
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
        return accountRepository.findById(accNo).flatMap(account->{account.setKyc(Boolean.TRUE);
        return accountRepository.update(account);
        })
                .map(account->accountDetailsMapper.mapToAccountDetailsOutputDto(account));
    }
//    private Mono<Void> sendEmail(String email, String password,Long accountNumber) {
//        String subject = "Username and password for your account";
//        String text = "Thank you for registering with our bank your account number is "+accountNumber+ " and your password is "+password;
//        return Mono.fromRunnable(()->{
//            emailService.sendEmail(email,subject,text);
//        });
//    }
}

