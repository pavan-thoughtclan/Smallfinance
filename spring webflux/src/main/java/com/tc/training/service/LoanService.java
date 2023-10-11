package com.tc.training.service;

import com.tc.training.dtos.inputdto.LoanInputDto;
import com.tc.training.dtos.outputdto.LoanOutputDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface LoanService {
    Mono<LoanOutputDto> addLoan(LoanInputDto loanInputDto);

    Mono<LoanOutputDto> getById(UUID id);

    Flux<LoanOutputDto> getAllByUser(Long accNo);

    Flux<LoanOutputDto> getAll();

    Mono<LoanOutputDto> setLoan(UUID id,String status);


    Flux<LoanOutputDto> getBytype(Long accNo, String type);

    Mono<Double> getTotalLoanAmount(Long accNo);

    Flux<LoanOutputDto> getAllPending();

    Flux<LoanOutputDto> getAllByNotPending();


    Flux<LoanOutputDto> getAllByStatus(String s);
}
