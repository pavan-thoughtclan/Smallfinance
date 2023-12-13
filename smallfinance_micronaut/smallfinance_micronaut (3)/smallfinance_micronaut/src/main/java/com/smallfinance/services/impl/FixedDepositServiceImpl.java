package com.smallfinance.services.impl;

import com.smallfinance.dtos.inputs.FixedDepositInput;
import com.smallfinance.dtos.inputs.TransactionInput;
import com.smallfinance.dtos.outputs.FDDetails;
import com.smallfinance.dtos.outputs.FixedDepositOutput;
import com.smallfinance.entities.AccountDetails;
import com.smallfinance.entities.FixedDeposit;
import com.smallfinance.entities.Slabs;
import com.smallfinance.enums.Tenures;
import com.smallfinance.enums.TypeOfSlab;
import com.smallfinance.mapper.FixedDepositMapper;
import com.smallfinance.repositories.AccountDetailsRepository;
import com.smallfinance.repositories.FixedDepositRepository;
import com.smallfinance.repositories.SlabRepository;
import com.smallfinance.services.FixedDepositService;
import com.smallfinance.services.TransactionService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation class responsible for managing Fixed Deposits operations.
 */
@Singleton
public class FixedDepositServiceImpl implements FixedDepositService {
    @Inject
    private final AccountDetailsRepository accountDetailsRepository;
    @Inject
    private final SlabRepository slabRepository;
    @Inject
    private final FixedDepositRepository fixedDepositRepository;
    @Inject
    private final TransactionService transactionService;

    @Singleton
    @Inject
    FixedDepositMapper fixedDepositMapper;

    /**
     * Constructs a FixedDepositServiceImpl with required dependencies.
     *
     * @param accountDetailsRepository The repository for account details.
     * @param slabRepository           The repository for slabs.
     * @param fixedDepositRepository   The repository for fixed deposits.
     * @param transactionService       The service for transactions.
     */
    public FixedDepositServiceImpl(AccountDetailsRepository accountDetailsRepository, SlabRepository slabRepository, FixedDepositRepository fixedDepositRepository, TransactionService transactionService) {
        this.accountDetailsRepository = accountDetailsRepository;
        this.slabRepository = slabRepository;
        this.fixedDepositRepository = fixedDepositRepository;
        this.transactionService = transactionService;
    }

    /**
     * Retrieves all active Fixed Deposits associated with a given account number.
     *
     * @param accNo The account number to filter Fixed Deposits.
     * @return A list of active FixedDepositOutput DTOs.
     */
    @Override
    public List<FixedDepositOutput> getAllActive(Long accNo) {
        List<FixedDeposit> fds = fixedDepositRepository.findByAccountNumberAndIsActive(accNo);
        return fds.stream().map(fd->fixedDepositMapper.mapToFixedDepositOutputDto(fd)).collect(Collectors.toList());
    }

    /**
     * Creates a new Fixed Deposit account for the given FixedDepositInput data.
     *
     * @param fixedDepositInput The input data for creating a Fixed Deposit.
     * @return A FixedDepositOutput DTO representing the newly created Fixed Deposit account.
     */
    @Override
    @Transactional
    public FixedDepositOutput createFixedDeposit(FixedDepositInput fixedDepositInput) {
        Double amount = fixedDepositInput.getAmount();
        AccountDetails accountNumber = accountDetailsRepository.findById(fixedDepositInput.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Tenures tenures = Tenures.valueOf(fixedDepositInput.getTenures());

        if (!accountNumber.getKyc()) {
            throw new RuntimeException("Complete your KYC");
        }
        if (amount > accountNumber.getBalance()) {
            throw new RuntimeException("Amount exceeds account balance");
        }

        FixedDeposit fixedDeposit = new FixedDeposit();
        fixedDeposit.setAccountNumber(accountNumber);
        fixedDeposit.setAmount(amount);
        fixedDeposit.setSlabs(slabRepository.findByTenuresAndTypeOfTransaction(tenures, TypeOfSlab.FD));
        fixedDeposit.setDepositedDate(LocalDate.now());
        fixedDeposit.setMaturityDate(getOverDate(fixedDeposit.getSlabs(), fixedDeposit.getDepositedDate()));
        fixedDeposit.setTotalAmount(amount);
        fixedDeposit.setInterestRate(fixedDeposit.getSlabs().getInterestRate());
        performTransaction(fixedDeposit, "DEBIT");
        fixedDepositRepository.save(fixedDeposit);
        FixedDepositOutput fixedDepositOutputDto = fixedDepositMapper.mapToFixedDepositOutputDto(fixedDeposit);
        fixedDepositOutputDto.setInterestRate(fixedDeposit.getSlabs().getInterestRate());
        return fixedDepositOutputDto;
    }


    /**
     * Retrieves all Fixed Deposits associated with a specific account number.
     *
     * @param accNo The account number for which to retrieve Fixed Deposits.
     * @return A list of FixedDepositOutput DTOs.
     */
    @Override
    public List<FixedDepositOutput> allFixedDeposit(Long accNo) {
        return fixedDepositRepository.findByAccountNumber(accNo).stream().map(fd->fixedDepositMapper.mapToFixedDepositOutputDto(fd)).collect(Collectors.toList());
    }

    /**
     * Retrieves details of Fixed Deposits associated with an account.
     *
     * @param accNo The account number for which to retrieve Fixed Deposit details.
     * @return FDDetails containing information about Fixed Deposits.
     */
    @Override
    public FDDetails getFDDetails(Long accNo) {
        Double sum=0D;
        List<FixedDeposit> fd = fixedDepositRepository.findByAccountNumber(accNo);
        for(FixedDeposit f : fd){
            sum+=f.getAmount();
        }
        FDDetails fdDetails = new FDDetails();
        fdDetails.setTotalFdAmount(sum);
        fdDetails.setTotalNoOfFD(fd.size());
        return fdDetails;
    }

    /**
     * Breaks a Fixed Deposit account, calculates interest, and returns the resulting FixedDepositOutput.
     *
     * @param id The unique identifier of the Fixed Deposit account to break.
     * @return A FixedDepositOutput DTO representing the broken Fixed Deposit account.
     */
    @Override
    @Transactional
    public FixedDepositOutput breakFixedDeposit(String id) {
        UUID uuid = UUID.fromString(id);
        FixedDeposit fd = fixedDepositRepository.findById(uuid).orElseThrow(()->new RuntimeException("No fixed repository with that id"));
        fd.setPreMatureWithDrawl(LocalDate.now());
        if(!fd.getIsActive()) throw new RuntimeException("This account is already closed");
        Period period = Period.between(fd.getDepositedDate(),LocalDate.now());
        String interest = "0";
        Double interestAmount=0D;
        if(period.getMonths()<1 && period.getYears()==0 ) {
            interestAmount=0D;
        }
        else if(period.getMonths()>1 && period.getMonths()<3 && period.getYears()==0) {
            interest = slabRepository.findByTenuresAndTypeOfTransaction(Tenures.ONE_MONTH, TypeOfSlab.FD).getInterestRate();
            interest = String.valueOf(Double.valueOf(interest)-1D);
            interestAmount = (fd.getAmount() * Double.valueOf(interest) * period.getMonths())/100;
        }
        else if(period.getMonths()>3 && period.getMonths()<6 && period.getYears()==0){
            interest = slabRepository.findByTenuresAndTypeOfTransaction(Tenures.THREE_MONTHS, TypeOfSlab.FD).getInterestRate();
            interest = String.valueOf(Double.valueOf(interest)-1D);
            interestAmount = (fd.getAmount() * Double.valueOf(interest) * (period.getMonths())/3)/100;
        }
        else if(period.getMonths()>6 && period.getYears()==0){
            interest = slabRepository.findByTenuresAndTypeOfTransaction(Tenures.SIX_MONTHS, TypeOfSlab.FD).getInterestRate();
            interest = String.valueOf(Double.valueOf(interest)-1D);
            interestAmount = (fd.getAmount() * Double.valueOf(interest) * (period.getMonths())/6)/100;
        }
        fd.setInterestAmount(interestAmount);
        fd.getSlabs().setInterestRate(interest);
        fd.setTotalAmount(fd.getAmount()+interestAmount);
        closeAccount(fd);
        performTransaction(fd,"CREDIT");
        FixedDepositOutput fixedDepositOutputDto = fixedDepositMapper.mapToFixedDepositOutputDto(fd);
        fixedDepositOutputDto.setInterestRate(interest);
        fixedDepositOutputDto.setInterestAmountAdded(interestAmount);
        return fixedDepositOutputDto;    }

    public void closeAccount(FixedDeposit fd){
        fd.setIsActive(Boolean.FALSE);
        fixedDepositRepository.update(fd);
    }

    /**
     * Retrieves all Fixed Deposit accounts in the system.
     *
     * @return A list of FixedDepositOutput DTOs representing all Fixed Deposit accounts.
     */
    @Override
    public List<FixedDepositOutput> getAll() {
         return fixedDepositRepository.findAll().stream().map(fd->fixedDepositMapper.mapToFixedDepositOutputDto(fd)).collect(Collectors.toList());
    }

    /**
     * Retrieves a Fixed Deposit account by its id.
     *
     * @param id The unique identifier of the Fixed Deposit account to retrieve.
     * @return A FixedDepositOutput DTO representing the Fixed Deposit account.
     */
    @Override
    public FixedDepositOutput getById(UUID id) {
        FixedDeposit fd = fixedDepositRepository.findById(id).orElseThrow(()-> new RuntimeException("no account found with that id"));
        return fixedDepositMapper.mapToFixedDepositOutputDto(fd);
    }

    private LocalDate getOverDate(Slabs slabs, LocalDate date) {
        Tenures tenure = slabs.getTenures();
        if(tenure.equals(Tenures.ONE_MONTH)) return date.plusMonths(1);
        else if(tenure.equals(Tenures.THREE_MONTHS))  return date.plusMonths(3);
        else if(tenure.equals(Tenures.SIX_MONTHS))  return date.plusMonths(6);
        else if(tenure.equals(Tenures.ONE_YEAR))  return date.plusYears(1);
        return null;
    }


    protected UUID performTransaction(FixedDeposit fd, String type) {

        TransactionInput transactionInputDto = new TransactionInput();
        transactionInputDto.setAmount(fd.getTotalAmount());
        transactionInputDto.setTo((fd.getAccountNumber().getAccountNumber()));
        transactionInputDto.setAccountNumber((fd.getAccountNumber().getAccountNumber()));
        transactionInputDto.setType("FD");
        transactionInputDto.setPurpose("FD amount "+type);
        transactionInputDto.setTrans(type);
        return transactionService.deposit(transactionInputDto,fd.getAccountNumber().getAccountNumber()).getTransactionID();

    }

}
