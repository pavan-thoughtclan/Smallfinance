package com.tc.training.smallfinance.service.Impl;

import com.tc.training.smallfinance.dtos.outputs.FDDetails;
import com.tc.training.smallfinance.dtos.outputs.FixedDepositOutputDto;
import com.tc.training.smallfinance.dtos.outputs.RecurringDepositOutputDto;
import com.tc.training.smallfinance.repository.AccountRepository;
import com.tc.training.smallfinance.service.DepositService;
import com.tc.training.smallfinance.service.FixedDepositService;
import com.tc.training.smallfinance.service.RecurringDepositService;
import com.tc.training.smallfinance.utils.RdStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepositServiceImpl implements DepositService {
    @Autowired
    private FixedDepositService fixedDepositService;
    @Autowired
    private RecurringDepositService recurringDepositService;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public FDDetails getDetails(Long accNo) {
        FDDetails fdDetails = new FDDetails();
        List<FixedDepositOutputDto> fds = fixedDepositService.getAllActive(accNo);
        List<RecurringDepositOutputDto> rds = recurringDepositService.getByStatus(accNo);
        fdDetails.setTotalNoOfFD(fds.size());
        fdDetails.setTotalNoOfRD(rds.size());
        Double fdSum =0D;
        Double rdSum = 0D;
        for(FixedDepositOutputDto fdout:fds){
            fdSum+=fdout.getAmount();
        }
        fdDetails.setTotalFdAmount(fdSum);
        fdDetails.setTotalRdAmount(recurringDepositService.getTotalMoneyInvested(accNo));
        return fdDetails;
    }

    @Override
    public List<Object> getAccounts(Long accNo) {
        List<Object> obj = new ArrayList<>();
        List<RecurringDepositOutputDto> rds = recurringDepositService.getByStatus(accNo);
        List<FixedDepositOutputDto> fds = fixedDepositService.getAllActive(accNo);
        for(FixedDepositOutputDto fdout:fds) {
            if(fdout.getIsActive()) obj.add(fdout);
        }
        for(RecurringDepositOutputDto rout:rds) {
            if(rout.getStatus().equals(RdStatus.ACTIVE)) obj.add(rout);
        }
        return obj;
    }
}
