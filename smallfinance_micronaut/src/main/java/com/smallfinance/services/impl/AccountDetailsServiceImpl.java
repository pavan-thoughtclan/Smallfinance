package com.smallfinance.services.impl;

import com.smallfinance.dtos.inputs.AccountDetailsInput;
import com.smallfinance.dtos.outputs.AccountDetailsOutput;
import com.smallfinance.dtos.outputs.FDDetails;
import com.smallfinance.dtos.outputs.HomePageOutput;
import com.smallfinance.entities.AccountDetails;
import com.smallfinance.entities.User;
import com.smallfinance.enums.AccountType;
import com.smallfinance.mapper.AccountDetailsMapper;
import com.smallfinance.mapper.UserMapper;
import com.smallfinance.repositories.AccountDetailsRepository;
import com.smallfinance.repositories.UserRepository;
import com.smallfinance.services.AccountDetailsService;
import com.smallfinance.services.DepositService;
import com.smallfinance.services.LoanService;
import com.smallfinance.services.UserService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service implementation class for managing account details operations.
 */
@Singleton
@RequiredArgsConstructor
public class AccountDetailsServiceImpl implements AccountDetailsService {

    @Inject
    private final AccountDetailsRepository accountDetailsRepository;
    @Inject
    private final UserService userService;
    private final UserRepository userRepository;
    private static long lastTimestamp = 8804175630060000L;
    private final AtomicLong lastAccountNumber = new AtomicLong(8804175630060000L);

    @Inject
    private final DepositService depositService;

    @Inject
    private final LoanService loanService;

    @Singleton
    @Inject
    AccountDetailsMapper accountDetailsMapper;

    @Singleton
    @Inject
    UserMapper userMapper;


    /**
     * Creates a new account based on the provided account_details input details.
     *
     * @param input The account details input.
     * @return AccountDetailsOutput representing the created account.
     */
    @Transactional
    @Override
    public AccountDetailsOutput create(AccountDetailsInput input) {
        User user = userService.addUser(input);
        input.setOpeningDate(LocalDate.now());
        AccountDetails accountDetail = accountDetailsMapper.dtoToAccount(input);
        accountDetail.setUser(user);
        accountDetail.setAccountNumber(generateUniqueAccountNumber());
//        accountDetail.setAccountType(AccountType.SAVINGS);
        AccountDetails saved = accountDetailsRepository.save(accountDetail);
        return accountDetailsMapper.accountToDto(saved);

    }

    /**
     * Retrieves account details by account number.
     *
     * @param accountNumber The account number .
     * @return AccountDetailsOutput representing the account.
     */
    @Override
    public AccountDetailsOutput getById(Long accountNumber) {
        AccountDetails accountDetails = accountDetailsRepository.findById(accountNumber).orElseThrow(() -> new RuntimeException("Account is not present with this account number"));
        User user = accountDetails.getUser();

        AccountDetailsOutput accountDetailsOutput = accountDetailsMapper.accountToDto(accountDetails);
        accountDetailsOutput.setEmail(user.getEmail());
        return accountDetailsOutput;
    }

    /**
     * Retrieves account details by user ID.
     *
     * @param userId The user ID.
     * @return AccountDetailsOutput representing the account.
     */
    @Override
    public AccountDetailsOutput getByUser(UUID userId) {
        User user = userMapper.userDtoToUser(userService.getById(userId));
        AccountDetailsOutput accountDetailsOutput = accountDetailsMapper.accountToDto(accountDetailsRepository.findByUserId(userId));
        accountDetailsOutput.setEmail(user.getEmail());
        return accountDetailsOutput;

    }

    /**
     * Retrieves the home page details for a given account ID.
     *
     * @param id The account ID.
     * @return HomePageOutput representing home page details.
     */
    @Override
    public HomePageOutput getHomePageById(Long id) {
        AccountDetails account = accountDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        HomePageOutput homePage = new HomePageOutput();
        homePage.setBalance(account.getBalance());
        homePage.setKyc(account.getKyc());
        FDDetails details = depositService.getDetails(id);
        homePage.setDepositAmount(details.getTotalFdAmount() + details.getTotalRdAmount());
        homePage.setLoanAmount(loanService.getTotalLoanAmount(id));
        homePage.setKyc(account.getKyc());
        return homePage;
    }

    /**
     * Retrieves the balance of the account by account number.
     *
     * @param accountNumber The account number.
     * @return The balance of the account.
     */
    @Override
    public Double getBalance(Long accountNumber) {
        return accountDetailsRepository.findById(accountNumber).get().getBalance();

    }

    /**
     * Setting KYC as true for a given account number.
     *
     * @param accNo The account number.
     * @return AccountDetailsOutput with KYC verification.
     */
    @Override
    public AccountDetailsOutput verifyKyc(Long accNo) {
        AccountDetails accountDetails = accountDetailsRepository.findById(accNo).orElseThrow(()->new RuntimeException("no account found with this id"));
        accountDetails.setKyc(Boolean.TRUE);
        accountDetailsRepository.update(accountDetails);
        User user = accountDetails.getUser();
        String userEmail = user.getEmail();
        AccountDetailsOutput accountOutputDto = accountDetailsMapper.accountToDto(accountDetails);

        accountOutputDto.setEmail(userEmail);
        return accountOutputDto;
    }


    /**
     * generating random unique number for account_number
     * @return unique account number.
     */
    private Long generateUniqueAccountNumber() {
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        accountDetailsList.sort(Comparator.comparing(AccountDetails::getAccountNumber));

        if (!accountDetailsList.isEmpty()) {
            lastTimestamp = accountDetailsList.get(accountDetailsList.size() - 1).getAccountNumber();
        }

        lastTimestamp++;
        return lastTimestamp;
    }


}
