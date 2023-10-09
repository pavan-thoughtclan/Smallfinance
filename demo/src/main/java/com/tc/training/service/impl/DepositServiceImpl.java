package com.tc.training.service.impl;

import com.tc.training.dtos.outputdto.FDDetails;
import com.tc.training.dtos.outputdto.FixedDepositOutputDto;
import com.tc.training.dtos.outputdto.RecurringDepositOutputDto;
import com.tc.training.repo.AccountDetailsRepository;
import com.tc.training.service.AccountDetailsService;
import com.tc.training.service.DepositService;
import com.tc.training.service.FixedDepositService;
import com.tc.training.service.RecurringDepositService;
import com.tc.training.utils.RdStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepositServiceImpl implements DepositService {
    @Autowired
    private FixedDepositService fixedDepositService;
    @Autowired
    private RecurringDepositService recurringDepositService;
    @Autowired
    private AccountDetailsRepository accountRepository;


    @Override
    public Mono<FDDetails> getDetails(Long accNo) {
        Flux<FixedDepositOutputDto> fdsFlux = fixedDepositService.getAllActive(accNo);
        Flux<RecurringDepositOutputDto> rdsFlux = recurringDepositService.getByStatus(accNo);

        return Flux.zip(
                        fdsFlux.collectList(),
                        rdsFlux.collectList()
                )
                .map(tuple -> {
                    List<FixedDepositOutputDto> fds = tuple.getT1();
                    List<RecurringDepositOutputDto> rds = tuple.getT2();

                    FDDetails fdDetails = new FDDetails();
                    fdDetails.setTotalNoOfFD(fds.size());
                    fdDetails.setTotalNoOfRD(rds.size());

                    Double fdSum = fds.stream()
                            .mapToDouble(FixedDepositOutputDto::getAmount)
                            .sum();
                    fdDetails.setTotalFdAmount(fdSum);
                    return recurringDepositService.getTotalMoneyInvested(accNo)
                            .map(amount -> {
                                fdDetails.setTotalRdAmount(amount);
                                return fdDetails;
                            });

                })
                .flatMap(fdDetails -> fdDetails)
                .singleOrEmpty();

    }

    @Override
    public Flux<Object> getAccounts(Long accNo) {
        Flux<FixedDepositOutputDto> fdsFlux = fixedDepositService.getAllActive(accNo);
        Flux<RecurringDepositOutputDto> rdsFlux = recurringDepositService.getByStatus(accNo);

        return Flux.concat(
                fdsFlux.filter(FixedDepositOutputDto::getActive), // Filter active FDs
                rdsFlux.filter(rout -> rout.getStatus().equals(RdStatus.ACTIVE)) // Filter active RDs
        );
    }
}
