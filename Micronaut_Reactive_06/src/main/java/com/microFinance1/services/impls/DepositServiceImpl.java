package com.microFinance1.services.impls;

import com.microFinance1.dtos.outputs.FDDetails;
import com.microFinance1.dtos.outputs.FixedDepositOutputDto;
import com.microFinance1.dtos.outputs.RecurringDepositOutputDto;
import com.microFinance1.services.DepositService;
import com.microFinance1.services.FixedDepositService;
import com.microFinance1.services.RecurringDepositService;
import com.microFinance1.utils.RdStatus;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
@Singleton
public class DepositServiceImpl implements DepositService {
    @Inject
    private FixedDepositService fixedDepositService;
    @Inject
    private RecurringDepositService recurringDepositService;
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
                fdsFlux.filter(FixedDepositOutputDto::getIsActive), // Filter active FDs
                rdsFlux.filter(rout -> rout.getStatus().equals(RdStatus.ACTIVE)) // Filter active RDs
        );
    }
}
