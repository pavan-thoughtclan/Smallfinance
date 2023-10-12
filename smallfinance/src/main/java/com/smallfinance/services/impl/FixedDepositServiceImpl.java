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

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public FixedDepositServiceImpl(AccountDetailsRepository accountDetailsRepository, SlabRepository slabRepository, FixedDepositRepository fixedDepositRepository, TransactionService transactionService) {
        this.accountDetailsRepository = accountDetailsRepository;
        this.slabRepository = slabRepository;
        this.fixedDepositRepository = fixedDepositRepository;
        this.transactionService = transactionService;
    }

    @Override
    public List<FixedDepositOutput> getAllActive(Long accNo) {
        List<FixedDeposit> fds = fixedDepositRepository.findByAccountNumberAndIsActive(accNo);
        return fds.stream().map(fd->fixedDepositMapper.mapToFixedDepositOutputDto(fd)).collect(Collectors.toList());
    }

    @Override
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

    @Override
    public List<FixedDepositOutput> allFixedDeposit(Long accNo) {
        return fixedDepositRepository.findByAccountNumber(accNo).stream().map(fd->fixedDepositMapper.mapToFixedDepositOutputDto(fd)).collect(Collectors.toList());
    }

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

    @Override
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

    @Override
    public List<FixedDepositOutput> getAll() {
         return fixedDepositRepository.findAll().stream().map(fd->fixedDepositMapper.mapToFixedDepositOutputDto(fd)).collect(Collectors.toList());
    }

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

    private UUID performTransaction(FixedDeposit fd, String type) {

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
