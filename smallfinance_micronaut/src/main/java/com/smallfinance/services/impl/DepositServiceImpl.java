package com.smallfinance.services.impl;

import com.smallfinance.dtos.outputs.FDDetails;
import com.smallfinance.dtos.outputs.FixedDepositOutput;
import com.smallfinance.dtos.outputs.RecurringDepositOutput;
import com.smallfinance.enums.RdStatus;
import com.smallfinance.services.DepositService;
import com.smallfinance.services.FixedDepositService;
import com.smallfinance.services.RecurringDepositService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class that manages deposit-related operations and provides details about deposits.
 */
@Singleton
public class DepositServiceImpl implements DepositService {


    @Inject
    private final FixedDepositService fixedDepositService;
    @Inject
    private final RecurringDepositService recurringDepositService;

    /**
     * Constructs a DepositServiceImpl with required dependencies.

     * fixedDepositService     The service for fixed deposits.
     *  recurringDepositService The service for recurring deposits.
     */
    public DepositServiceImpl(FixedDepositService fixedDepositService, RecurringDepositService recurringDepositService) {
        this.fixedDepositService = fixedDepositService;
        this.recurringDepositService = recurringDepositService;
    }

    /**
     * Retrieves details about deposits associated with a specific account.
     *
     * @param accNo The account number to retrieve deposit details.
     * @return FDDetails containing information about fixed and recurring deposits.
     */
    @Override
    public FDDetails getDetails(Long accNo) {
        FDDetails fdDetails = new FDDetails();
        List<FixedDepositOutput> fds = fixedDepositService.getAllActive(accNo);
        List<RecurringDepositOutput> rds = recurringDepositService.getByStatus(accNo);
        fdDetails.setTotalNoOfRD(rds.size());
        Double fdSum = 0D;
        Double rdSum = 0D;
        for (FixedDepositOutput fdout : fds) {
            fdSum += fdout.getAmount();
        }
        fdDetails.setTotalFdAmount(fdSum);
        fdDetails.setTotalRdAmount(recurringDepositService.getTotalMoneyInvested(accNo));
        return fdDetails;
    }

    /**
     * Retrieves a list of active deposit accounts associated with a specific account number.
     *
     * @param accNo The account number  to retrieve deposit accounts.
     * @return A list of FixedDepositOutput and RecurringDepositOutput objects representing active deposit accounts.
     */
    @Override
    public List<Object> getAccounts(Long accNo) {
        List<Object> obj = new ArrayList<>();
        List<RecurringDepositOutput> rds = recurringDepositService.getByStatus(accNo);
        List<FixedDepositOutput> fds = fixedDepositService.getAllActive(accNo);

        for(FixedDepositOutput fdout:fds) {
            if(fdout.getIsActive()) obj.add(fdout);
        }
        for(RecurringDepositOutput rout:rds) {
            if(rout.getStatus().equals(RdStatus.ACTIVE)) obj.add(rout);
        }

        return obj;

    }
}
